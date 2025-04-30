package com.mic.crm.api_crm.controller;

import com.mic.crm.api_crm.dto.CustomerDto;
import com.mic.crm.api_crm.dto.CustomerResponseApi;
import com.mic.crm.api_crm.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    //Endpoint: crear y guarda un nuevo cliente
    @PostMapping("/create")
    public ResponseEntity<CustomerResponseApi> createCustomer(@RequestBody @Valid CustomerDto customerDto){
        CustomerDto newCustomer = customerService.saveCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CustomerResponseApi("Cliente creado con éxito", newCustomer));
    }

    //Endpoint: obtener todos los clientes
    @GetMapping
    public ResponseEntity<CustomerResponseApi> getCustomers(){
        List<CustomerDto> customers = customerService.getCustomers();
        return customers.isEmpty()
                ? ResponseEntity.status(HttpStatus.OK).body(new CustomerResponseApi("No se encontraron clientes", customers))
                : ResponseEntity.status(HttpStatus.OK).body(new CustomerResponseApi("Clientes encontrados: ", customers));
    }

    // Endpoint: obtener un cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseApi> getCustomerById(@PathVariable long id){
        CustomerDto customer = customerService.getCustomerById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomerResponseApi("Cliente encontrado con éxito", customer));
    }

    //Endpoint: obtener un cliente por nombre
    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<CustomerResponseApi> getCustomerByName(@PathVariable String name){
        CustomerDto customer = customerService.getCustomerByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomerResponseApi("Cliente encontrado con éxito", customer));
    }

    // Endpoint para actualizar un cliente
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseApi> updateCustomer(@PathVariable long id, @RequestBody CustomerDto customerDto){
        CustomerDto updatedCustomer = customerService.updateCustomer(id, customerDto);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomerResponseApi("Cliente actualizado con éxito", updatedCustomer));
    }

    // Endpoint para eliminar un cliente por nombre
    @DeleteMapping("/{name}")
    public ResponseEntity<CustomerResponseApi> deleteCustomer(@PathVariable String name){
        customerService.deleteCustomer(name);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomerResponseApi("Cliente eliminado con éxito."));
    }
}
