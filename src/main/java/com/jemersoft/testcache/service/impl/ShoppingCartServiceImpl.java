package com.jemersoft.testcache.service.impl;

import com.jemersoft.testcache.domain.ShoppingCart;
import com.jemersoft.testcache.repository.ShoppingCartRepository;
import com.jemersoft.testcache.service.ShoppingCartService;
import com.jemersoft.testcache.service.dto.ShoppingCartDTO;
import com.jemersoft.testcache.service.mapper.ShoppingCartMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ShoppingCart}.
 */
@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

    private final ShoppingCartRepository shoppingCartRepository;

    private final ShoppingCartMapper shoppingCartMapper;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ShoppingCartMapper shoppingCartMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.shoppingCartMapper = shoppingCartMapper;
    }

    @Override
    public ShoppingCartDTO save(ShoppingCartDTO shoppingCartDTO) {
        log.debug("Request to save ShoppingCart : {}", shoppingCartDTO);
        ShoppingCart shoppingCart = shoppingCartMapper.toEntity(shoppingCartDTO);
        shoppingCart = shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public ShoppingCartDTO update(ShoppingCartDTO shoppingCartDTO) {
        log.debug("Request to update ShoppingCart : {}", shoppingCartDTO);
        ShoppingCart shoppingCart = shoppingCartMapper.toEntity(shoppingCartDTO);
        shoppingCart = shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    public Optional<ShoppingCartDTO> partialUpdate(ShoppingCartDTO shoppingCartDTO) {
        log.debug("Request to partially update ShoppingCart : {}", shoppingCartDTO);

        return shoppingCartRepository
            .findById(shoppingCartDTO.getId())
            .map(existingShoppingCart -> {
                shoppingCartMapper.partialUpdate(existingShoppingCart, shoppingCartDTO);

                return existingShoppingCart;
            })
            .map(shoppingCartRepository::save)
            .map(shoppingCartMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShoppingCartDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShoppingCarts");
        return shoppingCartRepository.findAll(pageable).map(shoppingCartMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShoppingCartDTO> findOne(Long id) {
        log.debug("Request to get ShoppingCart : {}", id);
        return shoppingCartRepository.findById(id).map(shoppingCartMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShoppingCart : {}", id);
        shoppingCartRepository.deleteById(id);
    }
}
