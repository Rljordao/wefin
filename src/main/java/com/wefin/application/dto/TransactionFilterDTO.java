package com.wefin.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionFilterDTO {

    private String sourceCurrency;
    private String targetCurrency;
    private String kingdom;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String status;

}
