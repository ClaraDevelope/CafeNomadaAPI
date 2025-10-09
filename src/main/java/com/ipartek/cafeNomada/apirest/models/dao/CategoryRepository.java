package com.ipartek.cafeNomada.apirest.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ipartek.cafeNomada.apirest.models.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Para evitar duplicados más adelante:
    boolean existsByNameIgnoreCase(String name);
}
