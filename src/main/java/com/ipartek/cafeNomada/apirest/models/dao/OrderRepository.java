package com.ipartek.cafeNomada.apirest.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.cafeNomada.apirest.models.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Carga un pedido junto con sus items (evita N+1).
     * No sobreescribe findById; usamos un nombre propio.
     */
    @EntityGraph(attributePaths = {"items"})
    Optional<Order> findWithItemsById(Long id);

    /**
     * Pedidos de un cliente (recientes primero) cargando items para evitar N+1.
     */
    @EntityGraph(attributePaths = {"items"})
    List<Order> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
}

