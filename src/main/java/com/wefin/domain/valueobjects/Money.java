package com.wefin.domain.valueobjects;

import com.wefin.domain.entities.Currency;
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
public class Money {
    BigDecimal amount;
    Currency currency;

    public static final int DEFAULT_SCALE = 6;

    private Money(BigDecimal amount, Currency currency) {
        this.amount = Objects.requireNonNull(amount).setScale(DEFAULT_SCALE, RoundingMode.HALF_UP);
        this.currency = Objects.requireNonNull(currency);
    }

    public static Money of(BigDecimal amount, Currency currency) {
        return new Money(amount, currency);
    }

    public Money multiply( Currency currency, BigDecimal factor) {
        return Money.of(this.amount.multiply(factor), currency);
    }

    public Money convertTo(Currency targetCurrency, BigDecimal exchangeRate) {
        if (this.currency.equals(targetCurrency)) {
            return this;
        }
        return Money.of(exchangeRate, targetCurrency);
    }

    public BigDecimal calculateRate(Money sourceMoney) {
        if (sourceMoney.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Cannot calculate rate from zero amount");
        }
        return this.amount.divide(sourceMoney.getAmount(), DEFAULT_SCALE, RoundingMode.HALF_UP);
    }
}