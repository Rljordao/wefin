package com.wefin.application.ports.out;

import com.wefin.application.dto.TransactionFilterCriteria;
import com.wefin.domain.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionRepositoryPort {

    Transaction save(Transaction transaction);
    Page<Transaction> findWithFilters(TransactionFilterCriteria criteria, Pageable pageable);
}