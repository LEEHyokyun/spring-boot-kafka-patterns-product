package com.msa.product.model.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProductCreateRequest {
    private String productName;
    private Long productQty;
    private Long productPrice;
}
