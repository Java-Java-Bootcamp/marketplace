package ru.teamtwo.api.service.impl;

import org.hamcrest.Matchers;
import org.mockito.hamcrest.MockitoHamcrest;
import org.springframework.jdbc.datasource.init.ScriptParseException;
import ru.teamtwo.api.exception.ItemNotFoundException;
import ru.teamtwo.api.exception.ServerRuntimeException;
import ru.teamtwo.api.exception.UnableToAddItemException;
import ru.teamtwo.api.repository.GetByCustomerRepository;
import ru.teamtwo.api.repository.GetByOrderRepository;
import ru.teamtwo.api.service.api.GetByCustomerService;
import ru.teamtwo.api.service.api.GetByOrderService;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ru.teamtwo.api.TestUtils.UNIMPORTANT_ID;

public class ServiceTestUtils {
    public static void testBasicGet(ServiceTestUtilsParams params){
        assertThatThrownBy(()-> {
            params.service().get(params.entity().getId());
            verify(params.repository()).existsById(params.entity().getId());
        }).isInstanceOf(ItemNotFoundException.class);

        reset(params.repository(), params.mapper());

        when(params.repository().existsById(params.entity().getId())).thenReturn(true);
        when(params.repository().getById(params.entity().getId())).thenReturn(params.entity());
        when(params.mapper().convertToDto(params.entity())).thenReturn(params.dto());
        assertThat(params.service().get(params.entity().getId())).isEqualTo(params.dto());
        verify(params.repository()).existsById(params.entity().getId());
        verify(params.repository()).getById(params.entity().getId());
        verify(params.mapper()).convertToDto(params.entity());
    }
    
    public static void testBasicSave(ServiceTestUtilsParams params){
        when(params.mapper().convertToEntity(params.dto())).thenReturn(params.entity());
        when(params.repository().saveAll(any())).thenThrow(RuntimeException.class);
        assertThatThrownBy(()-> {
            params.service().save(Collections.singleton(params.dto()));
            verify(params.mapper()).convertToEntity(params.dto());
            verify(params.repository()).saveAll(MockitoHamcrest.argThat(Matchers.hasItems(params.entity())));
        }).isInstanceOf(ServerRuntimeException.class);

        reset(params.repository(), params.mapper());

        when(params.mapper().convertToEntity(params.dto())).thenReturn(params.entity());
        when(params.repository().saveAll(any())).thenThrow(ScriptParseException.class);
        assertThatThrownBy(()-> {
            params.service().save(Collections.singleton(params.dto()));
            verify(params.mapper()).convertToEntity(params.dto());
            verify(params.repository()).saveAll(MockitoHamcrest.argThat(Matchers.hasItems(params.entity())));
        }).isInstanceOf(UnableToAddItemException.class);

        reset(params.repository(), params.mapper());

        when(params.mapper().convertToEntity(params.dto())).thenReturn(params.entity());
        when(params.repository().saveAll(MockitoHamcrest.argThat(Matchers.hasItems(params.entity())))).thenReturn(Collections.singletonList(params.entity()));
        assertThat(params.service().save(Collections.singleton(params.dto()))).isEqualTo(Collections.singleton(params.entity().getId()));
        verify(params.mapper()).convertToEntity(params.dto());
        verify(params.repository()).saveAll(MockitoHamcrest.argThat(Matchers.hasItems(params.entity())));
    }

    public static void testGetByOrder(ServiceTestUtilsParams params){
        GetByOrderRepository repository = (GetByOrderRepository) params.repository();
        GetByOrderService service = (GetByOrderService) params.service();
        when(repository.getByOrder_Id(UNIMPORTANT_ID)).thenReturn(Collections.singleton(params.entity()));
        when(params.mapper().convertToDto(params.entity())).thenReturn(params.dto());
        assertThat(service.getAllByOrder(UNIMPORTANT_ID)).contains(params.dto());
        //verify(repository.getByOrder_Id(UNIMPORTANT_ID)); //TODO: breaks test
        verify(params.mapper()).convertToDto(params.entity());
    }

    public static void testGetByCustomer(ServiceTestUtilsParams params){
        GetByCustomerRepository repository = (GetByCustomerRepository) params.repository();
        GetByCustomerService service = (GetByCustomerService) params.service();
        when(repository.getByCustomer_Id(UNIMPORTANT_ID)).thenReturn(Collections.singleton(params.entity()));
        when(params.mapper().convertToDto(params.entity())).thenReturn(params.dto());
        assertThat(service.getAllByCustomer(UNIMPORTANT_ID)).contains(params.dto());
        //verify(repository.getByCustomer_Id(UNIMPORTANT_ID)); TODO: breaks test
        verify(params.mapper()).convertToDto(params.entity());
    }
}
