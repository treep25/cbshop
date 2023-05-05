package com.cbshop.demo.product.mapper;

import com.cbshop.demo.product.model.Product;
import com.cbshop.demo.product.model.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductEntityToDtoMapper {
    public ProductDTO entityToDto(Product product) {
        return ProductDTO.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .currency(product.getCurrency())
                .build();
    }

    public Page<ProductDTO> entityListToDtoList(Page<Product> products) {
        List<ProductDTO> productDTOS = new ArrayList<>();
        products.forEach(product -> productDTOS.add(entityToDto(product)));
        return new PageImpl<>(productDTOS, products.getPageable(), productDTOS.size());
    }
}
