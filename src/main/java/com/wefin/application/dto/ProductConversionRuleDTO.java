package com.wefin.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductConversionRuleDTO {

    private Long id;
    private ProductDTO product;
    private CurrencyDTO sourceCurrency;
    private CurrencyDTO targetCurrency;
    private String conversionFormula;
    private LocalDateTime effectiveDate;
    private boolean active;
}
