package com.wefin.infrastructure.adapters.in.web;

import com.wefin.application.dto.ExchangeRateDTO;
import com.wefin.application.ports.in.CurrencyPort;
import com.wefin.infrastructure.adapters.in.web.mappers.ExchangeRateModelMapper;
import com.wefin.openapi.api.CurrenciesApi;
import com.wefin.openapi.model.ExchangeRateModel;
import com.wefin.openapi.model.UpdateExchangeRateRequestModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class CurrencyController implements CurrenciesApi {

    private final CurrencyPort currencyPort;
    private final ExchangeRateModelMapper exchangeRateModelMapper;

    @Override
    public ResponseEntity<ExchangeRateModel> getExchangeRate(String sourceCurrencyCode, String targetCurrencyCode) {
        ExchangeRateDTO rate = currencyPort.getCurrentExchangeRate(sourceCurrencyCode, targetCurrencyCode);
        return ResponseEntity.ok(exchangeRateModelMapper.toModel(rate));
    }
    @Override
    public ResponseEntity<ExchangeRateModel> updateExchangeRate(UpdateExchangeRateRequestModel request) {
        ExchangeRateDTO rate = currencyPort.updateExchangeRate(exchangeRateModelMapper.toRequest(request));
        return ResponseEntity.ok(exchangeRateModelMapper.toModel(rate));
    }
}