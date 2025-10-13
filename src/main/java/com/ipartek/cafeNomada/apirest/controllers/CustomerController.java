package com.ipartek.cafeNomada.apirest.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ipartek.cafeNomada.apirest.models.entities.Customer;
import com.ipartek.cafeNomada.apirest.models.services.CustomerService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200") 
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    // Inyección por constructor
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // GET /api/customers -> lista todos los clientes
    @GetMapping("/")
    public List<Customer> getAll() {
        return customerService.getAllCustomers();
    }

    // GET /api/customers/{id} -> obtener un cliente por id
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Long id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/customers/new -> crear un nuevo cliente
    @PostMapping("/new")
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        try {
            Customer saved = customerService.createCustomer(customer);
            return ResponseEntity
                    .created(URI.create("/api/customers/" + saved.getId()))
                    .body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT /api/customers/{id} -> actualizar un cliente
    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer customer) {
        Optional<Customer> existing = customerService.getCustomerById(id);

        if (existing.isPresent()) {
            customer.setId(id);
            Customer updated = customerService.updateCustomer(customer);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/customers/{id} -> eliminar un cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (customerService.getCustomerById(id).isPresent()) {
            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
