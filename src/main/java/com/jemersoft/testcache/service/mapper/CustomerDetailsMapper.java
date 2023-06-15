package com.jemersoft.testcache.service.mapper;

import com.jemersoft.testcache.domain.CustomerDetails;
import com.jemersoft.testcache.service.dto.CustomerDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CustomerDetails} and its DTO {@link CustomerDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface CustomerDetailsMapper extends EntityMapper<CustomerDetailsDTO, CustomerDetails> {}
