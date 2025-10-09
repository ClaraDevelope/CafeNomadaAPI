package com.ipartek.cafeNomada.apirest.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.cafeNomada.apirest.models.entities.Product;
import com.ipartek.cafeNomada.apirest.models.entities.Category;

@Repository // opcional, pero no molesta
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Buscar productos por la entidad Category
    List<Product> findByCategory(Category category);

    // Útil si prefieres buscar por id sin cargar la entidad Category
    List<Product> findByCategoryId(Long categoryId);
}
