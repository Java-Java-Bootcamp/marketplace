package ru.teamtwo.api.service.impl.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.teamtwo.api.mappers.product.ProductOfferMapper;
import ru.teamtwo.api.models.product.ProductOffer;
import ru.teamtwo.api.repository.product.ProductOfferRepository;
import ru.teamtwo.api.service.api.product.ProductOfferService;
import ru.teamtwo.api.service.impl.ServiceUtils;
import ru.teamtwo.core.dtos.controller.product.ProductOfferController;
import ru.teamtwo.core.dtos.product.ProductOfferDto;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductOfferServiceImpl implements ProductOfferService {
    private final ProductOfferRepository productOfferRepository;
    private final ProductOfferMapper productOfferMapper;

    @Override
    public ProductOfferDto get(Long id) {
        return productOfferMapper.convertToDto((ProductOffer) ServiceUtils.get(productOfferRepository, id));
    }

    @Override
    public Set<Long> save(Set<ProductOfferDto> dtos) {
        return ServiceUtils.save(() -> productOfferRepository
                .saveAll(dtos.stream().map(productOfferMapper::convertToEntity).collect(Collectors.toSet()))
                .stream()
                .map(ProductOffer::getId)
                .collect(Collectors.toSet()), log);
    }

    @Override
    public Set<ProductOfferDto> query(ProductOfferController.ProductQuery productQuery) {
        Sort sort = Sort.by(productQuery.sortingTypeField().name().replace("_",".").toLowerCase(Locale.ROOT));
        if (productQuery.sortingTypeAscDesc().equals(ProductOfferController.SortingTypeAscDesc.ASC))
            sort.ascending();
        else
            sort.descending();

        PageRequest pageRequest = PageRequest.of(productQuery.offset(),
                productQuery.limit(),
                sort);

        return productOfferRepository
                .getProductOffersByProduct_NameContains(productQuery.query(), pageRequest)
                .get()
                .map(productOfferMapper::convertToDto)
                .collect(Collectors.toSet());
    }
}
