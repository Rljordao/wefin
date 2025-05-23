package com.wefin.infrastructure.adapters.out.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "product_conversion_rules")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductConversionRuleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "source_currency_id", nullable = false)
    private CurrencyEntity sourceCurrency;

    @ManyToOne
    @JoinColumn(name = "target_currency_id", nullable = false)
    private CurrencyEntity targetCurrency;

    @Column(nullable = false, length = 200)
    private String conversionFormula;

    @Column(nullable = false)
    private LocalDateTime effectiveDate;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
