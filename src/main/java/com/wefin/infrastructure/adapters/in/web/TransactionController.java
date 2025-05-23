package com.wefin.infrastructure.adapters.in.web;

import com.wefin.application.dto.TransactionDTO;
import com.wefin.application.dto.TransactionFilterDTO;
import com.wefin.application.ports.in.ConversionPort;
import com.wefin.application.ports.in.TransactionPort;
import com.wefin.infrastructure.adapters.in.web.mappers.TransactionModelMapper;
import com.wefin.openapi.api.TransactionsApi;
import com.wefin.openapi.model.ConvertCurrencyRequestModel;
import com.wefin.openapi.model.PageTransactionModel;
import com.wefin.openapi.model.TransactionModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class TransactionController implements TransactionsApi {

    private final ConversionPort conversionPort;
    private final TransactionPort transactionPort;
    private final TransactionModelMapper transactionModelMapper;


    @Override
    public ResponseEntity<TransactionModel> convertCurrency(ConvertCurrencyRequestModel request) {
        TransactionDTO transaction = conversionPort.createTransaction(transactionModelMapper.toRequest(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionModelMapper.toModel(transaction));
    }

    @Override
    public ResponseEntity<PageTransactionModel> getTransactions(String sourceCurrency, String targetCurrency,
                                                                String kingdom, LocalDate fromDate, LocalDate toDate, String status, Integer page, Integer size){

        TransactionFilterDTO filter = TransactionFilterDTO.builder()
                .sourceCurrency(sourceCurrency)
                .targetCurrency(targetCurrency)
                .kingdom(kingdom)
                .fromDate(fromDate)
                .toDate(toDate)
                .status(status)
                .build();
        Page<TransactionDTO> transactions = transactionPort.getTransactions(filter, PageRequest.of(page, size));
    return ResponseEntity.ok(transactionModelMapper.toPageModel(transactions));
    }

}