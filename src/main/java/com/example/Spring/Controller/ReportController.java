package com.example.Spring.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Spring.Dao.OrderDetailDAO;
import com.example.Spring.Dao.ProductDAO;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api")
public class ReportController {
    @Autowired
    ProductDAO dao;

    @Autowired
    OrderDetailDAO daood;
    // @RequestMapping("/report/inventory-by-category")
    // public String inventory(Model model) {
    //     List<Report> items = dao.getInventoryByCategory();
    //     model.addAttribute("items", items);
    //     return "report/inventory-by-category";
    // }

    @GetMapping("/report")
    public List<Map<String, Object>> getReportByUsername() {
        List<Object[]> results = daood.findTop10UsersByTotalSpent();
        
        List<Map<String, Object>> response = new ArrayList<>();
        
        for (Object[] row : results) {
            Map<String, Object> orderInfo = new HashMap<>();
            orderInfo.put("username", row[0]);      // Sản phẩm
            orderInfo.put("fullname", row[1]);     // Số lượng
            orderInfo.put("price", row[2]);   // Tổng giá (price * quantity)
            response.add(orderInfo);
        }
        
        return response;
    }
}
