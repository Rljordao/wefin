package com.wefin.application.ports.in;

import com.wefin.application.dto.TransactionDTO;
import com.wefin.application.dto.TransactionFilterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionPort {

    Page<TransactionDTO> getTransactions(TransactionFilterDTO filter, Pageable pageable);
}