package com.mic.crm.api_crm.dto;

import java.util.List;

/**
 * Class that provides a message and the Customers's Data Transport Object as response information when an API request is successful.
 */
public class ContactResponseApi {

    private String message;
    private ContactDto contactDto;
    private List<ContactDto> contacts;

    public ContactResponseApi(){

    }
    /**
     * Constructor to create an ApiResponse with a message and an ContactDTO.
     *
     * @param message    The error message.
     * @param contactDto The Contact Entity DTO.
     */


    public ContactResponseApi(String message) {
        this.message = message;
    }

    public ContactResponseApi(String message, ContactDto contactDto) {
        this.message = message;
        this.contactDto = contactDto;
    }
    public ContactResponseApi(String message, List<ContactDto> contacts){
        this.message = message;
        this.contacts = contacts;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ContactDto getContactDto() {
        return contactDto;
    }

    public void setContactDto(ContactDto contactDto) {
        this.contactDto = contactDto;
    }

    public List<ContactDto> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactDto> contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        return "ResponseApi{" +
                "message='" + message + '\'' +
                ", contactoDto=" + contactDto +
                '}';
    }
}