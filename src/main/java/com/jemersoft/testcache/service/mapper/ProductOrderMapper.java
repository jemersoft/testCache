package com.jemersoft.testcache.service.mapper;

import com.jemersoft.testcache.domain.Product;
import com.jemersoft.testcache.domain.ProductOrder;
import com.jemersoft.testcache.domain.ShoppingCart;
import com.jemersoft.testcache.service.dto.ProductDTO;
import com.jemersoft.testcache.service.dto.ProductOrderDTO;
import com.jemersoft.testcache.service.dto.ShoppingCartDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductOrder} and its DTO {@link ProductOrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductOrderMapper extends EntityMapper<ProductOrderDTO, ProductOrder> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productName")
    @Mapping(target = "cart", source = "cart", qualifiedByName = "shoppingCartId")
    ProductOrderDTO toDto(ProductOrder s);

    @Named("productName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProductDTO toDtoProductName(Product product);

    @Named("shoppingCartId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShoppingCartDTO toDtoShoppingCartId(ShoppingCart shoppingCart);
}
