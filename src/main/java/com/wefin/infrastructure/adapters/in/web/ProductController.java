package com.wefin.infrastructure.adapters.in.web;

import com.wefin.application.dto.ProductConversionRuleDTO;
import com.wefin.application.dto.ProductDTO;
import com.wefin.application.ports.in.ProductPort;
import com.wefin.infrastructure.adapters.in.web.mappers.ProductConversionRuleModelMapper;
import com.wefin.infrastructure.adapters.in.web.mappers.ProductModelMapper;
import com.wefin.openapi.api.ProductsApi;
import com.wefin.openapi.model.CreateProductConversionRuleRequestModel;
import com.wefin.openapi.model.CreateProductRequestModel;
import com.wefin.openapi.model.ProductConversionRuleModel;
import com.wefin.openapi.model.ProductModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ProductController implements ProductsApi {

    private final ProductPort productPort;
    private final ProductModelMapper productDTOMapper;
    private final ProductConversionRuleModelMapper productConversionRuleModelMapper;

    @Override
    public ResponseEntity<ProductModel> createProduct(CreateProductRequestModel request) {
        ProductDTO createdProduct = productPort.createProduct(productDTOMapper.toRequest(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(productDTOMapper.toModel(createdProduct));
    }

    @Override
    public ResponseEntity<ProductModel> getProductById(Long id) {
        ProductDTO product = productPort.getProduct(id);
        return ResponseEntity.ok(productDTOMapper.toModel(product));
    }

    @Override
    public  ResponseEntity<ProductConversionRuleModel> createProductConversionRule(Long id, CreateProductConversionRuleRequestModel createProductConversionRuleRequestModel) {
        createProductConversionRuleRequestModel.setProductId(id);
        ProductConversionRuleDTO rule = productPort.createConversionRule(productConversionRuleModelMapper.toCreateRequest(createProductConversionRuleRequestModel));
        return ResponseEntity.status(HttpStatus.CREATED).body(productConversionRuleModelMapper.toModelConversionRuleModel(rule));
    }


    @Override
    public ResponseEntity<List<ProductConversionRuleModel>> findProductConversionRules(Long id, String sourceCurrencyCode, String targetCurrencyCode) {
        List<ProductConversionRuleDTO> rules = productPort.findConversionRule(id, sourceCurrencyCode, targetCurrencyCode);
        return ResponseEntity.ok(productConversionRuleModelMapper.toModelList(rules));
    }


}