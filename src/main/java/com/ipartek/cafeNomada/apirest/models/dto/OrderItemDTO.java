package com.ipartek.cafeNomada.apirest.models.dto;

import java.math.BigDecimal;

import com.ipartek.cafeNomada.apirest.models.entities.OrderItem;

/**
 * DTO para una línea de pedido.
 * Solo datos planos (snapshot) para evitar ciclos y acoplamientos con JPA.
 */
public class OrderItemDTO {

    private Long productId;
    private String productName;
    private BigDecimal unitPrice;
    private Integer quantity;
    private BigDecimal lineTotal;

    // --- Getters/Setters ---
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

    // --- Mapeo helper ---
    public static OrderItemDTO fromEntity(OrderItem entity) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setProductId(entity.getProductId());
        dto.setProductName(entity.getProductName());
        dto.setUnitPrice(entity.getUnitPrice());
        dto.setQuantity(entity.getQuantity());
        dto.setLineTotal(entity.getLineTotal());
        return dto;
    }
}
