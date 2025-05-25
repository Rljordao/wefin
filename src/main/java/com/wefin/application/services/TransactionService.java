package com.wefin.application.services;

import com.wefin.application.dto.TransactionDTO;
import com.wefin.application.dto.TransactionFilterCriteria;
import com.wefin.application.dto.TransactionFilterDTO;
import com.wefin.application.mappers.TransactionDTOMapper;
import com.wefin.application.ports.in.TransactionPort;
import com.wefin.application.ports.out.TransactionRepositoryPort;
import com.wefin.domain.entities.enums.TransactionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService implements TransactionPort {

    private final TransactionRepositoryPort transactionRepository;
    private final TransactionDTOMapper transactionMapper;


    @Override
    public Page<TransactionDTO> getTransactions(TransactionFilterDTO filter, Pageable pageable) {

        LocalDateTime fromDateTime = filter.getFromDate() != null ? filter.getFromDate().atStartOfDay() : null;

        LocalDateTime toDateTime = filter.getToDate() != null ? filter.getToDate().plusDays(1).atStartOfDay() : null;

        TransactionFilterCriteria criteria = TransactionFilterCriteria.builder()
                .sourceCurrencyCode(filter.getSourceCurrency())
                .targetCurrencyCode(filter.getTargetCurrency())
                .kingdom(filter.getKingdom())
                .startDate(fromDateTime)
                .endDate(toDateTime)
                .status(filter.getStatus() != null ?
                        TransactionStatus.valueOf(filter.getStatus()) : null)
                .build();

        return transactionRepository.findWithFilters(criteria, pageable)
                .map(transactionMapper::toDTO);
    }

}

