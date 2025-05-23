package com.wefin.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductConversionRule {

    private Long id;
    private Product product;
    private Currency sourceCurrency;
    private Currency targetCurrency;
    private String conversionFormula;
    private LocalDateTime effectiveDate;
    private boolean active;
}