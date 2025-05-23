package com.wefin.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRate {

    private Long id;
    private Currency sourceCurrency;
    private Currency targetCurrency;
    private BigDecimal rate;
    private LocalDateTime effectiveDate;
    private LocalDateTime expirationDate;
    private boolean active;
}
