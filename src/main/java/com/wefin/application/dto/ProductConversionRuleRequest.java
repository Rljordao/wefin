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
public class ProductConversionRuleRequest {

    private Long productId;
    private String sourceCurrencyCode;
    private String targetCurrencyCode;
    private String conversionFormula;
    private LocalDateTime effectiveDate;

}
