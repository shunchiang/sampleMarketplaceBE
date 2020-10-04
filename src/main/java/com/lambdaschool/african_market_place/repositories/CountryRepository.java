package com.lambdaschool.african_market_place.repositories;

import com.lambdaschool.african_market_place.models.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Long> {
}
