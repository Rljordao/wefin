package com.wefin.infrastructure.adapters.out.persistence.adapters;

import com.wefin.application.dto.TransactionFilterCriteria;
import com.wefin.application.ports.out.TransactionRepositoryPort;
import com.wefin.application.util.DateResolver;
import com.wefin.domain.entities.Transaction;
import com.wefin.infrastructure.adapters.out.persistence.entities.TransactionEntity;
import com.wefin.infrastructure.adapters.out.persistence.mappers.TransactionMapper;
import com.wefin.infrastructure.adapters.out.persistence.repositories.TransactionRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

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
            entity.setCreatedAt(DateResolver.localDateTimeNow());
        }
        entity.setUpdatedAt(DateResolver.localDateTimeNow());

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
                predicates.add(cb.equal(root.get("sourceAmount").get("currency").get("code"), criteria.getSourceCurrencyCode()));
            }

            if (criteria.getTargetCurrencyCode() != null) {
                predicates.add(cb.equal(root.get("targetAmount").get("currency").get("code"), criteria.getTargetCurrencyCode()));
            }

            if (criteria.getKingdom() != null) {
                Predicate  productKingdom = cb.equal(root.join("product").get("kingdom"), criteria.getKingdom());
                Predicate sourceKingdom = cb.equal(root.get("sourceAmount").get("currency").get("kingdom"), criteria.getKingdom());
                Predicate targetKingdom = cb.equal(  root.get("targetAmount").get("currency").get("kingdom"), criteria.getKingdom());
                predicates.add(cb.or(sourceKingdom, targetKingdom, productKingdom));
            }

            if (criteria.getStartDate() != null && criteria.getEndDate() != null) {
                predicates.add(cb.between(root.get("transactionDate"), criteria.getStartDate(), criteria.getEndDate()));
            } else if (criteria.getStartDate() != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("transactionDate"), criteria.getStartDate()));
            } else if (criteria.getEndDate() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("transactionDate"), criteria.getEndDate()));
            }

            if (criteria.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), criteria.getStatus()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}