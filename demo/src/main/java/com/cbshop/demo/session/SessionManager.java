package com.cbshop.demo.session;

import com.cbshop.demo.basket.service.BasketService;
import com.cbshop.demo.product.model.Product;
import com.cbshop.demo.user.model.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SessionManager {

    private final BasketService basketService;
    private static final String ID = "Id";
    private static final String EMAIL = "Email";
    private static final String ROLE = "Role";

    public void addSessionsParam(HttpSession session, User user) {
        session.setAttribute(ID, user.getId());
        session.setAttribute(EMAIL, user.getEmail());
        session.setAttribute(ROLE, user.getRole());
    }

    public void clearBasket(HttpSession session) {
        basketService.clearBasket(session);
    }

    public void removeSomeProductsFromBasket(HttpSession session, List<Long> productIds) {
        basketService.removeFromBasket(session, productIds);
    }

    public void addSomeProductsToBasket(HttpSession session, List<Long> productIds) {
        basketService.addProductsToBasket(session, productIds);
    }

    public Optional<List<Product>> getBasketFromSession(HttpSession session) {
        return Optional.ofNullable(basketService.getBasket(session));
    }

    public long getUserId(HttpSession session) {
        return (long) session.getAttribute(ID);
    }
}
