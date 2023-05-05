package com.cbshop.demo.order.model;

import com.cbshop.demo.order.enums.OrderStatus;
import com.cbshop.demo.product.model.Product;
import com.cbshop.demo.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Product product;
    @ManyToOne
    private User user;
    private Integer price;
    @CreatedDate
    private Date createDate;
    private Date guarantee;
    private OrderStatus orderStatus;
}
