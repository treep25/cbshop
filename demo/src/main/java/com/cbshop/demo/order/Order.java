package com.cbshop.demo.order;

import com.cbshop.demo.product.model.Product;
import com.cbshop.demo.user.model.User;
import jakarta.persistence.*;
import lombok.*;

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
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private User user;
    private int price;
    //todo data purchasasise
}
