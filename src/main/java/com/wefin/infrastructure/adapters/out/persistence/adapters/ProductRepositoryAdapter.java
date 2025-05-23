package com.wefin.infrastructure.adapters.out.persistence.adapters;

import com.wefin.application.ports.out.ProductRepositoryPort;
import com.wefin.domain.entities.Product;
import com.wefin.domain.entities.ProductConversionRule;
import com.wefin.infrastructure.adapters.out.persistence.entities.ProductConversionRuleEntity;
import com.wefin.infrastructure.adapters.out.persistence.entities.ProductEntity;
import com.wefin.infrastructure.adapters.out.persistence.mappers.ProductConversionRuleMapper;
import com.wefin.infrastructure.adapters.out.persistence.mappers.ProductMapper;
import com.wefin.infrastructure.adapters.out.persistence.repositories.ProductConversionRuleRepository;
import com.wefin.infrastructure.adapters.out.persistence.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final ProductRepository productRepository;
    private final ProductConversionRuleRepository conversionRuleRepository;
    private final ProductMapper productMapper;
    private final ProductConversionRuleMapper conversionRuleMapper;

    @Override
    public Product save(Product product) {
        ProductEntity entity = productMapper.toEntity(product);
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(LocalDateTime.now());
        }
        entity.setUpdatedAt(LocalDateTime.now());
        ProductEntity savedEntity = productRepository.save(entity);
        return productMapper.toDomain(savedEntity);
    }

    @Override
    public ProductConversionRule save(ProductConversionRule rule) {
        ProductConversionRuleEntity entity = conversionRuleMapper.toEntity(rule);

        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(LocalDateTime.now());
        }
        entity.setUpdatedAt(LocalDateTime.now());

        ProductConversionRuleEntity savedEntity = conversionRuleRepository.save(entity);
        return conversionRuleMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDomain);
    }

    @Override
    public Optional<ProductConversionRule> findActiveConversionRule(Long productId, Long sourceCurrencyId, Long targetCurrencyId) {
        return conversionRuleRepository
                .findByProductIdAndSourceCurrencyIdAndTargetCurrencyIdAndActiveTrue(
                        productId, sourceCurrencyId, targetCurrencyId)
                .map(conversionRuleMapper::toDomain);
    }

    @Override
    public List<ProductConversionRule> findByProductIdAndSourceCurrencyCodeAndTargetCurrencyCode(Long productId, String sourceCurrencyCode, String targetCurrencyCode) {
        return conversionRuleRepository.findByCriteria(productId, sourceCurrencyCode, targetCurrencyCode)
                .stream().map(conversionRuleMapper::toDomain).toList();
    }
}