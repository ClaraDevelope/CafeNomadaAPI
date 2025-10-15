package com.ipartek.cafeNomada.apirest.models.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.ipartek.cafeNomada.apirest.models.entities.Order;
import com.ipartek.cafeNomada.apirest.models.entities.OrderStatus;

import java.util.stream.Collectors;

/**
 * DTO para Pedido: información plana y serializable para la API.
 * Evita exponer entidades JPA y previene ciclos (Order -> items -> order...).
 */
public class OrderDTO {

    private Long id;
    private Long customerId;
    private OrderStatus status;
    private BigDecimal subtotal;
    private BigDecimal total;
    private LocalDateTime createdAt;

    private List<OrderItemDTO> items;

    // --- Getters/Setters ---
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public BigDecimal getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public List<OrderItemDTO> getItems() {
        return items;
    }
    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }

    // --- Mapeo helper ---
    public static OrderDTO fromEntity(Order entity) {
        OrderDTO dto = new OrderDTO();
        dto.setId(entity.getId());
        dto.setCustomerId(entity.getCustomer() != null ? entity.getCustomer().getId() : null);
        dto.setStatus(entity.getStatus());
        dto.setSubtotal(entity.getSubtotal());
        dto.setTotal(entity.getTotal());
        dto.setCreatedAt(entity.getCreatedAt());

        List<OrderItemDTO> itemDTOs = entity.getItems()
                .stream()
                .map(OrderItemDTO::fromEntity)
                .collect(Collectors.toList());
        dto.setItems(itemDTOs);

        return dto;
    }
}
