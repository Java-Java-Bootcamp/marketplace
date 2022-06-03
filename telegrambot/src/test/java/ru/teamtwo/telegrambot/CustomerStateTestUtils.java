package ru.teamtwo.telegrambot;

import ru.teamtwo.core.dtos.controller.product.ProductOfferController;
import ru.teamtwo.core.dtos.customer.CustomerDto;
import ru.teamtwo.telegrambot.model.customer.CustomerState;
import ru.teamtwo.telegrambot.service.api.stage.Stage;

import java.util.HashSet;


public class CustomerStateTestUtils {

    public static CustomerState getCustomerState() {
        return new CustomerState(
                "string",
                "string",
                1L,
                Stage.WAITING_FOR_ADDRESS,
                "string",
                ProductOfferController.SortingTypeField.PRODUCT_RATING,
                ProductOfferController.SortingTypeAscDesc.ASC,
                10,
                15,
                new HashSet<>(),
                22L,
                new HashSet<>(),
                new HashSet<>()
        );
    }

    public static CustomerDto getCustomerDto() {
        CustomerState customerState = getCustomerState();
        return new CustomerDto(
                customerState.getUserId(),
                customerState.getName(),
                customerState.getAddress(),
                customerState.getStage().toString(),
                customerState.getSearchQuery(),
                customerState.getSortingTypeField().toString(),
                customerState.getSortingTypeAscDesc().toString(),
                customerState.getOffset(),
                customerState.getLimit(),
                customerState.getCurrentProductId()
        );
    }
}
