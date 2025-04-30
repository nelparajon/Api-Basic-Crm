package com.mic.crm.api_crm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    @SequenceGenerator(name="customer_seq", sequenceName = "customer_seq", allocationSize = 1)
    private long id;

    public Customer() {
    }

    public Customer(String name, OffsetDateTime registrationDate, List<Contact> contactos) {
        this.name = name;
        this.registrationDate = registrationDate;
        this.contactos = contactos;
    }
    @Column(nullable = false)
    @NotBlank(message = "El campo nombre no debe estar vacío.")
    private String name;

    @Column(name = "registration_date")
    @CreationTimestamp(source = SourceType.DB) //genera un valor por defecto acorde al tipo de source de la BD
    private OffsetDateTime registrationDate;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private List<Contact> contactos;

    public long getId() {
        return id;
    }
    public void setId(long id){ this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OffsetDateTime getRegistrationDate() {
        return registrationDate;
    }

    public List<Contact> getContactos() {
        return contactos;
    }

    public void setContactos(List<Contact> contactos) {
        this.contactos = contactos;
    }

    public void addContact(Contact contact) {
        contactos.add(contact);
        contact.setCustomer(this);//asignar el customer al contacto actual
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", registrationDate=" + registrationDate +
                ", contactos=" + contactos +
                '}';
    }
}
