package com.msa.product.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;
    private Long productQty;
    private Long productPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Product create(String productName, Long productQty, Long productPrice){

        Product product = new Product();

        product.productName = productName;
        product.productQty = productQty;
        product.productPrice = productPrice;
        product.createdAt = LocalDateTime.now();
        product.updatedAt = product.createdAt;

        return product;
    }

    public void updateQty(Long orderedQty){

        this.productQty -= orderedQty;
        this.updatedAt = LocalDateTime.now();

    }
}
