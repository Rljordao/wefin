package com.wefin.infrastructure.adapters.in.web.mappers;

import com.wefin.application.dto.ProductCreateRequest;
import com.wefin.application.dto.ProductDTO;
import com.wefin.openapi.model.CreateProductRequestModel;
import com.wefin.openapi.model.ProductModel;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ProductModelMapper {

    ProductModel toModel(ProductDTO productDTO);


    ProductCreateRequest toRequest(CreateProductRequestModel createProductRequestModel);





}
