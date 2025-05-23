package com.wefin.application.services;

import com.wefin.domain.exceptions.ValidationException;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class FormulaValidationService {

    private static final Set<String> REQUIRED_VARIABLES = Set.of("baseRate", "amount");
    private static final Set<String> DISALLOWED_TERMS = Set.of("new", "T(", "class", "System");
    private final ExpressionParser parser = new SpelExpressionParser();

    public boolean validateConversionFormula(String formula) {
        if (formula == null || formula.trim().isEmpty()) {
            throw new ValidationException("A fórmula não pode ser vazia");
        }

        for (String term : DISALLOWED_TERMS) {
            if (formula.contains(term)) {
                throw new ValidationException(
                        "A fórmula contém termos não permitidos: " + term,
                        "INVALID_FORMULA_TERM"
                );
            }
        }

        for (String str : REQUIRED_VARIABLES) {
            if (!formula.contains(str)) {
                throw new ValidationException(
                        "A fórmula deve conter a variável obrigatória: " + str,
                        "MISSING_REQUIRED_VARIABLE"
                );
            }
        }

        try {
            formula = formula.replaceAll("\\bbaseRate\\b", "#baseRate")
                    .replaceAll("\\bamount\\b", "#amount");

            Expression expression = parser.parseExpression(formula);
            EvaluationContext context = new StandardEvaluationContext();
            context.setVariable("baseRate", 1.0);
            context.setVariable("amount", 100.0);
            Object result = expression.getValue(context);
            if (!(result instanceof Number)) {
                throw new ValidationException(
                        "A fórmula deve retornar um valor numérico",
                        "INVALID_FORMULA_RESULT"
                );
            }
            return true;
        } catch (ParseException e) {
            throw new ValidationException("Erro de sintaxe na fórmula: " + e.getMessage(), "FORMULA_SYNTAX_ERROR");
        } catch (Exception e) {
            throw new ValidationException("Erro ao validar a fórmula: " + e.getMessage(), "FORMULA_VALIDATION_ERROR");
        }
    }
}