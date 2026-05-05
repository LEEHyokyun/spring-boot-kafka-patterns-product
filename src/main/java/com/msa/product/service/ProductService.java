package com.msa.product.service;

import com.msa.product.infra.event.Event;
import com.msa.product.infra.event.payload.EventPayload;
import com.msa.product.model.entity.Product;
import com.msa.product.model.request.ProductCreateRequest;
import com.msa.product.model.response.ProductListResponse;
import com.msa.product.model.response.ProductResponse;
import com.msa.product.repository.ProductRepository;
import com.msa.product.infra.event.eventhandler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse create(ProductCreateRequest productCreateRequest) {
        Product product = Product.create(
                productCreateRequest.getProductName(),
                productCreateRequest.getProductQty(),
                productCreateRequest.getProductPrice()
        );

        productRepository.save(product);

        /*
        * created product
        * */
        return ProductResponse.from(product);
    }

    public ProductResponse readProduct(Long productId) {
        return ProductResponse.from(productRepository.findById(productId).orElse(null));
    }

    public ProductListResponse readAllProducts() {
        return ProductListResponse.from(
                productRepository.findAll()
        );
    }

}
