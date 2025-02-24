package com.example.Spring.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Spring.Dao.CategoryDAO;
import com.example.Spring.Entity.Category;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    CategoryDAO dao;

    @GetMapping("/categorys")
    public List<Category> getAllCategories() {
        return dao.findAll();
    }

    @PostMapping("/categorys/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(dao.save(category));
    }

    @PutMapping("/categorys/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable String id, @RequestBody Category category) {
        if (!dao.existsById(id)) return ResponseEntity.notFound().build();
        category.setId(id);
        return ResponseEntity.ok(dao.save(category));
    }

    @DeleteMapping("/categorys/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String id) {
        if (!dao.existsById(id)) return ResponseEntity.notFound().build();
        dao.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}