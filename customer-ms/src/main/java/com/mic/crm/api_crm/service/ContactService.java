package com.mic.crm.api_crm.service;

import com.mic.crm.api_crm.dto.ContactDto;
import com.mic.crm.api_crm.dto.CustomerDto;
import com.mic.crm.api_crm.exception.ContactNotFoundException;
import com.mic.crm.api_crm.exception.CustomerNotFoundException;
import com.mic.crm.api_crm.exception.InvalidContactDataException;
import com.mic.crm.api_crm.model.Contact;
import com.mic.crm.api_crm.model.Customer;
import com.mic.crm.api_crm.repository.ContactRepository;
import com.mic.crm.api_crm.utils.ContactMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    private final CustomerService customerService;

    public ContactService(ContactRepository contactRepository, ContactMapper contactMapper, CustomerService customerService){
        this.contactRepository = contactRepository;
        this.contactMapper = contactMapper;
        this.customerService = customerService;
    }
    public ContactDto saveContact(ContactDto contactDto) {
        // Paso 1: Obtener el Customer utilizando el customerId
        Customer associatedCustomer = customerService.findCustomerEntityById(contactDto.getCustomerId());
        // Paso 2: Convertir el ContactDto a Contact y asociar el Customer manualmente
        Contact newContact = new Contact();
        newContact.setContactFirstName(contactDto.getContactFirstName());
        newContact.setContactSecondName(contactDto.getContactSecondName());
        newContact.setEmail(contactDto.getEmail());
        newContact.setPhone(contactDto.getPhone());
        newContact.setDepartamento(contactDto.getDepartamento());
        newContact.setCargo(contactDto.getCargo());
        newContact.setCustomer(associatedCustomer);
        associatedCustomer.addContact(newContact);

        // Paso 3: Guardar el nuevo contacto
        Contact savedContact = contactRepository.save(newContact);

        return contactMapper.contactToContactDto(savedContact);

    }

    public Contact getContactById(long id){

        if(id <= 0){
            throw new InvalidContactDataException("El id debe ser un numero positivo");
        }

       return contactRepository.findById(id)
                .orElseThrow( () -> new ContactNotFoundException("Contacto no encontrado con id: " + id));

    }

    public List<ContactDto> getContacts(){
        return contactRepository.findAll()
                .stream()
                .map(contactMapper::contactToContactDto)
                .toList();
    }

    public ContactDto updateContact(long id, ContactDto contactDto) {
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException("No existe un contacto con ese id"));
        System.out.println("EXISTING CONTACT ID: " + existingContact.getId());
        System.out.println("ID DEL PATH: " + id);

        // Actualizar datos del contacto
        Contact updatedContact = contactMapper.updateContactFromDto(contactDto, existingContact);

        System.out.println("ID DEL UPDATE CONTACT: " + updatedContact.getId());

        // Asignar el Customer correspondiente
        Customer associatedCustomer = customerService.findCustomerEntityById(existingContact.getCustomer().getId());
        updatedContact.setCustomer(associatedCustomer);

        contactRepository.save(updatedContact);

        return contactMapper.contactToContactDto(updatedContact);
    }
}