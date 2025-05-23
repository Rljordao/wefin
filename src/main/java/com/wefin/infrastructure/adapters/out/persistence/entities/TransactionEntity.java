package com.wefin.infrastructure.adapters.out.persistence.entities;

import com.wefin.infrastructure.adapters.out.persistence.entities.enums.TransactionEntityStatus;
import jakarta.persistence.AssociationOverride;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "source_amount"))
    @AssociationOverride(name = "currency", joinColumns = @JoinColumn(name = "source_currency_id"))
    private MoneyEmbeddable sourceAmount;

    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "target_amount"))
    @AssociationOverride(name = "currency", joinColumns = @JoinColumn(name = "target_currency_id"))
    private MoneyEmbeddable targetAmount;

    @Column(precision = 19, scale = 6)
    private BigDecimal appliedRate;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TransactionEntityStatus status;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

}
