package com.ipartek.cafeNomada.apirest.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ipartek.cafeNomada.apirest.models.entities.Product;
import com.ipartek.cafeNomada.apirest.models.services.ProductService;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200") // permite peticiones desde tu Angular en local
@RestController
@RequestMapping("/api/products") // prefijo común para todos los endpoints

public class ProductController {

    private final ProductService productService;

    // inyección por constructor (Spring crea e inyecta el servicio)
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET /api/products  -> lista todos
    @GetMapping("/")
    public List<Product> getAll() {
        return productService.getAllProducts();
    }

    // GET /api/products/{id}  -> uno por id
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/products  -> crear
    @PostMapping("/new")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product saved = productService.saveProduct(product);
        return ResponseEntity
                .created(URI.create("/api/products/" + saved.getId()))
                .body(saved);
    }

    // PUT /api/products/{id}  -> actualizar completo
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        return productService.getProductById(id)
                .map(existing -> {
                    product.setId(id);                // aseguramos el id
                    Product updated = productService.saveProduct(product);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/products/{id}  -> borrar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (productService.getProductById(id).isPresent()) {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
