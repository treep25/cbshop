package com.cbshop.demo.product.controller;

import com.cbshop.demo.product.mapper.ProductEntityToDtoMapper;
import com.cbshop.demo.product.model.Product;
import com.cbshop.demo.product.model.ProductDTO;
import com.cbshop.demo.product.service.ProductService;
import com.cbshop.demo.utils.DataValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {
    private final ProductEntityToDtoMapper productEntityToDtoMapper;
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> read(@RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "10") int size) {
        DataValidation.validatePageAndSizePagination(page, size);
        Page<Product> allProducts = productService.getAllProducts(PageRequest.of(page, size));
        return ResponseEntity.ok(productEntityToDtoMapper.entityListToDtoList(allProducts));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readById(@PathVariable long id) {
        DataValidation.isIdValid(id);
        Product product = productService.getById(id);
        return ResponseEntity.ok(productEntityToDtoMapper.entityToDto(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        DataValidation.isIdValid(id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody ProductDTO productDTO) {
        DataValidation.isIdValid(id);
        DataValidation.isProductValidForUpdate(productDTO);
        Product product = productService.updateProduct(productDTO, id);
        return ResponseEntity.ok(productEntityToDtoMapper.entityToDto(product));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductDTO productDTO) {
        DataValidation.isProductValidForCreate(productDTO);
        Product product = productService.createProduct(productDTO);
        return ResponseEntity.ok(productEntityToDtoMapper.entityToDto(product));
    }
}
