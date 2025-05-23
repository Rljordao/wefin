package com.wefin.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class MoneyDTO {

    private BigDecimal amount;
    private CurrencyDTO currency;

    public static final int DEFAULT_SCALE = 6;

    private MoneyDTO(BigDecimal amount, CurrencyDTO currency) {
        this.amount = Objects.requireNonNull(amount).setScale(DEFAULT_SCALE, RoundingMode.HALF_UP);
        this.currency = Objects.requireNonNull(currency);
    }

    public static MoneyDTO of(BigDecimal amount, CurrencyDTO currency) {
        return new MoneyDTO(amount, currency);
    }


    public MoneyDTO multiply( CurrencyDTO currency, BigDecimal factor) {
        return MoneyDTO.of(this.amount.multiply(factor), currency);
    }

    public MoneyDTO convertTo(CurrencyDTO targetCurrency, BigDecimal exchangeRate) {
        if (this.currency.equals(targetCurrency)) {
            return this;
        }
        return MoneyDTO.of(exchangeRate, targetCurrency);
    }

    public BigDecimal calculateRate(MoneyDTO sourceMoney) {
        if (sourceMoney.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Cannot calculate rate from zero amount");
        }
        return this.amount.divide(sourceMoney.getAmount(), DEFAULT_SCALE, RoundingMode.HALF_UP);
    }
}