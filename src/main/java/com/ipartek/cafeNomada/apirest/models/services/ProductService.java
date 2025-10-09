package com.ipartek.cafeNomada.apirest.models.services;

import org.springframework.stereotype.Service;

import com.ipartek.cafeNomada.apirest.models.dao.ProductRepository;
import com.ipartek.cafeNomada.apirest.models.entities.Product;
import com.ipartek.cafeNomada.apirest.models.entities.Category;

import java.util.List;
import java.util.Optional;

@Service  // Indica a Spring que esta clase es un servicio (la gestionará automáticamente)
public class ProductService {

    private final ProductRepository productRepository;

    // Inyección de dependencias: Spring "inyecta" el repositorio aquí automáticamente
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // --- Métodos CRUD básicos ---

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByCategory(Category category){
        return productRepository.findByCategory(category);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
