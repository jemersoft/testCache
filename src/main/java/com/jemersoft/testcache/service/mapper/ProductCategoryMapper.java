package com.jemersoft.testcache.service.mapper;

import com.jemersoft.testcache.domain.ProductCategory;
import com.jemersoft.testcache.service.dto.ProductCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductCategory} and its DTO {@link ProductCategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductCategoryMapper extends EntityMapper<ProductCategoryDTO, ProductCategory> {}
