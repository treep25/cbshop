package com.cbshop.demo.product.model;

import com.cbshop.demo.product.enums.Category;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class ProductDTO {
    private String name;
    private String description;
    private Integer price;
    private Category category;
}
