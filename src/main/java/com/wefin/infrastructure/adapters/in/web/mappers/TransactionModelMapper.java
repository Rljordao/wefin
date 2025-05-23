package com.wefin.infrastructure.adapters.in.web.mappers;

import com.wefin.application.dto.CurrencyConversionRequest;
import com.wefin.application.dto.TransactionDTO;
import com.wefin.infrastructure.adapters.out.persistence.mappers.ProductMapper;
import com.wefin.openapi.model.ConvertCurrencyRequestModel;
import com.wefin.openapi.model.PageTransactionModel;
import com.wefin.openapi.model.PageTransactionPageableModel;
import com.wefin.openapi.model.PageTransactionPageableSortModel;
import com.wefin.openapi.model.TransactionModel;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, CurrencyModelMapper.class, MoneyModelMapper.class})
public interface TransactionModelMapper {


    TransactionModel toModel(TransactionDTO transactionDTO);
    CurrencyConversionRequest toRequest(ConvertCurrencyRequestModel convertCurrencyRequestModel );


    default PageTransactionModel toPageModel(Page<TransactionDTO> page) {
        if (page == null) {
            return null;
        }
        PageTransactionModel pageModel = new PageTransactionModel();
        List<TransactionModel> content = page.getContent().stream()
                .map(this::toModel).toList();
        pageModel.setContent(content);

        pageModel.setTotalPages(page.getTotalPages());
        pageModel.setTotalElements(Long.valueOf(page.getTotalElements()).intValue());
        pageModel.setSize(page.getSize());
        pageModel.setNumber(page.getNumber());
        pageModel.setNumberOfElements(page.getNumberOfElements());
        pageModel.setFirst(page.isFirst());
        pageModel.setLast(page.isLast());
        pageModel.setEmpty(page.isEmpty());

        page.getPageable();
        PageTransactionPageableModel pageableModel = getPageTransactionPageableModel(page);

        pageModel.setPageable(pageableModel);

        return pageModel;
    }

    private static PageTransactionPageableModel getPageTransactionPageableModel(Page<TransactionDTO> page) {
        PageTransactionPageableModel pageableModel = new PageTransactionPageableModel();
        pageableModel.setOffset(Long.valueOf(page.getPageable().getOffset()).intValue());
        pageableModel.setPageNumber(page.getPageable().getPageNumber());
        pageableModel.setPageSize(page.getPageable().getPageSize());
        pageableModel.setPaged(true);
        pageableModel.setUnpaged(false);

        page.getPageable().getSort();
        if (page.getPageable().getSort().isSorted()) {
            PageTransactionPageableSortModel sortModel = new PageTransactionPageableSortModel();
            sortModel.setEmpty(page.getPageable().getSort().isEmpty());
            sortModel.setSorted(page.getPageable().getSort().isSorted());
            sortModel.setUnsorted(!page.getPageable().getSort().isSorted());

            pageableModel.setSort(sortModel);
        }
        return pageableModel;
    }

}
