package com.jemersoft.testcache.service.impl;

import com.jemersoft.testcache.domain.CustomerDetails;
import com.jemersoft.testcache.repository.CustomerDetailsRepository;
import com.jemersoft.testcache.service.CustomerDetailsService;
import com.jemersoft.testcache.service.dto.CustomerDetailsDTO;
import com.jemersoft.testcache.service.mapper.CustomerDetailsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CustomerDetails}.
 */
@Service
@Transactional
public class CustomerDetailsServiceImpl implements CustomerDetailsService {

    private final Logger log = LoggerFactory.getLogger(CustomerDetailsServiceImpl.class);

    private final CustomerDetailsRepository customerDetailsRepository;

    private final CustomerDetailsMapper customerDetailsMapper;

    public CustomerDetailsServiceImpl(CustomerDetailsRepository customerDetailsRepository, CustomerDetailsMapper customerDetailsMapper) {
        this.customerDetailsRepository = customerDetailsRepository;
        this.customerDetailsMapper = customerDetailsMapper;
    }

    @Override
    public CustomerDetailsDTO save(CustomerDetailsDTO customerDetailsDTO) {
        log.debug("Request to save CustomerDetails : {}", customerDetailsDTO);
        CustomerDetails customerDetails = customerDetailsMapper.toEntity(customerDetailsDTO);
        customerDetails = customerDetailsRepository.save(customerDetails);
        return customerDetailsMapper.toDto(customerDetails);
    }

    @Override
    public CustomerDetailsDTO update(CustomerDetailsDTO customerDetailsDTO) {
        log.debug("Request to update CustomerDetails : {}", customerDetailsDTO);
        CustomerDetails customerDetails = customerDetailsMapper.toEntity(customerDetailsDTO);
        customerDetails = customerDetailsRepository.save(customerDetails);
        return customerDetailsMapper.toDto(customerDetails);
    }

    @Override
    public Optional<CustomerDetailsDTO> partialUpdate(CustomerDetailsDTO customerDetailsDTO) {
        log.debug("Request to partially update CustomerDetails : {}", customerDetailsDTO);

        return customerDetailsRepository
            .findById(customerDetailsDTO.getId())
            .map(existingCustomerDetails -> {
                customerDetailsMapper.partialUpdate(existingCustomerDetails, customerDetailsDTO);

                return existingCustomerDetails;
            })
            .map(customerDetailsRepository::save)
            .map(customerDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerDetails");
        return customerDetailsRepository.findAll(pageable).map(customerDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CustomerDetailsDTO> findOne(Long id) {
        log.debug("Request to get CustomerDetails : {}", id);
        return customerDetailsRepository.findById(id).map(customerDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CustomerDetails : {}", id);
        customerDetailsRepository.deleteById(id);
    }
}
