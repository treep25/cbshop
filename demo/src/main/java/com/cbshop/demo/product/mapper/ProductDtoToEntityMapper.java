package com.cbshop.demo.product.mapper;

import com.cbshop.demo.product.model.Product;
import com.cbshop.demo.product.model.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoToEntityMapper {
    public Product dtoToEntity(ProductDTO productDTO){
        return Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .category(productDTO.getCategory())
                .currency(productDTO.getCurrency())
                .build();
    }
}
