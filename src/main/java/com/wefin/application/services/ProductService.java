package com.wefin.application.services;

import com.wefin.application.dto.CurrencyDTO;
import com.wefin.application.dto.ProductConversionRuleDTO;
import com.wefin.application.dto.ProductCreateRequest;
import com.wefin.application.dto.ProductDTO;
import com.wefin.application.dto.ProductConversionRuleRequest;
import com.wefin.application.mappers.ProductConversionRuleDTOMapper;
import com.wefin.application.mappers.ProductDTOMapper;
import com.wefin.application.ports.in.CurrencyPort;
import com.wefin.application.ports.in.ProductPort;
import com.wefin.application.ports.out.ProductRepositoryPort;
import com.wefin.application.util.DateResolver;
import com.wefin.domain.entities.Product;
import com.wefin.domain.entities.ProductConversionRule;
import com.wefin.domain.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductPort {

    private final ProductRepositoryPort productRepositoryPort;
    private final CurrencyPort currencyService;
    private final FormulaValidationService  formulaValidation;
    private final ProductConversionRuleDTOMapper conversionRuleMapper;
    private final ProductDTOMapper productMapper;

    @Override
    public ProductDTO getProduct(Long id) {
        Product product = getProductEntity(id);
        return productMapper.toDTO(product);
    }


    @Override
    @Transactional
    public ProductConversionRuleDTO createConversionRule(ProductConversionRuleRequest request) {
        ProductDTO product = getProduct(request.getProductId());
        CurrencyDTO sourceCurrency = currencyService.getCurrencyByCode(request.getSourceCurrencyCode());
        CurrencyDTO targetCurrency = currencyService.getCurrencyByCode(request.getTargetCurrencyCode());

        formulaValidation.validateConversionFormula(request.getConversionFormula());

        productRepositoryPort.findActiveConversionRule(product.getId(), sourceCurrency.getId(), targetCurrency.getId())
                .ifPresent(rule -> {
                    rule.setActive(false);
                    productRepositoryPort.save(rule);
                });

        ProductConversionRuleDTO newRule = ProductConversionRuleDTO.builder()
                .product(product)
                .sourceCurrency(sourceCurrency)
                .targetCurrency(targetCurrency)
                .conversionFormula(request.getConversionFormula())
                .effectiveDate(request.getEffectiveDate() != null ? request.getEffectiveDate() : DateResolver.localDateTimeNow())
                .active(true)
                .build();

        return saveProductConversionRule(newRule);
    }

    @Override
    public ProductConversionRuleDTO saveProductConversionRule(ProductConversionRuleDTO productConversionRuleDTO) {
        return conversionRuleMapper.toDTO(productRepositoryPort.save(conversionRuleMapper.toDomain(productConversionRuleDTO)));
    }

    @Override
    @Transactional
    public ProductDTO createProduct(ProductCreateRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .kingdom(request.getKingdom())
                .build();
        return productMapper.toDTO(productRepositoryPort.save(product));
    }

    @Override
    public List<ProductConversionRuleDTO> findConversionRule(Long productId, String sourceCurrencyCode,
            String targetCurrencyCode) {
        List<ProductConversionRule> rules = productRepositoryPort
                .findByProductIdAndSourceCurrencyCodeAndTargetCurrencyCode(productId, sourceCurrencyCode, targetCurrencyCode);

        return rules.stream()
                .map(conversionRuleMapper::toDTO)
                .toList();
    }

    private Product getProductEntity(Long id) {
        return productRepositoryPort.findProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id));
    }
}