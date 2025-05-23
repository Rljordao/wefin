package com.wefin.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateUpdateRequest {

    private String sourceCurrencyCode;
    private String targetCurrencyCode;
    private BigDecimal rate;
    private LocalDateTime effectiveDate;
}
