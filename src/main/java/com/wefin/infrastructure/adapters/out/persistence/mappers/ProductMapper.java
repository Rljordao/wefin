package com.wefin.infrastructure.adapters.out.persistence.mappers;

import com.wefin.domain.entities.Product;
import com.wefin.infrastructure.adapters.out.persistence.entities.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductEntity toEntity(Product domain);

    Product toDomain(ProductEntity entity);

}
