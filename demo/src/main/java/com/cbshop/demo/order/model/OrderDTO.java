package com.cbshop.demo.order.model;

import com.cbshop.demo.order.enums.OrderStatus;
import com.cbshop.demo.product.model.Product;
import com.cbshop.demo.user.model.User;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class OrderDTO {
    private Product product;
    private User user;
    private Integer price;
    private Date createDate;
    private Date guarantee;
    private OrderStatus orderStatus;
}
