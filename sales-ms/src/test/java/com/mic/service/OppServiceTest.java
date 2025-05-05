package com.mic.service;

import com.mic.dto.OppDto;
import com.mic.exception.OpportunityNotFoundException;
import com.mic.model.Opportunity;
import com.mic.repository.OppRepository;
import com.mic.utils.OppMapper;
import com.mic.utils.OppPriority;
import com.mic.utils.OppState;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OppServiceTest {

    @Mock
    private OppRepository oppRepository;
    @Mock
    private OppMapper oppMapper;

    @InjectMocks
    private OppService oppService;

    @Test
    void shouldSaveOpportunity_whenValidOppDtoProvided() {
        // Arrange
        OppDto oppDto = OppDto.builder()
                .title("Web")
                .description("Página Web")
                .state(OppState.NUEVA)
                .priority(OppPriority.ALTA)
                .amount(1000.0)
                .startDate(OffsetDateTime.now())
                .endDate(OffsetDateTime.now().plusDays(30))
                .customerId(1L)
                .contactId(2L)
                .assignedUserId("user123")
                .notes("Cliente potencial interesado en rediseño web")
                .build();

        Opportunity opportunityEntity = new Opportunity();
        Opportunity savedOpportunity = new Opportunity();
        OppDto expectedDto = OppDto.builder()
                .title("Web")
                .description("Página Web")
                .state(OppState.NUEVA)
                .priority(OppPriority.ALTA)
                .amount(1000.0)
                .startDate(oppDto.getStartDate())
                .endDate(oppDto.getEndDate())
                .customerId(1L)
                .contactId(2L)
                .assignedUserId("user123")
                .notes("Cliente potencial interesado en rediseño web")
                .build();

        when(oppMapper.oppDtoToOpp(oppDto)).thenReturn(opportunityEntity);
        when(oppRepository.save(opportunityEntity)).thenReturn(savedOpportunity);
        when(oppMapper.oppToOppDto(savedOpportunity)).thenReturn(expectedDto);

        // Act
        OppDto result = oppService.saveOpp(oppDto);

        // Assert
        assertNotNull(result);
        assertEquals("Web", result.getTitle());
        assertEquals(1000.0, result.getAmount());

        // Verify interactions
        verify(oppMapper).oppDtoToOpp(oppDto);
        verify(oppRepository).save(opportunityEntity);
        verify(oppMapper).oppToOppDto(savedOpportunity);
    }

    @Test
    void shouldReturnOpportunity_whenValidPublicIdProvided() {
        // Arrange
        String publicId = "validPublicId";
        Opportunity opportunity = new Opportunity("Web", "Página Web", OppState.NUEVA,
                OppPriority.ALTA, 1000.0, OffsetDateTime.now(),
                OffsetDateTime.now().plusDays(30), 1L, 2L);
        opportunity.setPublicId(publicId);

        when(oppRepository.findByPublicId(publicId)).thenReturn(Optional.of(opportunity));

        // Act
        Opportunity result = oppService.getOppByPublicId(publicId);

        // Assert
        assertNotNull(result);
        assertEquals(publicId, result.getPublicId());

        // Verify interactions
        verify(oppRepository).findByPublicId(publicId);
    }

    @Test
    void shouldThrowOpportunityNotFoundException_whenInvalidPublicIdProvided() {
        // Arrange
        String publicId = "invalidPublicId";

        when(oppRepository.findByPublicId(publicId)).thenReturn(Optional.empty());

        // Act & Assert
        OpportunityNotFoundException exception = assertThrows(OpportunityNotFoundException.class,
                () -> oppService.getOppByPublicId(publicId));

        assertEquals("No se encontró la oportunidad con publicId: " + publicId, exception.getMessage());

        // Verify interactions
        verify(oppRepository).findByPublicId(publicId);
    }

    @Test
    void shouldReturnAllOpportunities_whenOpportunitiesExist() {
        // Arrange
        Opportunity opportunity1 = Opportunity.builder()
                .title("Web")
                .description("Página Web")
                .state(OppState.NUEVA)
                .priority(OppPriority.ALTA)
                .amount(1000.0)
                .startDate(OffsetDateTime.now())
                .endDate(OffsetDateTime.now().plusDays(30))
                .customerId(1L)
                .contactId(2L)
                .build();

        Opportunity opportunity2 = Opportunity.builder()
                .title("App")
                .description("Aplicación Móvil")
                .state(OppState.NUEVA)
                .priority(OppPriority.MEDIA)
                .amount(1500.0)
                .startDate(OffsetDateTime.now())
                .endDate(OffsetDateTime.now().plusDays(30))
                .customerId(3L)
                .contactId(4L)
                .build();

        List<Opportunity> opportunityList = List.of(opportunity1, opportunity2);
        Page<Opportunity> opportunityPage = new PageImpl<>(opportunityList);

        Pageable pageable = PageRequest.of(0, 10);
        when(oppRepository.findAll(pageable)).thenReturn(opportunityPage);

        when(oppMapper.oppToOppDto(opportunity1)).thenReturn(OppDto.builder()
                .title("Web")
                .description("Página Web")
                .state(OppState.NUEVA)
                .priority(OppPriority.ALTA)
                .amount(1000.0)
                .startDate(opportunity1.getStartDate())
                .endDate(opportunity1.getEndDate())
                .customerId(1L)
                .contactId(2L)
                .assignedUserId("user123")
                .build());

        when(oppMapper.oppToOppDto(opportunity2)).thenReturn(OppDto.builder()
                .title("App")
                .description("Aplicación Móvil")
                .state(OppState.NUEVA)
                .priority(OppPriority.MEDIA)
                .amount(1500.0)
                .startDate(opportunity2.getStartDate())
                .endDate(opportunity2.getEndDate())
                .customerId(3L)
                .contactId(4L)
                .assignedUserId("user456")
                .build());

        // Act
        Page<OppDto> result = oppService.getAllOpps(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("Web", result.getContent().get(0).getTitle());
        assertEquals("App", result.getContent().get(1).getTitle());

        // Verify interactions
        verify(oppRepository).findAll(pageable);
        verify(oppMapper).oppToOppDto(opportunity1);
        verify(oppMapper).oppToOppDto(opportunity2);
    }

    @Test
    void shouldUpdateOpportunity_whenValidPublicIdAndOppDtoProvided() {
        // Arrange
        String publicId = "validPublicId";

        // Crear el OppDto con el builder
        OppDto oppDto = OppDto.builder()
                .title("Updated Web")
                .description("Updated Página Web")
                .state(OppState.NUEVA)
                .priority(OppPriority.ALTA)
                .amount(2000.0)
                .startDate(OffsetDateTime.now())
                .endDate(OffsetDateTime.now().plusDays(60))
                .customerId(1L)
                .contactId(2L)
                .assignedUserId("user123")
                .notes("Actualización de la oportunidad")
                .build();

        // Crear la Opportunity existente con builder
        Opportunity existingOpportunity = Opportunity.builder()
                .title("Web")
                .description("Página Web")
                .state(OppState.NUEVA)
                .priority(OppPriority.ALTA)
                .amount(1000.0)
                .startDate(OffsetDateTime.now())
                .endDate(OffsetDateTime.now().plusDays(30))
                .customerId(1L)
                .contactId(2L)
                .build();
        existingOpportunity.setPublicId(publicId);

        // Crear la Opportunity actualizada con builder
        Opportunity updatedOpportunity = Opportunity.builder()
                .title("Updated Web")
                .description("Updated Página Web")
                .state(OppState.EVAL)
                .priority(OppPriority.ALTA)
                .amount(2000.0)
                .startDate(OffsetDateTime.now())
                .endDate(OffsetDateTime.now().plusDays(60))
                .customerId(1L)
                .contactId(2L)
                .build();
        updatedOpportunity.setPublicId(publicId);

        // Mocking
        when(oppRepository.findByPublicId(publicId)).thenReturn(Optional.of(existingOpportunity));
        when(oppMapper.updateOpp(oppDto, existingOpportunity)).thenReturn(updatedOpportunity);
        when(oppRepository.save(updatedOpportunity)).thenReturn(updatedOpportunity);
        when(oppMapper.oppToOppDto(updatedOpportunity)).thenReturn(oppDto);

        // Act
        OppDto result = oppService.updateOpp(publicId, oppDto);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Web", result.getTitle());
        assertEquals("Updated Página Web", result.getDescription());
        assertEquals(2000.0, result.getAmount());

        // Verify interactions
        verify(oppRepository).findByPublicId(publicId);
        verify(oppMapper).updateOpp(oppDto, existingOpportunity);
        verify(oppRepository).save(updatedOpportunity);
        verify(oppMapper).oppToOppDto(updatedOpportunity);
    }
    @Test
    void shouldDeleteOpportunity_whenValidPublicIdProvided() {
        // Arrange
        String publicId = "validPublicId";

        // Usando builder para crear la Opportunity
        Opportunity opportunity = Opportunity.builder()
                .title("Web")
                .description("Página Web")
                .state(OppState.NUEVA)
                .priority(OppPriority.ALTA)
                .amount(1000.0)
                .startDate(OffsetDateTime.now())
                .endDate(OffsetDateTime.now().plusDays(30))
                .customerId(1L)
                .contactId(2L)
                .build();
        opportunity.setPublicId(publicId);

        when(oppRepository.findByPublicId(publicId)).thenReturn(Optional.of(opportunity));

        // Act
        oppService.deleteOpp(publicId);

        // Assert
        // Verify that deleteById is called after finding the opportunity
        verify(oppRepository).findByPublicId(publicId);
        verify(oppRepository).deleteById(opportunity.getId());
    }

    @Test
    void shouldThrowOpportunityNotFoundException_whenInvalidPublicIdForDeleteProvided() {
        // Arrange
        String publicId = "invalidPublicId";

        when(oppRepository.findByPublicId(publicId)).thenReturn(Optional.empty());

        // Act & Assert
        OpportunityNotFoundException exception = assertThrows(OpportunityNotFoundException.class,
                () -> oppService.deleteOpp(publicId));

        assertEquals("No se encontró la oportunidad con publicId: " + publicId, exception.getMessage());

        // Verify interactions
        verify(oppRepository).findByPublicId(publicId);
        verify(oppRepository, never()).deleteById(any());
    }

}
