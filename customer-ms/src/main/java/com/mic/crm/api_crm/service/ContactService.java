package com.mic.crm.api_crm.service;

import com.mic.crm.api_crm.dto.ContactDto;
import com.mic.crm.api_crm.dto.CustomerDto;
import com.mic.crm.api_crm.model.Contact;
import com.mic.crm.api_crm.model.Customer;
import com.mic.crm.api_crm.repository.ContactRepository;
import com.mic.crm.api_crm.utils.ContactMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
            throw new IllegalArgumentException("El id debe ser un numero positivo");
        }

       return contactRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("Contacto no encontrado con id: " + id));

    }

    public ContactDto updateContact(Long contactId, ContactDto contactDto) {
        Contact existingContact = getContactById(contactId);

        //Mapear el DTO a la entidad existente
        contactMapper.updateContactFromDto(contactDto, existingContact);

        contactRepository.save(existingContact);

        //Convertir de nuevo la entidad a DTO para exponerla
        return contactMapper.contactToContactDto(existingContact);
    }
}
