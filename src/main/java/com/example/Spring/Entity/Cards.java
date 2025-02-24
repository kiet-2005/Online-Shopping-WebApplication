package com.example.Spring.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "Cards")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Username", referencedColumnName = "username")
    @JsonIgnoreProperties("cards")
    private Account user;

    @ManyToOne
    @JoinColumn(name = "Productid", referencedColumnName = "id")
    @JsonIgnoreProperties("cards")
    private Product product;
}

