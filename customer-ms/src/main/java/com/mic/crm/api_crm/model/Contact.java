package com.mic.crm.api_crm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "contacts")
@Data
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String contactFirstName;

    private String contactSecondName;
    @Email(message = "El email no tiene el formato correcto")
    private String email;

    private String phone;

    private String departamento;

    private String cargo;

    @ManyToOne()
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "El cliente no puede estar vacío.")
    private Customer customer;

    public Contact() {
    }

    public Contact(String contactFirstName, String contactSecondName, String email, String phone, String departamento, String cargo, Customer customer) {
        this.contactFirstName = contactFirstName;
        this.contactSecondName = contactSecondName;
        this.email = email;
        this.phone = phone;
        this.departamento = departamento;
        this.cargo = cargo;
        this.customer = customer;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @PrePersist
    public void validateContactMethod(){
        if ((this.email == null || this.email.isEmpty()) && (this.phone == null || this.phone.isEmpty())) {
            throw new IllegalArgumentException("Debe proporcionar al menos un método de contacto: email o teléfono.");
        }
    }
}
