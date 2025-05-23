package com.wefin.infrastructure.adapters.out.persistence.mappers;

import com.wefin.domain.entities.ProductConversionRule;
import com.wefin.infrastructure.adapters.out.persistence.entities.ProductConversionRuleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, CurrencyMapper.class})
public interface ProductConversionRuleMapper {
    ProductConversionRuleEntity toEntity(ProductConversionRule domain);

    ProductConversionRule toDomain(ProductConversionRuleEntity entity);
}
