package ru.teamtwo.api.mappers.customer;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import ru.teamtwo.api.mappers.BaseMapper;
import ru.teamtwo.api.models.customer.CartItem;
import ru.teamtwo.api.repository.customer.CustomerRepository;
import ru.teamtwo.api.repository.product.ProductOfferRepository;
import ru.teamtwo.core.dtos.customer.CartItemDto;

@Mapper(componentModel = "spring")
public abstract class CartItemMapper implements BaseMapper<CartItem, CartItemDto> {
    @Autowired
    private ProductOfferRepository productOfferRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Mappings({
            @Mapping(target="customerId", source="cartItem.customer.id"),
            @Mapping(target="productOfferId", source= "cartItem.productOffer.id")
    })
    public abstract CartItemDto convertToDto(CartItem cartItem);
    public abstract CartItem convertToEntity(CartItemDto cartItemDto);
    @AfterMapping
    protected void afterMapping(CartItemDto cartItemDto, @MappingTarget CartItem.CartItemBuilder cartItem) {
        cartItem.customer(customerRepository.getById(cartItemDto.customerId()));
        cartItem.productOffer(productOfferRepository.getById(cartItemDto.productOfferId()));
    }
}
