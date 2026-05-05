package com.msa.product.controller;

import com.msa.product.model.request.ProductCreateRequest;
import com.msa.product.model.response.ProductListResponse;
import com.msa.product.model.response.ProductResponse;
import com.msa.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* model 효율화
* - create, update 세부 목적 등으로 Request Object 운용(처리과정 및 세부 로직 등으로 인해 필요 항목이 다를 수 있음).
* - request Object -> controller -> User Entity(domain 규칙을 포함한 엔티티 create) -> service -> Response (from domain entity)
* - Response Object는 처리 시 활용한 객체 상태/값들을 보여주는 목적으로 분리하지 않고 일괄 운용
* */
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/health-check")
    public String status(){
        return String.format("Now Working");
    }

    @GetMapping("/products")
    public ProductListResponse readAllProducts(){

        return productService.readAllProducts();
    }

    @GetMapping("/products/{productId}")
    public ProductResponse readProduct(@PathVariable("productId") Long userId){

        return productService.readProduct(userId);
    }

    @PostMapping("/products")
    public ProductResponse create(@RequestBody ProductCreateRequest request){

        return productService.create(request);
    }

}
