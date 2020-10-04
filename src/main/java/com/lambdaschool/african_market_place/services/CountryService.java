package com.lambdaschool.african_market_place.services;

import com.lambdaschool.african_market_place.models.Country;

import java.util.List;

public interface CountryService {
    Country findCountryById(long countrycode);

    List<Country> findAllCountries();

    Country save(Country country);
}
