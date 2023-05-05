package com.cbshop.demo.order.service;

import com.cbshop.demo.exceptions.controlleradvice.ItemNotFoundException;
import com.cbshop.demo.order.repository.OrderRepository;
import com.cbshop.demo.order.enums.OrderStatus;
import com.cbshop.demo.order.model.Order;
import com.cbshop.demo.product.model.Product;
import com.cbshop.demo.product.service.ProductService;
import com.cbshop.demo.user.model.User;
import com.cbshop.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@EnableTransactionManagement
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final UserService userService;
    private static final int GUARANTEE_1_DAY = 1000 * 60 * 60 * 24;

    @Transactional
    public Order createOrder(long user_id, long productId, int guaranteeDays) {
        Product product = productService.getById(productId);
        User user = userService.readById(user_id);
        Order newOrder = Order.builder().product(product)
                .user(user)
                .price(product.getPrice())
                .guarantee(getGuarantee(guaranteeDays))
                .orderStatus(OrderStatus.DONE)
                .build();
        return orderRepository.save(newOrder);
    }

    private Date getGuarantee(int guaranteeDays) {
        return new Date(System.currentTimeMillis() + convertDaysToMillis(guaranteeDays));
    }

    private Integer convertDaysToMillis(int guaranteeDays) {
        return guaranteeDays * GUARANTEE_1_DAY;
    }

    public Page<Order> getAllOrders(int page, int size) {
        return orderRepository.findAll(PageRequest.of(page, size));
    }

    public Page<Order> getOrdersByUserId(long user_id, int page, int size) {
        Page<Order> order = orderRepository.getOrdersByUserId(user_id, PageRequest.of(page, size));
        if (!order.isEmpty()) {
            return order;
        }
        throw new ItemNotFoundException("No orders where user_id = " + user_id);
    }

    public Order getOrderById(long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("No order with (id = " + id + ")"));
    }

    public Order cancelOrder(long orderID) {
        Order order = getOrderById(orderID);
        order.setOrderStatus(OrderStatus.CANCELED);
        return orderRepository.save(order);
    }
}
