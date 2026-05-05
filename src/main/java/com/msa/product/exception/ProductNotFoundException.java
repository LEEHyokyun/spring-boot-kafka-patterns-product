package com.msa.product.exception;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(Long productId) {
        super("해당 제품[" + productId + "]은 존재하지 않는 제품입니다.");
    }

}
