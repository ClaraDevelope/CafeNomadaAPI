package com.ipartek.cafeNomada.apirest.controllers;

import com.ipartek.cafeNomada.apirest.models.dto.AddItemRequest;
import com.ipartek.cafeNomada.apirest.models.dto.CartDTO;
import com.ipartek.cafeNomada.apirest.models.dto.CartItemDTO;
import com.ipartek.cafeNomada.apirest.models.dto.UpdateQuantityRequest;
import com.ipartek.cafeNomada.apirest.models.entities.Cart;
import com.ipartek.cafeNomada.apirest.models.entities.CartItem;
import com.ipartek.cafeNomada.apirest.models.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carts")
@CrossOrigin(origins = "http://localhost:4200")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 🔹 Obtener carrito con sus items
    @GetMapping("/{customerId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long customerId) {
        Cart cart = cartService.getCartWithItems(customerId);
        return ResponseEntity.ok(toDTO(cart));
    }

    // 🔹 Añadir producto al carrito
    @PostMapping("/{customerId}/items")
    public ResponseEntity<CartDTO> addItem(@PathVariable Long customerId,
                                           @RequestBody AddItemRequest req) {
        Cart cart = cartService.addItem(customerId, req.getProductId(), req.getQuantity());
        return ResponseEntity.ok(toDTO(cart));
    }

    // 🔹 Actualizar cantidad (si quantity <= 0, se elimina)
    @PatchMapping("/{customerId}/items/{productId}")
    public ResponseEntity<CartDTO> updateQuantity(@PathVariable Long customerId,
                                                  @PathVariable Long productId,
                                                  @RequestBody UpdateQuantityRequest req) {
        Cart cart = cartService.updateQuantity(customerId, productId, req.getQuantity());
        return ResponseEntity.ok(toDTO(cart));
    }

    // 🔹 Eliminar un item del carrito
    @DeleteMapping("/{customerId}/items/{productId}")
    public ResponseEntity<CartDTO> removeItem(@PathVariable Long customerId,
                                              @PathVariable Long productId) {
        Cart cart = cartService.removeItem(customerId, productId);
        return ResponseEntity.ok(toDTO(cart));
    }

    // 🔹 Vaciar completamente el carrito
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long customerId) {
        cartService.clearCart(customerId);
        return ResponseEntity.noContent().build();
    }

    // ────────────────────────────────────────────────
    // 🔸 MÉTODOS PRIVADOS DE CONVERSIÓN A DTO
    // ────────────────────────────────────────────────

    private CartDTO toDTO(Cart cart) {
        CartDTO dto = new CartDTO();
        dto.setCartId(cart.getId());
        dto.setCustomerId(cart.getCustomer().getId());

        var items = cart.getItems()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        dto.setItems(items);

        // Cálculo subtotal y total
        BigDecimal subtotal = items.stream()
                .map(CartItemDTO::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        dto.setSubtotal(subtotal);
        dto.setTotal(subtotal);
        dto.setUpdatedAt(cart.getUpdatedAt());

        return dto;
    }

    private CartItemDTO toDTO(CartItem item) {
        CartItemDTO dto = new CartItemDTO();
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getName()); // ajusta si tu Product tiene otro campo
        dto.setUnitPrice(item.getUnitPrice());
        dto.setQuantity(item.getQuantity());
        dto.setLineTotal(item.getLineTotal());
        return dto;
    }
}
