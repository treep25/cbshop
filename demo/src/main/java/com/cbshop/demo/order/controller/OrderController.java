package com.cbshop.demo.order.controller;

import com.cbshop.demo.order.mapper.OrderEntityToDtoMapper;
import com.cbshop.demo.order.model.Order;
import com.cbshop.demo.order.service.OrderService;
import com.cbshop.demo.user.model.User;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Validated
public class OrderController {
    private final OrderService orderService;
    private final OrderEntityToDtoMapper orderEntityToDtoMapper;

    @PostMapping("/{productId}")
    public ResponseEntity<?> createOrder(@AuthenticationPrincipal User user,
                                         @PathVariable("productId")
                                         @Min(value = 1, message = "ProductId should be >= 1") long productId,
                                         @RequestParam(value = "page", defaultValue = "0")
                                         @Min(value = 0, message = "Discount should be >= 0") int guaranteeDays) {
        Order order = orderService.createOrder(user.getId(), productId, guaranteeDays);
        return new ResponseEntity<>(Map.of("order", orderEntityToDtoMapper.entityToDto(order)), HttpStatus.CREATED);
    }

    @GetMapping("/by-user-id/{id}")
    @PreAuthorize("@permissionComponent.hasPermission(#user,#id)")
    public ResponseEntity<?> getOrdersByUserId(@AuthenticationPrincipal User user,
                                               @PathVariable long id,
                                               @RequestParam(value = "page", defaultValue = "0")
                                               @Min(value = 0, message = "Page index should be >= 0") int page,
                                               @RequestParam(value = "size", defaultValue = "10")
                                               @Min(value = 1, message = "Size should be should be >= 1") int size) {
        Page<Order> order = orderService.getOrdersByUserId(id, page, size);
        return ResponseEntity.ok(Map.of("userOrders", orderEntityToDtoMapper.entityListToDtoList(order)));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable
                                          @Min(value = 1, message = "Size should be should be >= 1") long orderId) {
        Order orderById = orderService.getOrderById(orderId);
        return ResponseEntity.ok(Map.of("order", orderEntityToDtoMapper.entityToDto(orderById)));
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders(@RequestParam(value = "page", defaultValue = "0")
                                          @Min(value = 0, message = "Page index should be >= 0") int page,
                                          @RequestParam(value = "size", defaultValue = "20")
                                          @Min(value = 1, message = "Size should be should be >= 10") int size) {
        Page<Order> orders = orderService.getAllOrders(page, size);
        return ResponseEntity.ok(Map.of("orders", orderEntityToDtoMapper.entityListToDtoList(orders)));
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable
                                         @Min(value = 0, message = "Page index should be >= 0") long orderId) {
        Order order = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(Map.of("orders", orderEntityToDtoMapper.entityToDto(order)));
    }
}