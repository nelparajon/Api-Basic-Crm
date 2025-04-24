package com.mic.crm.api_crm.controller;

import com.mic.crm.api_crm.dto.ContactDto;
import com.mic.crm.api_crm.dto.ContactResponseApi;
import com.mic.crm.api_crm.model.Contact;
import com.mic.crm.api_crm.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService){ this.contactService = contactService; }
    @PostMapping("/create")
    public ResponseEntity<ContactResponseApi> createContact(@RequestBody @Valid ContactDto contactDto) {

        ContactDto savedContact = contactService.saveContact(contactDto);

        // Retornar el contacto creado con un mensaje
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ContactResponseApi("Contacto añadido con éxito", savedContact));
    }

    @PutMapping("/{contactId}")
    public ResponseEntity<ContactResponseApi> updateContact(@PathVariable long contactId, @RequestBody @Valid ContactDto contactDto){
        ContactDto updatedContact = contactService.updateContact(contactId, contactDto);

        return ResponseEntity.status(HttpStatus.OK).body(new ContactResponseApi("Contacto actualizado con éxito", updatedContact));
    }
}
