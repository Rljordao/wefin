package com.wefin.application.mappers;

import com.wefin.application.dto.ProductConversionRuleDTO;
import com.wefin.domain.entities.ProductConversionRule;
import com.wefin.infrastructure.adapters.out.persistence.mappers.ProductMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, CurrencyDTOMapper.class})
public interface ProductConversionRuleDTOMapper {

    ProductConversionRule toDomain(ProductConversionRuleDTO productConversionRuleDTO );
    ProductConversionRuleDTO toDTO(ProductConversionRule productConversionRule);
}
