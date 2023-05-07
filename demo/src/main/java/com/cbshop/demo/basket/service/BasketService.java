package com.cbshop.demo.basket.service;

import com.cbshop.demo.product.model.Product;
import com.cbshop.demo.product.repository.ProductRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BasketService {

    private final ProductRepository productRepository;
    private final static String BASKET = "basket";

    public void addProductsToBasket(HttpSession session, List<Long> productIds) {
        session.setAttribute(BASKET, productRepository.findAllById(productIds));
    }
    @SuppressWarnings("unchecked")
    public void removeFromBasket(HttpSession session, List<Long> productIds) {
        List<Product> basket = (List<Product>) session.getAttribute(BASKET);

        basket.removeIf(product -> productIds.contains(product.getId()));

        session.setAttribute(BASKET, basket);
    }

    public void clearBasket(HttpSession session){
        session.removeAttribute(BASKET);
    }

    @SuppressWarnings("unchecked")
    public List<Product> getBasket(HttpSession session){
        return (List<Product>) session.getAttribute(BASKET);
    }
}
