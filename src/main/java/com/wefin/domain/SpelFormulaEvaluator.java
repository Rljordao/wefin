package com.wefin.domain;

import com.wefin.application.dto.CurrencyDTO;
import com.wefin.application.dto.MoneyDTO;
import com.wefin.application.ports.out.FormulaEvaluatorPort;
import com.wefin.domain.exceptions.FormulaEvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Component
public class SpelFormulaEvaluator implements FormulaEvaluatorPort {

    private final ExpressionParser expressionParser = new SpelExpressionParser();

    @Override
    public BigDecimal evaluate(String formula, Map<String, Object> context) {
        StandardEvaluationContext evaluationContext = new StandardEvaluationContext();
        context.forEach(evaluationContext::setVariable);

        Expression expression = expressionParser.parseExpression(formula);
        Object result = expression.getValue(evaluationContext);

        if (result instanceof BigDecimal) {
            return ((BigDecimal) result).setScale(6, RoundingMode.HALF_UP);
        } else if (result instanceof Double) {
            return BigDecimal.valueOf((Double) result).setScale(6, RoundingMode.HALF_UP);
        } else if (result instanceof Integer) {
            return BigDecimal.valueOf((Integer) result).setScale(6, RoundingMode.HALF_UP);
        } else if (result instanceof Long) {
            return BigDecimal.valueOf((Long) result).setScale(6, RoundingMode.HALF_UP);
        } else if (result instanceof Float) {
            return BigDecimal.valueOf((Float) result).setScale(6, RoundingMode.HALF_UP);
        }

        throw new FormulaEvaluationException("O resultado da fórmula não pode ser convertido em BigDecimal: " + result);
    }

    @Override
    public MoneyDTO evaluateMoneyFormula(String formula, MoneyDTO sourceMoney, CurrencyDTO targetCurrency, BigDecimal baseRate) {
        formula = formula.replaceAll("\\bbaseRate\\b", "#baseRate")
                .replaceAll("\\bamount\\b", "#amount");

        Map<String, Object> context = new HashMap<>();
        context.put("baseRate", baseRate);
        context.put("amount", sourceMoney.getAmount());

        BigDecimal adjustedRate = evaluate(formula, context);

        return sourceMoney.convertTo(targetCurrency, adjustedRate);
    }
}