package com.wefin.application.services;

import com.wefin.application.dto.CurrencyConversionRequest;
import com.wefin.application.dto.CurrencyDTO;
import com.wefin.application.dto.ExchangeRateDTO;
import com.wefin.application.dto.MoneyDTO;
import com.wefin.application.dto.ProductDTO;
import com.wefin.application.dto.TransactionDTO;
import com.wefin.application.dto.enums.TransactionDTOStatus;
import com.wefin.application.mappers.TransactionDTOMapper;
import com.wefin.application.ports.in.ConversionPort;
import com.wefin.application.ports.in.CurrencyPort;
import com.wefin.application.ports.in.ProductPort;
import com.wefin.application.ports.out.FormulaEvaluatorPort;
import com.wefin.application.ports.out.ProductRepositoryPort;
import com.wefin.application.ports.out.TransactionRepositoryPort;
import com.wefin.application.util.DateResolver;
import com.wefin.domain.entities.ProductConversionRule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConversionService implements ConversionPort {

    private final CurrencyPort currencyPort;
    private final ProductPort productPort;
    private final ProductRepositoryPort productRepositoryPort;
    private final FormulaEvaluatorPort formulaEvaluatorPort;
    private final TransactionDTOMapper transactionMapper;
    private final TransactionRepositoryPort transactionRepository;

    @Override
    public MoneyDTO convertCurrency(Long productId, BigDecimal amountValue,
            String sourceCurrencyCode, String targetCurrencyCode) {

        CurrencyDTO sourceCurrency = currencyPort.getCurrencyByCode(sourceCurrencyCode);
        CurrencyDTO targetCurrency = currencyPort.getCurrencyByCode(targetCurrencyCode);

        MoneyDTO sourceAmount = MoneyDTO.of(amountValue, sourceCurrency);

        ExchangeRateDTO baseRateDTO = currencyPort.getCurrentExchangeRate(sourceCurrencyCode, targetCurrencyCode);

        BigDecimal baseRate = baseRateDTO.getRate();

        if (productId == null) {
            return sourceAmount.convertTo(targetCurrency, baseRate);
        }

        Optional<ProductConversionRule> rule= productRepositoryPort.findActiveConversionRule(
                productId, sourceCurrency.getId(), targetCurrency.getId());
        if (rule.isEmpty()) {
            return sourceAmount.multiply(targetCurrency, baseRate);
        }

        String formula = rule.get().getConversionFormula();

        return formulaEvaluatorPort.evaluateMoneyFormula(formula, sourceAmount, targetCurrency, baseRate);
    }

    @Override
    @Transactional
    public TransactionDTO createTransaction(CurrencyConversionRequest request) {
        ProductDTO product = null;
        if (request.getProductId() != null) {
            product = productPort.getProduct(request.getProductId());
        }

        CurrencyDTO sourceCurrency = currencyPort.getCurrencyByCode(request.getSourceCurrencyCode());

        MoneyDTO sourceAmount = MoneyDTO.of(request.getAmount(), sourceCurrency);

        TransactionDTO transaction = TransactionDTO.builder()
                .product(product)
                .sourceAmount(sourceAmount)
                .status(TransactionDTOStatus.PENDING)
                .targetAmount(sourceAmount)
                .transactionDate(DateResolver.localDateTimeNow())
                .build();

        TransactionDTO savedTransaction = saveTransaction(transaction);

        try {
            MoneyDTO convertedAmount = convertCurrency(
                    request.getProductId(),
                    request.getAmount(),
                    request.getSourceCurrencyCode(),
                    request.getTargetCurrencyCode());

            savedTransaction.setTargetAmount(convertedAmount);
            savedTransaction.setAppliedRate(convertedAmount.calculateRate(sourceAmount));
            savedTransaction.setStatus(TransactionDTOStatus.COMPLETED);

        } catch (Exception e) {
            savedTransaction.setStatus(TransactionDTOStatus.FAILED);
            savedTransaction.setNotes("Error: " + e.getMessage());
            throw e;
        } finally {
            savedTransaction = saveTransaction(savedTransaction);
        }
        return savedTransaction;
    }

    @Override
    public TransactionDTO saveTransaction(TransactionDTO transactionDTO) {
        return transactionMapper.toDTO(transactionRepository.save(transactionMapper.toDomain(transactionDTO)));
    }
}