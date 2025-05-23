package com.wefin.domain.entities;

import com.wefin.domain.entities.enums.TransactionStatus;
import com.wefin.domain.valueobjects.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private Long id;
    private Product product;
    private Money sourceAmount;
    private Money targetAmount;
    private BigDecimal appliedRate;
    private LocalDateTime transactionDate;
    private TransactionStatus status;
    private String notes;
}