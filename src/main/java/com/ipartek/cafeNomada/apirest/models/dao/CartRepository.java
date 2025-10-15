package com.ipartek.cafeNomada.apirest.models.dao;

import com.ipartek.cafeNomada.apirest.models.entities.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // Obtener el carrito por id de cliente
    Optional<Cart> findByCustomerId(Long customerId);

    // Versión que carga los items en la misma consulta (evita N+1)
    @EntityGraph(attributePaths = {"items", "items.product"})
    Optional<Cart> findWithItemsByCustomerId(Long customerId);

    // Saber si el cliente ya tiene carrito
    boolean existsByCustomerId(Long customerId);
}
