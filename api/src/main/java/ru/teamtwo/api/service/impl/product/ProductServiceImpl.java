package ru.teamtwo.api.service.impl.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.teamtwo.api.exception.ItemNotFoundException;
import ru.teamtwo.api.mappers.product.ProductMapper;
import ru.teamtwo.api.models.product.Product;
import ru.teamtwo.api.repository.product.ProductRepository;
import ru.teamtwo.api.service.api.product.ProductService;
import ru.teamtwo.core.dtos.product.ProductDto;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository storeRepository;
    private final ProductMapper storeMapper;

    @Override
    public ProductDto get(Long id) {
        if(storeRepository.existsById(id))
            return storeMapper.convert(storeRepository.getById(id));
        else
            throw new ItemNotFoundException();
    }

    @Override
    public Set<Long> save(Set<ProductDto> dtos) {
        return storeRepository
                .saveAll(dtos.stream().map(storeMapper::convert).collect(Collectors.toSet()))
                .stream()
                .map(Product::getId)
                .collect(Collectors.toSet());
    }
}
