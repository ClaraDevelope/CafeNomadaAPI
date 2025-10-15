package com.ipartek.cafeNomada.apirest.models.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ipartek.cafeNomada.apirest.models.dao.CartRepository;
import com.ipartek.cafeNomada.apirest.models.dao.OrderRepository;
import com.ipartek.cafeNomada.apirest.models.entities.Cart;
import com.ipartek.cafeNomada.apirest.models.entities.CartItem;
import com.ipartek.cafeNomada.apirest.models.entities.Order;
import com.ipartek.cafeNomada.apirest.models.entities.OrderItem;
import com.ipartek.cafeNomada.apirest.models.entities.OrderStatus;
import com.ipartek.cafeNomada.apirest.models.entities.Product;

@Service
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartService cartService; // ya existente en tu proyecto

    public OrderService(CartRepository cartRepository,
                        OrderRepository orderRepository,
                        CartService cartService) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    /**
     * Realiza el checkout:
     *  - Lee el carrito con items del cliente
     *  - Crea Order y OrderItem con "snapshots"
     *  - Calcula subtotal y total
     *  - Guarda el pedido
     *  - Vacía el carrito
     *  - Devuelve el Order creado
     */
    @Transactional
    public Order checkout(Long customerId) {
        Cart cart = cartRepository.findWithItemsByCustomerId(customerId)
                .orElseThrow(() -> new IllegalStateException("El cliente no tiene carrito."));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new IllegalStateException("El carrito está vacío.");
        }
        if (cart.getCustomer() == null) {
            throw new IllegalStateException("El carrito no tiene asociado un cliente.");
        }

        Order order = new Order();
        order.setCustomer(cart.getCustomer());
        order.setStatus(OrderStatus.CREATED);

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (CartItem ci : cart.getItems()) {
            // Validaciones defensivas para evitar NPE
            if (ci == null) {
                throw new IllegalStateException("Se encontró un item nulo en el carrito.");
            }
            if (ci.getQuantity() == null) {
                throw new IllegalStateException("Cantidad nula en un item del carrito.");
            }
            if (ci.getUnitPrice() == null) {
                throw new IllegalStateException("Precio unitario nulo en un item del carrito.");
            }

            Product p = ci.getProduct();
            if (p == null) {
                throw new IllegalStateException("El item del carrito no tiene producto asociado.");
            }
            if (p.getId() == null) {
                throw new IllegalStateException("El producto del item no tiene ID.");
            }
            // Si el nombre real del campo no es 'name', dímelo y lo adapto.
            if (p.getName() == null) {
                throw new IllegalStateException("El producto con id " + p.getId() + " no tiene nombre.");
            }

            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProductId(p.getId());
            oi.setProductName(p.getName());
            oi.setUnitPrice(ci.getUnitPrice());
            oi.setQuantity(ci.getQuantity());

            BigDecimal lineTotal = ci.getUnitPrice().multiply(BigDecimal.valueOf(ci.getQuantity()));
            oi.setLineTotal(lineTotal);

            orderItems.add(oi);
            subtotal = subtotal.add(lineTotal);
        }

        order.setItems(orderItems);
        order.setSubtotal(subtotal);
        order.setTotal(subtotal); // por ahora sin gastos ni descuentos

        Order saved = orderRepository.save(order);

        // Vaciar carrito tras confirmar que el pedido se guardó
        cartService.clearCart(customerId);

        return saved;
    }

    /** Obtiene un pedido por id con sus items (evita N+1). */
    @Transactional(readOnly = true)
    public Order getByIdWithItems(Long id) {
        return orderRepository.findWithItemsById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado: " + id));
    }

    /** Lista pedidos por cliente (más recientes primero) con items. */
    @Transactional(readOnly = true)
    public List<Order> getByCustomer(Long customerId) {
        return orderRepository.findByCustomerIdOrderByCreatedAtDesc(customerId);
    }
}
