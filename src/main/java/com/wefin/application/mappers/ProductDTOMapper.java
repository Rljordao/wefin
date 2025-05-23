package com.wefin.application.mappers;

import com.wefin.application.dto.ProductDTO;
import com.wefin.domain.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDTOMapper {

    Product toDomain(ProductDTO productDTO);

    ProductDTO toDTO(Product product);

}
