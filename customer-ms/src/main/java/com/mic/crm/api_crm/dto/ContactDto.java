package com.mic.crm.api_crm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mic.crm.api_crm.model.Customer;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class ContactDto {
    long id;
    private String contactFirstName;
    private String contactSecondName;
    @Email(message = "El formato del email no es válido")
    private String email;
    private String phone;
    private String departamento;
    private String cargo;
    private long customerId;
    List<ContactDto> contacts;

    public ContactDto() {
    }


    public ContactDto(String contactFirstName, String contactSecondName, String email, String phone, String departamento, String cargo, long customerId) {
        this.contactFirstName = contactFirstName;
        this.contactSecondName = contactSecondName;
        this.email = email;
        this.phone = phone;
        this.departamento = departamento;
        this.cargo = cargo;
        this.customerId = customerId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public String getContactSecondName() {
        return contactSecondName;
    }

    public void setContactSecondName(String contactSecondName) {
        this.contactSecondName = contactSecondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "ContactDto{" +
                ", Nombre='" + contactFirstName + '\'' +
                ", Apellidos='" + contactSecondName + '\'' +
                ", Email='" + email + '\'' +
                ", Tlfn='" + phone + '\'' +
                ", Departamento='" + departamento + '\'' +
                ", Cargo='" + cargo + '\'' +
                '}';
    }
}
