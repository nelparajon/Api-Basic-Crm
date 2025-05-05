package com.mic.crm.api_crm.service;

import com.mic.crm.api_crm.dto.ContactDto;
import com.mic.crm.api_crm.dto.CustomerDto;
import com.mic.crm.api_crm.exception.CustomerNotFoundException;
import com.mic.crm.api_crm.exception.InvalidCustomerDataException;
import com.mic.crm.api_crm.model.Contact;
import com.mic.crm.api_crm.model.Customer;
import com.mic.crm.api_crm.repository.CustomerRepository;
import com.mic.crm.api_crm.utils.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    private CustomerDto customerDto;
    private Customer customer;
    private OffsetDateTime now;

    @BeforeEach
    void setUp() {
        now = OffsetDateTime.now();
        customerDto = CustomerDto.builder()
                .id(1L)
                .name("Empresa Test")
                .registrationDate(now)
                .contactos(Collections.emptyList())
                .build();

        customer = Customer.builder()
                .id(1L)
                .name("Empresa Test")
                .registrationDate(now)
                .contactos(Collections.emptyList())
                .build();
    }

    @Test
    void saveCustomer_validInput_returnsDto() {
        when(customerMapper.customerDtoToCustomer(customerDto)).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.customerToCustomerDto(customer)).thenReturn(customerDto);

        CustomerDto result = customerService.saveCustomer(customerDto);

        assertNotNull(result);
        assertEquals(customerDto.getId(), result.getId());
        verify(customerRepository).save(customer);
    }

    @Test
    void saveCustomer_nullDto_throwsException() {
        assertThrows(InvalidCustomerDataException.class, () -> customerService.saveCustomer(null));
    }

    @Test
    void saveCustomer_blankName_throwsException() {
        CustomerDto invalidDto = CustomerDto.builder().id(1L).name(" ").build();
        assertThrows(InvalidCustomerDataException.class, () -> customerService.saveCustomer(invalidDto));
    }

    @Test
    void getCustomerById_validId_returnsDto() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerMapper.customerToCustomerDto(customer)).thenReturn(customerDto);

        CustomerDto result = customerService.getCustomerById(1L);
        assertEquals(customerDto.getId(), result.getId());
    }

    @Test
    void getCustomerById_invalidId_throwsException() {
        assertThrows(InvalidCustomerDataException.class, () -> customerService.getCustomerById(0));
    }

    @Test
    void getCustomerById_notFound_throwsException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(1L));
    }

    @Test
    void getCustomerByName_validName_returnsDto() {
        when(customerRepository.findCustomerByName("Empresa Test")).thenReturn(Optional.of(customer));
        when(customerMapper.customerToCustomerDto(customer)).thenReturn(customerDto);

        CustomerDto result = customerService.getCustomerByName("Empresa Test");
        assertEquals("Empresa Test", result.getName());
    }

    @Test
    void getCustomerByName_nullName_throwsException() {
        assertThrows(InvalidCustomerDataException.class, () -> customerService.getCustomerByName(null));
    }

    @Test
    void getCustomerIdByName_validName_returnsId() {
        when(customerRepository.findIdByName("Empresa Test")).thenReturn(Optional.of(1L));
        long id = customerService.getCustomerIdByName("Empresa Test");
        assertEquals(1L, id);
    }

    @Test
    void getCustomers_returnsPageableList() {
        Customer customer = new Customer(1L, "COAA", OffsetDateTime.now(), List.of(new Contact()));
        CustomerDto customerDto = new CustomerDto(1L, "COAA", OffsetDateTime.now(), List.of(new ContactDto()));

        // Crear un Page con la paginación simulada
        Page<Customer> customerPage = new PageImpl<>(List.of(customer), PageRequest.of(0, 10), 1);
        when(customerRepository.findAllCustomersWithContacts(PageRequest.of(0, 10))).thenReturn(customerPage);
        when(customerMapper.customerToCustomerDto(customer)).thenReturn(customerDto);

        // Act
        Page<CustomerDto> result = customerService.getCustomers(PageRequest.of(0, 10));

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());  // Verifica que haya 1 elemento en la página
        assertEquals(1, result.getContent().size());  // Verifica que haya 1 cliente en la página
        assertEquals("COAA", result.getContent().get(0).getName());  // Verifica que el nombre sea el correcto
    }

    @Test
    void updateCustomer_validInput_updatesAndReturnsDto() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(customerMapper.customerToCustomerDto(customer)).thenReturn(customerDto);
        when(customerMapper.customerDtoToCustomer(any())).thenReturn(customer);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerMapper.customerToCustomerDto(customer)).thenReturn(customerDto);

        CustomerDto updated = CustomerDto.builder().id(1L).name("Nuevo Nombre").build();
        CustomerDto result = customerService.updateCustomer(1L, updated);

        assertEquals("Nuevo Nombre", result.getName());
    }

    @Test
    void deleteCustomer_validName_deletesById() {
        when(customerRepository.findIdByName("Empresa Test")).thenReturn(Optional.of(1L));
        customerService.deleteCustomer("Empresa Test");
        verify(customerRepository).deleteById(1L);
    }

    @Test
    void deleteCustomer_blankName_throwsException() {
        assertThrows(InvalidCustomerDataException.class, () -> customerService.deleteCustomer(""));
    }

}
