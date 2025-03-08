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
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.Spring.Dao.ProductDAO;
import com.example.Spring.Entity.Product;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductDAO dao;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return dao.findAll();
    }

    @GetMapping("/detail/{id}")
    public Product getDetail(@PathVariable Integer id) {
        return dao.findById(id).orElse(null);
    }

    @GetMapping("/products/category/{categoryId}")
    public List<Product> getProductsByCategory(@PathVariable String categoryId) {
        return dao.findByCategoryId(categoryId);
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getByCategory(@PathVariable String categoryId) {
        return dao.findByCategoryId(categoryId);
    }

    @PostMapping("/admin/products/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(dao.save(product));
    }

    @PutMapping("/admin/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        if (!dao.existsById(id)) return ResponseEntity.notFound().build();
        product.setId(id);
        return ResponseEntity.ok(dao.save(product));
    }

    @DeleteMapping("/admin/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        if (!dao.existsById(id))
            return ResponseEntity.notFound().build();
        dao.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/products/{keyword}")
    public ResponseEntity<List<Product>> getProductsByKeyword(@PathVariable String keyword) {
        List<Product> products = dao.findAllByNameLike("%" + keyword + "%");
        
        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.ok(products);
    }
}