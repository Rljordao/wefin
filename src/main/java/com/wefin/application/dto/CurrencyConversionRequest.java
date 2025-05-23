package com.wefin.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyConversionRequest {

    private Long productId;
    private BigDecimal amount;
    private String sourceCurrencyCode;
    private String targetCurrencyCode;
}
