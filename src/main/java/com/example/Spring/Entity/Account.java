package com.example.Spring.Entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@Table(name = "Accounts")
public class Account implements Serializable {
    @Id
    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Vui lòng nhập lại username!")
    String username;
    String password;
    String fullname;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Vui lòng nhập lại email!")
    String email;
    String photo;
    boolean activated;
    boolean admin;
    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "account")
    List<Order> orders;
}