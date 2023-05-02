package com.cbshop.demo.basket.controller;

import com.cbshop.demo.session.SessionManager;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/basket")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BasketController {

    private final SessionManager sessionManager;


    @GetMapping
    public ResponseEntity<?> read(HttpSession session) {
        return ResponseEntity.ok(sessionManager.getBasketFromSession(session));
    }

    @PostMapping
    public ResponseEntity<?> create(HttpSession session, @RequestParam List<Long> productIds) {
        sessionManager.addSomeProductsToBasket(session,productIds);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(HttpSession session, @RequestParam List<Long> productIds) {
        sessionManager.removeSomeProductsFromBasket(session,productIds);

        return ResponseEntity.noContent().build();
    }
}
