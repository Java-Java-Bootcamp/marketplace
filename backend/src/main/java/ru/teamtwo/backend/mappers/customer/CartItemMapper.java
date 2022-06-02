package ru.teamtwo.backend.mappers.customer;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import ru.teamtwo.backend.mappers.BaseMapper;
import ru.teamtwo.backend.models.customer.CartItem;
import ru.teamtwo.backend.repository.customer.CustomerRepository;
import ru.teamtwo.backend.repository.product.ProductOfferRepository;
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
