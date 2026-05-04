package com.msa.product.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msa.product.model.entity.Product;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class ProductResponse {
    @JsonIgnore
    private Long productId;
    private String productName;
    private Long productQty;
    private Long productPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductResponse from(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.productId = product.getProductId();
        productResponse.productName = product.getProductName();
        productResponse.productQty = product.getProductQty();
        productResponse.productPrice = product.getProductPrice();
        productResponse.createdAt = product.getCreatedAt();
        productResponse.updatedAt = product.getUpdatedAt();

        return productResponse;
    }
}
