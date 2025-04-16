package com.example.Spring.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Products")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    String image;
    @Min(value = 0, message = "Vui lòng nhập lại giá!")
    Double price;
    String description;
    @Min(value = 0, message = "Vui lòng nhập lại số lượng!")
    String quantity;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "Createdate")
    Date createDate = new Date();
    
    Boolean available;

    @ManyToOne
    @JoinColumn(name = "Categoryid")
    Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    List<OrderDetail> orderDetails;
}
