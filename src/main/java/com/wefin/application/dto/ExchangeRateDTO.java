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
public class ExchangeRateDTO {

    private Long id;
    private CurrencyDTO sourceCurrency;
    private CurrencyDTO targetCurrency;
    private BigDecimal rate;
    private LocalDateTime effectiveDate;
    private LocalDateTime expirationDate;
}

