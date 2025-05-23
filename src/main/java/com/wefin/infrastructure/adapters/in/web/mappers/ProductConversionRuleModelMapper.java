package com.wefin.infrastructure.adapters.in.web.mappers;

import com.wefin.application.dto.ProductConversionRuleDTO;
import com.wefin.application.dto.ProductConversionRuleRequest;
import com.wefin.openapi.model.CreateProductConversionRuleRequestModel;
import com.wefin.openapi.model.ProductConversionRuleModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductModelMapper.class, CurrencyModelMapper.class})
public interface ProductConversionRuleModelMapper {

    ProductConversionRuleRequest toCreateRequest(CreateProductConversionRuleRequestModel createProductConversionRuleRequestModel);
    ProductConversionRuleModel toModelConversionRuleModel(ProductConversionRuleDTO productConversionRuleDTO);
    List<ProductConversionRuleModel> toModelList(List<ProductConversionRuleDTO> productConversionRuleDTOS);

}
