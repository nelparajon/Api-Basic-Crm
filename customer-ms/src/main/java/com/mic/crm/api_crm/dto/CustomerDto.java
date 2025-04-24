package com.mic.crm.api_crm.dto;

import com.mic.crm.api_crm.model.Contact;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;


public class CustomerDto {
    private long id;
    private String name;
    private OffsetDateTime registrationDate;
    private List<ContactDto> contactos;

    public CustomerDto() {
    }

    public CustomerDto(long id, String name, OffsetDateTime registrationDate, List<ContactDto> contactos) {
        this.id = id;
        this.name = name;
        this.registrationDate = registrationDate;
        this.contactos = contactos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OffsetDateTime getRegistrationDate() {
        return registrationDate;
    }

    public List<ContactDto> getContactos() {
        return contactos;
    }

    public void setContactos(List<ContactDto> contactos) {
        this.contactos = contactos;
    }

    public void setRegistrationDate(OffsetDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", registrationDate=" + registrationDate +
                ", contactos=" + contactos +
                '}';
    }
}
