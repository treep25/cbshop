package com.cbshop.demo.session;

import com.cbshop.demo.basket.service.BasketService;
import com.cbshop.demo.product.model.Product;
import com.cbshop.demo.user.model.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SessionManager {

    private final BasketService basketService;
    public void addSessionsParam(HttpSession session, User user) {
        session.setAttribute("Id", user.getId());
        session.setAttribute("Email", user.getEmail());
        session.setAttribute("Role", user.getRole());
    }

    public void clearBasket(HttpSession session){
        basketService.clearBasket(session);
    }

    public void removeSomeProductsFromBasket(HttpSession session, List<Long> productIds){
        basketService.removeFromBasket(session, productIds);
    }

    public void addSomeProductsToBasket(HttpSession session, List<Long> productIds) {
        basketService.addProductsToBasket(session, productIds);
    }

    public List<Product> getBasketFromSession(HttpSession session) {
        return basketService.getBasket(session);
    }

    public long getUserId(HttpSession session) {
        return (long) session.getAttribute("Id");
    }
}
