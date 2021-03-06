package ru.teamtwo.backend.service.impl.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.teamtwo.backend.mappers.product.StoreMapper;
import ru.teamtwo.backend.models.product.Store;
import ru.teamtwo.backend.repository.product.StoreRepository;
import ru.teamtwo.backend.service.api.product.StoreService;
import ru.teamtwo.backend.service.impl.ServiceUtils;
import ru.teamtwo.core.dtos.product.StoreDto;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    @Override
    public StoreDto get(Long id) {
        return storeMapper.convertToDto((Store) ServiceUtils.get(storeRepository, id));
    }

    @Override
    public Set<Long> save(Set<StoreDto> dtos) {
        return ServiceUtils.save(() -> storeRepository
                .saveAll(dtos.stream().map(storeMapper::convertToEntity).collect(Collectors.toSet()))
                .stream()
                .map(Store::getId)
                .collect(Collectors.toSet()), log);
    }
}
