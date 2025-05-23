package com.wefin.application.mappers;

import com.wefin.application.dto.MoneyDTO;
import com.wefin.domain.valueobjects.Money;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CurrencyDTOMapper.class})
public interface MoneyDTOMapper {

    MoneyDTO toDTO(Money money);
    Money toDomain(MoneyDTO money);

}
