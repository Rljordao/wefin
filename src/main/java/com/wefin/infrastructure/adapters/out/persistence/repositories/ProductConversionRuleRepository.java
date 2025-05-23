package com.wefin.infrastructure.adapters.out.persistence.repositories;

import com.wefin.infrastructure.adapters.out.persistence.entities.ProductConversionRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductConversionRuleRepository extends JpaRepository<ProductConversionRuleEntity, Long> {
    Optional<ProductConversionRuleEntity> findByProductIdAndSourceCurrencyIdAndTargetCurrencyIdAndActiveTrue(
            Long productId, Long sourceCurrencyId, Long targetCurrencyId);

    @Query("SELECT pcr FROM ProductConversionRuleEntity pcr " +
            "LEFT JOIN pcr.sourceCurrency sc " +
            "LEFT JOIN pcr.targetCurrency tc " +
            "WHERE pcr.product.id = :productId " +
            "AND pcr.active = true " +
            "AND (:sourceCurrencyCode IS NULL OR sc.code = :sourceCurrencyCode) " +
            "AND (:targetCurrencyCode IS NULL OR tc.code = :targetCurrencyCode)")
    List<ProductConversionRuleEntity> findByCriteria(
            @Param("productId") Long productId,
            @Param("sourceCurrencyCode") String sourceCurrencyCode,
            @Param("targetCurrencyCode") String targetCurrencyCode);
}
