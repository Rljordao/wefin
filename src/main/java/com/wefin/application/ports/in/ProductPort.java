package com.wefin.application.ports.in;

import com.wefin.application.dto.ProductConversionRuleDTO;
import com.wefin.application.dto.ProductCreateRequest;
import com.wefin.application.dto.ProductDTO;
import com.wefin.application.dto.ProductConversionRuleRequest;

import java.util.List;

public interface ProductPort {

    ProductDTO getProduct(Long id);

    ProductConversionRuleDTO createConversionRule(ProductConversionRuleRequest request);

    ProductConversionRuleDTO saveProductConversionRule(ProductConversionRuleDTO productConversionRuleDTO);

    ProductDTO createProduct(ProductCreateRequest request);

    List<ProductConversionRuleDTO> findConversionRule(Long productId, String sourceCurrencyCode, String targetCurrencyCode);
}