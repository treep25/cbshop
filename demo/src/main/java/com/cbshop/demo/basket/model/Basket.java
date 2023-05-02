package com.cbshop.demo.basket.model;

import com.cbshop.demo.product.model.Product;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Basket {
    private List<Product> basketOfProducts;
}
