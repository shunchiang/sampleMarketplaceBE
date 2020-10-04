package com.lambdaschool.african_market_place.services;

import com.lambdaschool.african_market_place.models.City;

import java.util.List;

public interface CityService {
    City findCityById(long cityCode);

    List<City> findAllCities();

    City save(City city);
}
