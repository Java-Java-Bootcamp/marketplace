package ru.teamtwo.api.service.impl.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.teamtwo.api.exception.ItemNotFoundException;
import ru.teamtwo.api.mappers.product.StoreMapper;
import ru.teamtwo.api.models.product.Store;
import ru.teamtwo.api.repository.product.StoreRepository;
import ru.teamtwo.api.service.api.product.StoreService;
import ru.teamtwo.core.dtos.product.StoreDto;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    @Override
    public StoreDto get(Long id) {
        if(storeRepository.existsById(id))
            return storeMapper.convert(storeRepository.getById(id));
        else
            throw new ItemNotFoundException();
    }

    @Override
    public Set<Long> save(Set<StoreDto> dtos) {
        return storeRepository
                .saveAll(dtos.stream().map(storeMapper::convert).collect(Collectors.toSet()))
                .stream()
                .map(Store::getId)
                .collect(Collectors.toSet());
    }
}
