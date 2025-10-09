package com.ipartek.cafeNomada.apirest.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ipartek.cafeNomada.apirest.models.entities.Category;
import com.ipartek.cafeNomada.apirest.models.services.CategoryService;

@CrossOrigin(origins = "http://localhost:4200") 
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // GET /api/categories
    @GetMapping("/")
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    // GET /api/categories/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        return categoryService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /api/categories
    @PostMapping("/new")
    public ResponseEntity<Category> create(@RequestBody Category body) {
        Category created = categoryService.create(body);
        return ResponseEntity.created(URI.create("/api/categories/" + created.getId())).body(created);
    }

    // // PUT /api/categories/{id}
    // @PutMapping("/{id}")
    // public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category body) {
    //     try {
    //         Category updated = categoryService.update(id, body);
    //         return ResponseEntity.ok(updated);
    //     } catch (IllegalArgumentException e) {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // // DELETE /api/categories/{id}
    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> delete(@PathVariable Long id) {
    //     categoryService.delete(id);
    //     return ResponseEntity.noContent().build(); // 204, correcto en REST
    // }
}
