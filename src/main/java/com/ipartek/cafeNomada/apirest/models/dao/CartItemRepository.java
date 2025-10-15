package com.ipartek.cafeNomada.apirest.models.dao;

import com.ipartek.cafeNomada.apirest.models.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Listar items de un carrito
    List<CartItem> findByCartId(Long cartId);

    // Buscar una línea concreta por carrito y producto (útil para sumar cantidades)
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

    // Borrar todas las líneas de un carrito (vaciar carrito)
    void deleteByCartId(Long cartId);

    // Borrar una línea concreta
    void deleteByCartIdAndProductId(Long cartId, Long productId);
}
