package com.msa.product.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msa.product.model.entity.Product;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
public class ProductListResponse {

    List<ProductResponse> productList;

    public static ProductListResponse fromResponse(List<ProductResponse> productResponseList){

        ProductListResponse productResponse = new ProductListResponse();

        productResponse.productList = productResponseList;

        return productResponse;
    }

    public static ProductListResponse from(List<Product> productList) {
        return ProductListResponse.fromResponse(productList.stream()
                .map(ProductResponse::from)
                .toList()
        );
    }
}
