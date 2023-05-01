package com.cbshop.demo.product.builder;

import com.cbshop.demo.product.model.Product;
import com.cbshop.demo.product.model.ProductDTO;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
@Component
public class ProductUpdateActionsBuilder {
    private final Map<Predicate<ProductDTO>, BiConsumer<Product, ProductDTO>> updatesMap =
            Map.of(
                    currentProduct -> currentProduct.getName() != null, (productById, productUpdates) ->
                            productById.setName(productUpdates.getName()),

                    currentProduct -> currentProduct.getDescription() != null, (productById, productUpdates) ->
                            productById.setDescription(productUpdates.getDescription()),

                    currentProduct -> currentProduct.getPrice() != null, (productById, productUpdates) ->
                            productById.setPrice(productUpdates.getPrice()),

                    currentProduct -> currentProduct.getCategory() != null, (productById, productUpdates) ->
                            productById.setCategory(productUpdates.getCategory())
            );

    public Product buildUpdatesProduct(ProductDTO productUpdates, Product currentProduct) {
        updatesMap
                .forEach((userPredicate, userBiConsumer) -> {
                    if (userPredicate.test(productUpdates)) {
                        userBiConsumer.accept(currentProduct, productUpdates);
                    }
                });
        return currentProduct;
    }
}
