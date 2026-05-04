package com.msa.product.service;

import com.msa.product.infra.event.Event;
import com.msa.product.infra.event.payload.EventPayload;
import com.msa.product.model.entity.Product;
import com.msa.product.model.request.ProductCreateRequest;
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

    /*
     * 이벤트핸들러(List)
     * Event 객체를 매개변수로 전달받아 해당하는 이벤트핸들러를 매핑해주기 위함
     * 참고로 Spring Framework에 의해 해당 리스트의 생성자 주입 시점에 해당 구현체들이 주입되어 할당됨
     * */
    private final List<EventHandler> eventHandlers;

    @Transactional
    public ProductResponse create(ProductCreateRequest productCreateRequest) {
        Product product = Product.create(
                productCreateRequest.getProductName(),
                productCreateRequest.getProductQty(),
                productCreateRequest.getProductPrice()
        );

        productRepository.save(product);

        return ProductResponse.from(product);
    }

    public ProductResponse readProduct(Long productId) {
        return ProductResponse.from(productRepository.findById(productId).orElse(null));
    }

    public List<ProductResponse> readAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponse::from)//Entity > Dto
                .toList();
    }

    @Transactional
    public void orderProducts(Event<EventPayload> event){
        EventHandler<EventPayload> eventHandler = findEventHandler(event);

        if(eventHandler == null){
            log.error("[ERROR][ProductService] No Such EventHandler found");
            return;
        }

        eventHandler.handle(event);
    }

    private EventHandler<EventPayload> findEventHandler(Event<EventPayload> event) {
        return eventHandlers.stream()
                .filter(eventHandler -> eventHandler.supports(event))
                .findAny()
                /*
                 * Wrapper -> Object
                 * */
                .orElse(null);
    }

}
