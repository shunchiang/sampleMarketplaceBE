package com.lambdaschool.african_market_place.services;

import com.lambdaschool.african_market_place.models.City;
import com.lambdaschool.african_market_place.models.Location;

import java.util.List;

public interface LocationService {
    List<Location> getAllLocations();

    Location findLocationByUserId(long userId);

    Location findLocationById(long locationCode);

    Location findLocationByOrderId(long orderCode);

    Location save(Location location);

    List<Location> findLocationByCityId(long cityId);

    void deleteLocationById(long locationCode);

    void deleteAllLocations();


}
