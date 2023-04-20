package com.jemersoft.testcache.service;

import com.jemersoft.testcache.service.dto.ShoppingCartDTO;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.jemersoft.testcache.domain.ShoppingCart}.
 */
@Cacheable("shopping_carts")
public interface ShoppingCartService {
    /**
     * Save a shoppingCart.
     *
     * @param shoppingCartDTO the entity to save.
     * @return the persisted entity.
     */
    ShoppingCartDTO save(ShoppingCartDTO shoppingCartDTO);

    /**
     * Updates a shoppingCart.
     *
     * @param shoppingCartDTO the entity to update.
     * @return the persisted entity.
     */
    ShoppingCartDTO update(ShoppingCartDTO shoppingCartDTO);

    /**
     * Partially updates a shoppingCart.
     *
     * @param shoppingCartDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShoppingCartDTO> partialUpdate(ShoppingCartDTO shoppingCartDTO);

    /**
     * Get all the shoppingCarts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShoppingCartDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shoppingCart.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShoppingCartDTO> findOne(Long id);

    /**
     * Delete the "id" shoppingCart.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
