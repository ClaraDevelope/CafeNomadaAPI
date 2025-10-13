package com.ipartek.cafeNomada.apirest.models.services;

import org.springframework.stereotype.Service;

import com.ipartek.cafeNomada.apirest.models.dao.ProductRepository;
import com.ipartek.cafeNomada.apirest.models.dao.CategoryRepository;
import com.ipartek.cafeNomada.apirest.models.entities.Product;
import com.ipartek.cafeNomada.apirest.models.entities.Category;

import java.util.List;
import java.util.Optional;

@Service  // Indica a Spring que esta clase es un servicio (la gestionará automáticamente)
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    // Inyección de dependencias: Spring "inyecta" los repositorios aquí automáticamente
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // --- Métodos CRUD básicos ---

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    public Product saveProduct(Product product) {

        // Si llega solo el id de categoría, la buscamos y la asociamos
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Optional<Category> categoryOpt = categoryRepository.findById(product.getCategory().getId());
            categoryOpt.ifPresent(product::setCategory);
        }

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

