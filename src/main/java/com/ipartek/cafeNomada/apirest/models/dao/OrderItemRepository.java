package com.ipartek.cafeNomada.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.cafeNomada.apirest.models.entities.OrderItem;

/**
 * Acceso a datos para las líneas de pedido.
 * Por ahora no necesitamos queries personalizadas:
 * cargaremos los items a través del Order con @EntityGraph en OrderRepository.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // Intencionadamente vacío (CRUD heredado).
}
