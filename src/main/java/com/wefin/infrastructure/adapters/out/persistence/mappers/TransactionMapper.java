package com.wefin.infrastructure.adapters.out.persistence.mappers;

import com.wefin.domain.entities.Transaction;
import com.wefin.domain.entities.enums.TransactionStatus;
import com.wefin.infrastructure.adapters.out.persistence.entities.TransactionEntity;
import com.wefin.infrastructure.adapters.out.persistence.entities.enums.TransactionEntityStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, CurrencyMapper.class})
public interface TransactionMapper {

    @Mapping(source = "status", target = "status", qualifiedByName = "mapStatusToEntity")
    TransactionEntity toEntity(Transaction domain);

    @Mapping(source = "status", target = "status", qualifiedByName = "mapStatusToDomain")
    Transaction toDomain(TransactionEntity entity);

    @Named("mapStatusToEntity")
    static TransactionEntityStatus mapStatusToEntity(TransactionStatus status) {
        return status == null ? null : TransactionEntityStatus.valueOf(status.name());
    }

    @Named("mapStatusToDomain")
    static TransactionStatus mapStatusToDomain(TransactionEntityStatus status) {
        return status == null ? null : TransactionStatus.valueOf(status.name());
    }




}
