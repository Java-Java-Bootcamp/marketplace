package ru.teamtwo.api.service.impl.product;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.teamtwo.api.exception.ItemNotFoundException;
import ru.teamtwo.api.mappers.product.ProductOfferMapper;
import ru.teamtwo.api.models.product.ProductOffer;
import ru.teamtwo.api.repository.product.ProductOfferRepository;
import ru.teamtwo.api.service.api.product.ProductOfferService;
import ru.teamtwo.core.dtos.controller.product.ProductOfferController;
import ru.teamtwo.core.dtos.product.ProductOfferDto;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductOfferServiceImpl implements ProductOfferService {
    private final ProductOfferRepository productOfferRepository;
    private final ProductOfferMapper productOfferMapper;

    @Override
    public ProductOfferDto get(Long id) {
        if(productOfferRepository.existsById(id))
            return productOfferMapper.convert(productOfferRepository.getProductOfferById(id));
        else
            throw new ItemNotFoundException();
    }

    @Override
    public Set<Long> save(Set<ProductOfferDto> dtos) {
        return productOfferRepository
                .saveAll(dtos.stream().map(productOfferMapper::convert).collect(Collectors.toSet()))
                .stream()
                .map(ProductOffer::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ProductOfferDto> query(ProductOfferController.ProductQuery productQuery) {
        Sort sort = Sort.by(productQuery.sortingTypeField().name().replace("_","."));
        if (productQuery.sortingTypeAscDesc().equals(ProductOfferController.SortingTypeAscDesc.ASC))
            sort.ascending();
        else
            sort.descending();

        PageRequest pageRequest = PageRequest.of(productQuery.offset(),
                productQuery.limit(),
                sort);

        return productOfferRepository
                .getProductOffersByProductName(productQuery.query(), pageRequest)
                .get()
                .map(productOfferMapper::convert)
                .collect(Collectors.toSet());
    }
}
