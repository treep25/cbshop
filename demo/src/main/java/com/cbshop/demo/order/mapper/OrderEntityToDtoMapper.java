package com.cbshop.demo.order.mapper;

import com.cbshop.demo.order.model.Order;
import com.cbshop.demo.order.model.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderEntityToDtoMapper {
    public OrderDTO entityToDto(Order order) {
        return OrderDTO.builder()
                .product(order.getProduct())
                .user(order.getUser())
                .price(order.getPrice())
                .createDate(order.getCreateDate())
                .guarantee(order.getGuarantee())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    public Page<OrderDTO> entityListToDtoList(Page<Order> orders) {
        List<OrderDTO> productDTOS = new ArrayList<>();
        orders.forEach(order -> productDTOS.add(entityToDto(order)));
        return new PageImpl<>(productDTOS, orders.getPageable(), productDTOS.size());
    }
}
