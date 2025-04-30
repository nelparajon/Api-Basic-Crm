package com.mic.crm.api_crm.service;

import com.mic.crm.api_crm.dto.CustomerDto;
import com.mic.crm.api_crm.exception.ContactNotFoundException;
import com.mic.crm.api_crm.exception.CustomerNotFoundException;
import com.mic.crm.api_crm.exception.InvalidCustomerDataException;
import com.mic.crm.api_crm.model.Customer;
import com.mic.crm.api_crm.repository.CustomerRepository;
import com.mic.crm.api_crm.utils.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper){
        this.customerRepository = customerRepository;
        this.customerMapper  = customerMapper;
    }

    public CustomerDto saveCustomer(CustomerDto customerDto){
        if (customerDto == null) {
            throw new InvalidCustomerDataException("El objeto CustomerDto no puede ser nulo.");
        }
        if (customerDto.getName() == null || customerDto.getName().isBlank()) {
            throw new InvalidCustomerDataException("El nombre del cliente es obligatorio.");
        }
        Customer newCustomer = customerMapper.customerDtoToCustomer(customerDto);

        Customer savedCustomer = customerRepository.save(newCustomer);

        return customerMapper.customerToCustomerDto(savedCustomer);
    }

    public CustomerDto getCustomerById(long id) {
        if (id <= 0) {
            throw new InvalidCustomerDataException("El ID del cliente debe ser un número positivo.");
        }

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Cliente no encontrado con ID: " + id, HttpStatus.NOT_FOUND));

        return customerMapper.customerToCustomerDto(customer);
    }

    public Optional<Customer> findCustomerById(long id){
       return Optional.ofNullable(customerRepository.findById(id)
               .orElseThrow(() -> new ContactNotFoundException("Contacto no encontrado con ese id")));
    }

    public Customer findCustomerEntityById(long id) {
        System.out.println("Customer id:" + id);
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Cliente no encontrado con ID: " + id, HttpStatus.NOT_FOUND));
    }

    public CustomerDto getCustomerByName(String name){
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidCustomerDataException("El nombre del cliente no puede ser nulo o estar vacío.");
        }
        Customer customer = customerRepository.findCustomerByName(name)
                .orElseThrow( () -> new CustomerNotFoundException("Cliente no encontrado por ese nombre: " + name, HttpStatus.NOT_FOUND));

        return customerMapper.customerToCustomerDto(customer);
    }

    public long getCustomerIdByName(String name){
        if(name == null || name.isEmpty()){
            throw new InvalidCustomerDataException("El nombre del cliente no puede ser nulo o estar vacío");
        }
        return customerRepository.findIdByName(name)
                .orElseThrow(()-> new CustomerNotFoundException("Cliente no encontrado por ese nombre", HttpStatus.NOT_FOUND));
    }

    public List<CustomerDto> getCustomers(){
        return customerRepository.findAllCustomersWithContacts()
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .toList();
    }

    public CustomerDto updateCustomer(long id, CustomerDto customerDto){
        if (customerDto == null) {
            throw new InvalidCustomerDataException("Los datos del cliente no pueden ser nulos.");
        }
        CustomerDto existingCustomerDto = getCustomerById(id);
        existingCustomerDto.setName(customerDto.getName());
        Customer updatedCustomer = customerMapper.customerDtoToCustomer(existingCustomerDto);
        customerRepository.save(updatedCustomer);

        return customerMapper.customerToCustomerDto(updatedCustomer);
    }

    public void deleteCustomer(String name){
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidCustomerDataException("El nombre del cliente no puede ser nulo o estar vacío.");
        }
        long id = getCustomerIdByName(name);

        customerRepository.deleteById(id);
    }
}
