package com.wefin.application.dto;

import com.wefin.application.dto.enums.TransactionDTOStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private Long id;
    private ProductDTO product;
    private MoneyDTO sourceAmount;
    private MoneyDTO targetAmount;
    private BigDecimal appliedRate;
    private LocalDateTime transactionDate;
    private TransactionDTOStatus status;
    private String notes;

}
