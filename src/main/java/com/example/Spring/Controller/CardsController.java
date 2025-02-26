package com.example.Spring.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Spring.Dao.AccountDAO;
import com.example.Spring.Dao.CardsDAO;
import com.example.Spring.Dao.ProductDAO;
import com.example.Spring.Entity.Account;
import com.example.Spring.Entity.Cards;
import com.example.Spring.Entity.Product;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class CardsController {
    @Autowired
    CardsDAO dao;

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    ProductDAO productDAO;

    @GetMapping("/cards/products")
public ResponseEntity<List<Map<String, Object>>> getCartProducts(@RequestParam String username) {
    List<Object[]> results = dao.findCartProductsByUsername(username);

    if (results.isEmpty()) return ResponseEntity.notFound().build();

    List<Map<String, Object>> cartItems = results.stream().map(row -> {
        Product product = (Product) row[0];
        Long quantity = (Long) row[1];

        Map<String, Object> item = new HashMap<>();
        item.put("id", product.getId());
        item.put("name", product.getName());
        item.put("price", product.getPrice());
        item.put("image", product.getImage());
        item.put("quantity", quantity);
        return item;
    }).collect(Collectors.toList());

    return ResponseEntity.ok(cartItems);
}


    @PostMapping("/cards/create")
    public ResponseEntity<?> createCard(@RequestBody Map<String, Object> payload) {
        try {
            String username = (String) payload.get("username");
            Integer productId = (Integer) payload.get("productId");

            if (username == null || productId == null) {
                return ResponseEntity.badRequest().body("Lỗi: Dữ liệu không hợp lệ!");
            }

            Account user = accountDAO.findByUsername(username).orElse(null);
            Product product = productDAO.findById(productId).orElse(null);

            if (user == null || product == null) {
                return ResponseEntity.badRequest().body("Lỗi: Username hoặc Product ID không tồn tại!");
            }

            Cards card = new Cards();
            card.setUser(user);
            card.setProduct(product);

            Cards savedCard = dao.save(card);
            return ResponseEntity.ok(savedCard);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi server: " + e.getMessage());
        }
    }

    @DeleteMapping("/cards/product/{productId}")
    public ResponseEntity<String> deleteCard(@PathVariable Long productId, @RequestParam String username) {
        try {
            dao.deleteByProductIdAndUsername(productId, username);
            return ResponseEntity.ok("Sản phẩm đã được xóa khỏi giỏ hàng.");
        }catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi server khi xóa sản phẩm.");
        }
    }

    @DeleteMapping("/cards/product/delete/{username}")
    public ResponseEntity<String> deleteCardAll(@RequestBody String username) {
        try {
            dao.deleteByUsername(username);
            return ResponseEntity.ok("Tất cả sản phẩm đã xóa khỏi giỏ hàng.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Lỗi khi xóa tất cả sản phẩm.");
        }
    }
}
