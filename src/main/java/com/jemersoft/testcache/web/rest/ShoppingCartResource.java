package com.jemersoft.testcache.web.rest;

import com.jemersoft.testcache.repository.ShoppingCartRepository;
import com.jemersoft.testcache.service.ShoppingCartService;
import com.jemersoft.testcache.service.dto.ShoppingCartDTO;
import com.jemersoft.testcache.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jemersoft.testcache.domain.ShoppingCart}.
 */
@RestController
@RequestMapping("/api")
public class ShoppingCartResource {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartResource.class);

    private static final String ENTITY_NAME = "shoppingCart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShoppingCartService shoppingCartService;

    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartResource(ShoppingCartService shoppingCartService, ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartService = shoppingCartService;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    /**
     * {@code POST  /shopping-carts} : Create a new shoppingCart.
     *
     * @param shoppingCartDTO the shoppingCartDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shoppingCartDTO, or with status {@code 400 (Bad Request)} if the shoppingCart has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shopping-carts")
    public ResponseEntity<ShoppingCartDTO> createShoppingCart(@Valid @RequestBody ShoppingCartDTO shoppingCartDTO)
        throws URISyntaxException {
        log.debug("REST request to save ShoppingCart : {}", shoppingCartDTO);
        if (shoppingCartDTO.getId() != null) {
            throw new BadRequestAlertException("A new shoppingCart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShoppingCartDTO result = shoppingCartService.save(shoppingCartDTO);
        return ResponseEntity
            .created(new URI("/api/shopping-carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shopping-carts/:id} : Updates an existing shoppingCart.
     *
     * @param id the id of the shoppingCartDTO to save.
     * @param shoppingCartDTO the shoppingCartDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoppingCartDTO,
     * or with status {@code 400 (Bad Request)} if the shoppingCartDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shoppingCartDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shopping-carts/{id}")
    public ResponseEntity<ShoppingCartDTO> updateShoppingCart(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ShoppingCartDTO shoppingCartDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ShoppingCart : {}, {}", id, shoppingCartDTO);
        if (shoppingCartDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shoppingCartDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shoppingCartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShoppingCartDTO result = shoppingCartService.update(shoppingCartDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shoppingCartDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /shopping-carts/:id} : Partial updates given fields of an existing shoppingCart, field will ignore if it is null
     *
     * @param id the id of the shoppingCartDTO to save.
     * @param shoppingCartDTO the shoppingCartDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoppingCartDTO,
     * or with status {@code 400 (Bad Request)} if the shoppingCartDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shoppingCartDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shoppingCartDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/shopping-carts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShoppingCartDTO> partialUpdateShoppingCart(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ShoppingCartDTO shoppingCartDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShoppingCart partially : {}, {}", id, shoppingCartDTO);
        if (shoppingCartDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shoppingCartDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shoppingCartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShoppingCartDTO> result = shoppingCartService.partialUpdate(shoppingCartDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shoppingCartDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shopping-carts} : get all the shoppingCarts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shoppingCarts in body.
     */
    @GetMapping("/shopping-carts")
    public ResponseEntity<List<ShoppingCartDTO>> getAllShoppingCarts(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ShoppingCarts");
        Page<ShoppingCartDTO> page = shoppingCartService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shopping-carts/:id} : get the "id" shoppingCart.
     *
     * @param id the id of the shoppingCartDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shoppingCartDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shopping-carts/{id}")
    public ResponseEntity<ShoppingCartDTO> getShoppingCart(@PathVariable Long id) {
        log.debug("REST request to get ShoppingCart : {}", id);
        Optional<ShoppingCartDTO> shoppingCartDTO = shoppingCartService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shoppingCartDTO);
    }

    /**
     * {@code DELETE  /shopping-carts/:id} : delete the "id" shoppingCart.
     *
     * @param id the id of the shoppingCartDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shopping-carts/{id}")
    public ResponseEntity<Void> deleteShoppingCart(@PathVariable Long id) {
        log.debug("REST request to delete ShoppingCart : {}", id);
        shoppingCartService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
