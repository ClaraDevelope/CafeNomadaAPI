package com.ipartek.cafeNomada.apirest.models.entities;

import jakarta.persistence.*;  // Importa las anotaciones para trabajar con JPA (Hibernate)

import java.io.Serializable;
import java.math.BigDecimal;   // Tipo de dato para manejar precios con decimales

import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity                         // Indica que esta clase será una tabla en la base de datos
@Table(name = "products")       // Nombre de la tabla (opcional, pero recomendable)
public class Product implements Serializable{
    private static final long serialVersionUID = 1L; // Identificador de versión

    @Id                         // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;            // Se generará automáticamente (autoincremental)

    @Column(nullable = false)   // Campo obligatorio
    private String name;

    private String description;

    @Column(precision = 10, scale = 2) // Ejemplo: 99999999.99
    private BigDecimal price;

    private int stock;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;    // Ejemplo: “Café”, “Librería”, “Comercio justo”

    private String imageUrl;    // URL de la imagen del producto

    // --- Constructores ---
    public Product() {}

    public Product(String name, String description, BigDecimal price, int stock, Category category, String imageUrl) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    // --- Getters y Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
