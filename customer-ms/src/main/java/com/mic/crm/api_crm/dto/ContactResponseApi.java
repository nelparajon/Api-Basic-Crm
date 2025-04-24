package com.mic.crm.api_crm.dto;

import java.util.List;

/**
 * Class that provides a message and the Customers's Data Transport Object as response information when an API request is successful.
 */
public class ContactResponseApi {

    private String message;
    private ContactDto contactDto;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ContactDto getCustomerDto() {
        return contactDto;
    }

    public void setCustomerDto(ContactDto contactDto) {
        this.contactDto = contactDto;
    }


    @Override
    public String toString() {
        return "ResponseApi{" +
                "message='" + message + '\'' +
                ", contactoDto=" + contactDto +
                '}';
    }
}