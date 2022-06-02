package ru.teamtwo.backend.service.impl.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.teamtwo.backend.mappers.product.ProductMapper;
import ru.teamtwo.backend.models.product.Product;
import ru.teamtwo.backend.repository.product.ProductRepository;
import ru.teamtwo.backend.service.api.product.ProductService;
import ru.teamtwo.backend.service.impl.ServiceUtils;
import ru.teamtwo.core.dtos.product.ProductDto;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDto get(Long id) {
        return productMapper.convertToDto((Product) ServiceUtils.get(productRepository, id));
    }

    @Override
    public Set<Long> save(Set<ProductDto> dtos) {
        return ServiceUtils.save(() -> productRepository
                .saveAll(dtos.stream().map(productMapper::convertToEntity).collect(Collectors.toSet()))
                .stream()
                .map(Product::getId)
                .collect(Collectors.toSet()), log);
    }
}
