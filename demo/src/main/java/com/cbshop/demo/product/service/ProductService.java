package com.cbshop.demo.product.service;

import com.cbshop.demo.exceptions.controlleradvice.ItemNotFoundException;
import com.cbshop.demo.product.builder.ProductUpdateActionsBuilder;
import com.cbshop.demo.product.mapper.ProductDtoToEntityMapper;
import com.cbshop.demo.product.model.Product;
import com.cbshop.demo.product.model.ProductDTO;
import com.cbshop.demo.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@EnableTransactionManagement
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductUpdateActionsBuilder productUpdateActionsBuilder;
    private final ProductDtoToEntityMapper productDtoToEntityMapper;

    public Product createProduct(ProductDTO productDTO) {
        Product product = productDtoToEntityMapper.dtoToEntity(productDTO);
        return productRepository.save(product);
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("There is no such product"));
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product updateProduct(ProductDTO productDTO, long id) {
        Product product = getById(id);
        return productUpdateActionsBuilder.buildUpdatesProduct(productDTO, product);
    }
}
