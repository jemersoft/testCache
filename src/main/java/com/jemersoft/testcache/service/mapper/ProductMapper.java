package com.jemersoft.testcache.service.mapper;

import com.jemersoft.testcache.domain.Product;
import com.jemersoft.testcache.domain.ProductCategory;
import com.jemersoft.testcache.service.dto.ProductCategoryDTO;
import com.jemersoft.testcache.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {
    @Mapping(target = "productCategory", source = "productCategory", qualifiedByName = "productCategoryName")
    ProductDTO toDto(Product s);

    @Named("productCategoryName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    ProductCategoryDTO toDtoProductCategoryName(ProductCategory productCategory);
}
