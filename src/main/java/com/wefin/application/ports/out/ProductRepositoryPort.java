package com.wefin.application.ports.out;

import com.wefin.domain.entities.Product;
import com.wefin.domain.entities.ProductConversionRule;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {

    Product save(Product product);
    ProductConversionRule save(ProductConversionRule rule);
    Optional<Product> findProductById(Long id);
    Optional<ProductConversionRule> findActiveConversionRule(Long productId, Long sourceCurrencyId, Long targetCurrencyId);
    List<ProductConversionRule> findByProductIdAndSourceCurrencyCodeAndTargetCurrencyCode(Long productId,
            String sourceCurrencyCode, String targetCurrencyCode);

}