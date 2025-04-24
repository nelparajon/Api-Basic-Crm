package com.mic.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name="product_seq", sequenceName = "product_seq", allocationSize = 1)
    private Long id;

    //private long UUID;

    @NotBlank(message = "Debe introducir un título válido")
    @Column(nullable = false)
    private String title;

    @Size(max = 540)
    private String description;
    @Positive(message = "Debe ser mayor que 0")
    @Column(name = "price", nullable = false)
    private Double price;

    public Product(String title, String description, double price) {
        this.title= title;
        this.description = description;
        this.price = price;
    }

}
