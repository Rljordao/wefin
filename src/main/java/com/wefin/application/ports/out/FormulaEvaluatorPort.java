package com.wefin.application.ports.out;

import com.wefin.application.dto.CurrencyDTO;
import com.wefin.application.dto.MoneyDTO;

import java.math.BigDecimal;
import java.util.Map;

public interface FormulaEvaluatorPort {

    BigDecimal evaluate(String formula, Map<String, Object> context);

    MoneyDTO evaluateMoneyFormula(String formula, MoneyDTO sourceMoney, CurrencyDTO targetCurrency, BigDecimal baseRate);
}

