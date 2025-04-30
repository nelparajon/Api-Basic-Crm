package com.mic.crm.api_crm.service;

import com.mic.crm.api_crm.dto.ContactDto;
import com.mic.crm.api_crm.model.Contact;
import com.mic.crm.api_crm.model.Customer;
import com.mic.crm.api_crm.repository.ContactRepository;
import com.mic.crm.api_crm.utils.ContactMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactMapper contactMapper;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private ContactService contactService;

    private ContactDto contactDto;
    private Contact contact;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = Customer.builder()
                .id(1L)
                .name("Cliente Ejemplo")
                .contactos(new ArrayList<>())
                .build();

        contactDto = ContactDto.builder()
                .id(1L)
                .contactFirstName("Juan")
                .contactSecondName("Pérez")
                .email("juan@example.com")
                .phone("123456789")
                .departamento("Ventas")
                .cargo("Gerente")
                .customerId(1L)
                .build();

        contact = new Contact();
        contact.setId(1L);
        contact.setContactFirstName("Juan");
        contact.setContactSecondName("Pérez");
        contact.setEmail("juan@example.com");
        contact.setPhone("123456789");
        contact.setDepartamento("Ventas");
        contact.setCargo("Gerente");
        contact.setCustomer(customer);
    }

    @Test
    void saveContact_validDto_savesAndReturnsDto() {
        when(customerService.findCustomerEntityById(1L)).thenReturn(customer);
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);
        when(contactMapper.contactToContactDto(contact)).thenReturn(contactDto);

        ContactDto result = contactService.saveContact(contactDto);

        assertNotNull(result);
        assertEquals(contactDto.getEmail(), result.getEmail());
        verify(customerService).findCustomerEntityById(1L);
        verify(contactRepository).save(any(Contact.class));
        verify(contactMapper).contactToContactDto(contact);
    }

    @Test
    void getContactById_validId_returnsContact() {
        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));
        Contact result = contactService.getContactById(1L);
        assertEquals("Juan", result.getContactFirstName());
    }

    @Test
    void getContactById_invalidId_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> contactService.getContactById(0L));
    }

    @Test
    void getContactById_notFound_throwsRuntimeException() {
        when(contactRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> contactService.getContactById(1L));
    }

    @Test
    void updateContact_validInput_updatesAndReturnsDto() {
        when(contactRepository.findById(1L)).thenReturn(Optional.of(contact));
        doNothing().when(contactMapper).updateContactFromDto(contactDto, contact);
        when(contactRepository.save(contact)).thenReturn(contact);
        when(contactMapper.contactToContactDto(contact)).thenReturn(contactDto);

        ContactDto result = contactService.updateContact(1L, contactDto);

        assertEquals(contactDto.getEmail(), result.getEmail());
        verify(contactMapper).updateContactFromDto(contactDto, contact);
        verify(contactRepository).save(contact);
    }
}

