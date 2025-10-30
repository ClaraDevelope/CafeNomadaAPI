package com.ipartek.cafeNomada.apirest.entities;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

import com.ipartek.cafeNomada.apirest.models.entities.Cart;
import com.ipartek.cafeNomada.apirest.models.entities.CartItem;
import com.ipartek.cafeNomada.apirest.models.entities.Product;

class CartTest {

    private Product p(long id, String name, String price) {
        Product p = new Product();
        p.setId(id);
        p.setName(name);
        p.setPrice(new BigDecimal(price));
        return p;
    }

    private CartItem item(Product p, int qty) {
        CartItem ci = new CartItem();
        ci.setProduct(p);
        ci.setUnitPrice(p.getPrice()); // snapshot
        ci.setQuantity(qty);
        return ci;
    }

    @Test
    void addItem_mismoProducto_fusionaCantidad_ySubtotal() {
        Cart cart = new Cart();
        Product cafe = p(1L, "Café", "8.50");

        cart.addItem(item(cafe, 1));
        cart.addItem(item(cafe, 2)); // suma cantidades

        assertEquals(1, cart.getItems().size());
        assertEquals(3, cart.getItems().get(0).getQuantity());
        assertEquals(new BigDecimal("25.50"), cart.getSubtotal()); // 8.50 * 3
    }

    @Test
    void removeItem_eliminaLinea() {
        Cart cart = new Cart();
        Product cafe = p(1L, "Café", "8.50");
        Product te   = p(2L, "Té",   "3.00");

        cart.addItem(item(cafe, 2)); // 17.00
        cart.addItem(item(te,   1)); // +3.00 = 20.00

        cart.removeItem(1L); // quita café

        assertEquals(1, cart.getItems().size());
        assertEquals(new BigDecimal("3.00"), cart.getSubtotal());
    }

    @Test
    void clear_vaciaCarrito() {
        Cart cart = new Cart();
        cart.addItem(item(p(1L, "Café", "8.50"), 1));
        cart.addItem(item(p(2L, "Té",   "3.00"), 2));

        cart.clear();

        assertTrue(cart.getItems().isEmpty());
        assertEquals(BigDecimal.ZERO, cart.getSubtotal());
    }

    @Test
    void lineTotal_correcto() {
        CartItem ci = item(p(5L, "Molido", "12.40"), 4);
        assertEquals(new BigDecimal("49.60"), ci.getLineTotal());
    }
}
