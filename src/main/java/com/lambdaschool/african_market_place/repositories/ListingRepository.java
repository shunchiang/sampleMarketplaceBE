package com.lambdaschool.african_market_place.repositories;

import com.lambdaschool.african_market_place.models.Listing;
import org.springframework.data.repository.CrudRepository;

public interface ListingRepository extends CrudRepository<Listing, Long> {
}
