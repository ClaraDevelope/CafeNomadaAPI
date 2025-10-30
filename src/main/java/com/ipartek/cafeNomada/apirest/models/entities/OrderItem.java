package com.ipartek.cafeNomada.apirest.models.entities;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

/**
 * Representa una línea de un pedido.
 * Guarda un "snapshot" del producto en el momento de la compra.
 */
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cada línea pertenece a un pedido concreto
    @JsonIgnore 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // Snapshot: ID del producto comprado
    @Column(nullable = false)
    private Long productId;

    // Snapshot: nombre del producto en el momento del pedido
    @Column(nullable = false)
    private String productName;

    // Snapshot: precio unitario en ese momento
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    // Cantidad de unidades pedidas
    @Column(nullable = false)
    private Integer quantity;

    // Total de la línea (unitPrice * quantity)
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal lineTotal;

    // --- Getters y Setters ---

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }
}
