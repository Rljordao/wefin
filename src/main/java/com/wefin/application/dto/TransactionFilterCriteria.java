package com.wefin.application.dto;

import com.wefin.domain.entities.enums.TransactionStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TransactionFilterCriteria {

    private String sourceCurrencyCode;
    private String targetCurrencyCode;
    private String kingdom;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private TransactionStatus status;
}
