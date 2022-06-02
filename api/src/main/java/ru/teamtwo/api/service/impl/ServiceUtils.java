package ru.teamtwo.api.service.impl;

import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.teamtwo.api.exception.ItemNotFoundException;
import ru.teamtwo.api.exception.ServerRuntimeException;
import ru.teamtwo.api.exception.UnableToAddItemException;
import ru.teamtwo.api.logging.LoggingUtils;
import ru.teamtwo.api.models.BaseEntity;

import java.util.Set;
import java.util.function.Supplier;

public class ServiceUtils {
    public static BaseEntity get(JpaRepository<? extends BaseEntity, Long> repository, Long id){
        if(repository.existsById(id))
            return repository.getById(id);
        else
            throw new ItemNotFoundException();
    }

    public static Set<Long> save(Supplier<Set<Long>> supplier, Logger log){
        try {
            return supplier.get();
        } catch (DataAccessException e) {
            throw new UnableToAddItemException();
        } catch (Exception e) {
            LoggingUtils.logException(log, e);
            throw new ServerRuntimeException();
        }
    }
}
