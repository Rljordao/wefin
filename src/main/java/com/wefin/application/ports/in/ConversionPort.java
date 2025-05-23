package com.wefin.application.ports.in;

import com.wefin.application.dto.CurrencyConversionRequest;
import com.wefin.application.dto.MoneyDTO;
import com.wefin.application.dto.TransactionDTO;

import java.math.BigDecimal;

public interface ConversionPort {

    MoneyDTO convertCurrency(Long productId, BigDecimal amount, String sourceCurrencyCode, String targetCurrencyCode);
    TransactionDTO createTransaction(CurrencyConversionRequest request);
    TransactionDTO saveTransaction(TransactionDTO transaction);
}