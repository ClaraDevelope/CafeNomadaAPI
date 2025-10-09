package com.ipartek.cafeNomada.apirest.models.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ipartek.cafeNomada.apirest.models.dao.CategoryRepository;
import com.ipartek.cafeNomada.apirest.models.entities.Category;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // Inyección por constructor (recomendada)
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // --- CRUD básico ---

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category create(Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new IllegalArgumentException("La categoría ya existe");
        }
        return categoryRepository.save(category);
    }

    // public Category update(Long id, Category data) {
    //     return categoryRepository.findById(id)
    //             .map(cat -> {
    //                 cat.setName(data.getName());
    //                 return categoryRepository.save(cat);
    //             })
    //             .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
    // }

    // public void delete(Long id) {
    //     categoryRepository.deleteById(id);
    // }
}
