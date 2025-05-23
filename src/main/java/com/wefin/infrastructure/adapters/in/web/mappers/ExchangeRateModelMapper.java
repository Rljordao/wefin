package com.wefin.infrastructure.adapters.in.web.mappers;

import com.wefin.application.dto.ExchangeRateDTO;
import com.wefin.application.dto.ExchangeRateUpdateRequest;
import com.wefin.openapi.model.ExchangeRateModel;
import com.wefin.openapi.model.UpdateExchangeRateRequestModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CurrencyModelMapper.class})
public interface ExchangeRateModelMapper {

    ExchangeRateModel toModel(ExchangeRateDTO exchangeRateDTO);
    ExchangeRateUpdateRequest toRequest(UpdateExchangeRateRequestModel updateExchangeRateRequestModel);

}
