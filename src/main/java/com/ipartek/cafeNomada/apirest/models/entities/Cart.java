package com.ipartek.cafeNomada.apirest.models.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "carts",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_carts_customer_id", columnNames = {"customer_id"})
    }
)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Un carrito por cliente
    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_cart_customer"))
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    // --- Helper methods ---
    public void addItem(CartItem item) {
        // Si ya existe el mismo producto, suma cantidades
        for (CartItem it : items) {
            if (it.getProduct().getId().equals(item.getProduct().getId())) {
                it.setQuantity(it.getQuantity() + item.getQuantity());
                touch();
                return;
            }
        }
        item.setCart(this);
        items.add(item);
        touch();
    }

    public void removeItem(Long productId) {
        items.removeIf(i -> i.getProduct().getId().equals(productId));
        touch();
    }

    public void clear() {
        items.clear();
        touch();
    }

    public BigDecimal getSubtotal() {
        return items.stream()
                .map(CartItem::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Si más adelante añades cupones, impuestos o envío, se calculan aquí
    public BigDecimal getTotal() {
        return getSubtotal();
    }

    @PreUpdate
    private void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    // --- Getters/Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public List<CartItem> getItems() { return items; }
    public void setItems(List<CartItem> items) { this.items = items; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
