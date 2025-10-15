package com.ipartek.cafeNomada.apirest.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ipartek.cafeNomada.apirest.models.dto.OrderDTO;
import com.ipartek.cafeNomada.apirest.models.entities.Order;
import com.ipartek.cafeNomada.apirest.models.services.OrderService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Crea un pedido a partir del carrito del cliente y lo devuelve.
     * POST /api/orders/checkout/{customerId}
     */
    @PostMapping("/checkout/{customerId}")
    public ResponseEntity<OrderDTO> checkout(@PathVariable Long customerId) {
        Order order = orderService.checkout(customerId);
        OrderDTO dto = OrderDTO.fromEntity(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    /**
     * Obtiene un pedido por id con sus items.
     * GET /api/orders/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getById(@PathVariable Long id) {
        Order order = orderService.getByIdWithItems(id);
        return ResponseEntity.ok(OrderDTO.fromEntity(order));
    }

    /**
     * Lista pedidos de un cliente (recientes primero) con sus items.
     * GET /api/orders/customer/{customerId}
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDTO>> getByCustomer(@PathVariable Long customerId) {
        List<OrderDTO> list = orderService.getByCustomer(customerId)
                .stream()
                .map(OrderDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
}
