package com.jemersoft.testcache.repository;

import com.jemersoft.testcache.domain.CustomerDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CustomerDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, Long> {}
