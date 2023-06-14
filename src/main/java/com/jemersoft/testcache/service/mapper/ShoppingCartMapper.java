package com.jemersoft.testcache.service.mapper;

import com.jemersoft.testcache.domain.CustomerDetails;
import com.jemersoft.testcache.domain.ShoppingCart;
import com.jemersoft.testcache.service.dto.CustomerDetailsDTO;
import com.jemersoft.testcache.service.dto.ShoppingCartDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShoppingCart} and its DTO {@link ShoppingCartDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShoppingCartMapper extends EntityMapper<ShoppingCartDTO, ShoppingCart> {
    @Mapping(target = "customerDetails", source = "customerDetails", qualifiedByName = "customerDetailsId")
    ShoppingCartDTO toDto(ShoppingCart s);

    @Named("customerDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CustomerDetailsDTO toDtoCustomerDetailsId(CustomerDetails customerDetails);
}
