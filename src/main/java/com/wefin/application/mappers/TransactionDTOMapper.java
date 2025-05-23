package com.wefin.application.mappers;

import com.wefin.application.dto.TransactionDTO;
import com.wefin.application.dto.enums.TransactionDTOStatus;
import com.wefin.domain.entities.Transaction;
import com.wefin.domain.entities.enums.TransactionStatus;
import com.wefin.infrastructure.adapters.out.persistence.mappers.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, CurrencyDTOMapper.class, MoneyDTOMapper.class})
public interface TransactionDTOMapper {

    @Mapping(source = "sourceAmount", target = "sourceAmount")
    @Mapping(source = "targetAmount", target = "targetAmount")
    @Mapping(source = "status", target = "status", qualifiedByName = "mapStatusToDomain")
    Transaction toDomain(TransactionDTO transactionDTO);

    @Mapping(source = "sourceAmount", target = "sourceAmount")
    @Mapping(source = "targetAmount", target = "targetAmount")
    @Mapping(source = "status", target = "status", qualifiedByName = "mapStatusToDTO")
    TransactionDTO toDTO(Transaction transaction);

    @Named("mapStatusToDTO")
    static TransactionDTOStatus mapStatusToEntity(TransactionStatus status) {
        return status == null ? null : TransactionDTOStatus.valueOf(status.name());
    }

    @Named("mapStatusToDomain")
    static TransactionStatus mapStatusToDomain(TransactionDTOStatus status) {
        return status == null ? null : TransactionStatus.valueOf(status.name());
    }




}
