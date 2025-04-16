package com.example.Spring.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Spring.Dao.AccountDAO;
import com.example.Spring.Dao.CardsDAO;
import com.example.Spring.Dao.OrderDAO;
import com.example.Spring.Dao.OrderDetailDAO;
import com.example.Spring.Dto.Request.OrderRequest;
import com.example.Spring.Entity.Account;
import com.example.Spring.Entity.Cards;
import com.example.Spring.Entity.Order;
import com.example.Spring.Entity.OrderDetail;
import com.example.Spring.Entity.Product;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    OrderDAO dao;
    @Autowired
    OrderDetailDAO daodetail;
    @Autowired
    AccountDAO daoaccount;
    @Autowired
    CardsDAO cardsdao;

    @GetMapping("/order/all")
    public List<Order> getAllOrder() {
        return dao.findAll();
    }

    @GetMapping("/orderdetail/{id}")
    public ResponseEntity<List<OrderDetail>> getAllOrderDetailById(@PathVariable Long id) {
        return ResponseEntity.ok(daodetail.findByOrderId(id));
    }

    @PostMapping("/order/create")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody OrderRequest request) {
        Map<String, Object> response = new HashMap<>();

        Optional<Account> otlAccount = daoaccount.findByUsername(request.getUsername());
        if (otlAccount.isPresent()) {
            Account account = otlAccount.get();
            List<Cards> cardItem = cardsdao.findByUserUsername(request.getUsername());

            if (cardItem.isEmpty()) {
                System.out.println("Giỏ hàng trống!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            Map<Product, Integer> productCount = new HashMap<>();
            for (Cards card : cardItem) {
                productCount.put(card.getProduct(), productCount.getOrDefault(card.getProduct(), 0) + 1);
            }

            Order order = new Order();
            order.setAccount(account);
            order.setAddress(request.getAddress());
            order.setCreateDate(new Date());
            order.setStatus(0);
            dao.save(order);

            for (Map.Entry<Product, Integer> entry : productCount.entrySet()) {
                OrderDetail orderdt = new OrderDetail();
                orderdt.setOrder(order);
                orderdt.setProduct(entry.getKey());
                orderdt.setQuantity(entry.getValue());
                orderdt.setPrice((double) entry.getKey().getPrice());
                daodetail.save(orderdt);
            }

            cardsdao.deleteAll(cardItem);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PutMapping("/order/update/{id}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, Integer> request) {
        Optional<Order> optionalOrder = dao.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(request.get("status"));
            dao.save(order);
            return ResponseEntity.ok("Cập nhật trạng thái thành công!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy đơn hàng!");
    }

    @GetMapping("/order/myorder/{username}")
    public List<Map<String, Object>> getOrdersByUsername(@PathVariable String username) {
        List<Object[]> results = daodetail.findProductsByUsername(username);
        
        List<Map<String, Object>> response = new ArrayList<>();
        
        for (Object[] row : results) {
            Map<String, Object> orderInfo = new HashMap<>();
            orderInfo.put("product", row[0]);      // Sản phẩm
            orderInfo.put("quantity", row[1]);     // Số lượng
            orderInfo.put("totalPrice", row[2]);   // Tổng giá (price * quantity)
            orderInfo.put("status", row[3]);       // Trạng thái đơn hàng
            // orderInfo.put("orderId", row[4]);
            response.add(orderInfo);
        }
        
        return response;
    }
}
