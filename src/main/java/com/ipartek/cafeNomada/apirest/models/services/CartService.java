package com.ipartek.cafeNomada.apirest.models.services;

import com.ipartek.cafeNomada.apirest.models.entities.Cart;

public interface CartService {

    Cart getOrCreateCart(Long customerId);

    Cart getCartWithItems(Long customerId);

    Cart addItem(Long customerId, Long productId, int quantity);

    Cart updateQuantity(Long customerId, Long productId, int quantity);

    Cart removeItem(Long customerId, Long productId);

    void clearCart(Long customerId);
}
