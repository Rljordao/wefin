package com.wefin.infrastructure.adapters.out.persistence.adapters;

import com.wefin.application.dto.TransactionFilterCriteria;
import com.wefin.application.ports.out.TransactionRepositoryPort;
import com.wefin.domain.entities.Transaction;
import com.wefin.domain.entities.enums.TransactionStatus;
import com.wefin.infrastructure.adapters.out.persistence.entities.TransactionEntity;
import com.wefin.infrastructure.adapters.out.persistence.mappers.TransactionMapper;
import com.wefin.infrastructure.adapters.out.persistence.repositories.TransactionRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TransactionRepositoryAdapter implements TransactionRepositoryPort {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public Transaction save(Transaction transaction) {
        TransactionEntity entity = transactionMapper.toEntity(transaction);

        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(LocalDateTime.now());
        }
        entity.setUpdatedAt(LocalDateTime.now());

        TransactionEntity savedEntity = transactionRepository.save(entity);
        return transactionMapper.toDomain(savedEntity);
    }

    @Override
    public Page<Transaction> findWithFilters(TransactionFilterCriteria criteria, Pageable pageable) {
        Specification<TransactionEntity> spec = buildSpecification(criteria);
        return transactionRepository.findAll(spec, pageable)
                .map(transactionMapper::toDomain);
    }

    private Specification<TransactionEntity> buildSpecification(TransactionFilterCriteria criteria) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getSourceCurrencyCode() != null) {
                predicates.add(cb.equal(root.get("sourceCurrency").get("code"), criteria.getSourceCurrencyCode()));
            }

            if (criteria.getTargetCurrencyCode() != null) {
                predicates.add(cb.equal(root.get("targetCurrency").get("code"), criteria.getTargetCurrencyCode()));
            }

            if (criteria.getKingdom() != null) {
                Predicate sourceKingdom = cb.equal(root.get("sourceCurrency").get("kingdom"), criteria.getKingdom());
                Predicate targetKingdom = cb.equal(root.get("targetCurrency").get("kingdom"), criteria.getKingdom());
                predicates.add(cb.or(sourceKingdom, targetKingdom));
            }

            if (criteria.getFromDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("transactionDate"), criteria.getFromDate()));
            }

            if (criteria.getToDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("transactionDate"), criteria.getToDate()));
            }

            if (criteria.getStatus() != null) {
                TransactionStatus jpaStatus = TransactionStatus.valueOf(criteria.getStatus().name());
                predicates.add(cb.equal(root.get("status"), jpaStatus));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}