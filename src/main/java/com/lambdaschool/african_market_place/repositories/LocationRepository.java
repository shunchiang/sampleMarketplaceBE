package com.lambdaschool.african_market_place.repositories;

import com.lambdaschool.african_market_place.models.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long> {
    Location findByAddressAndZip(String address, String zip);

    List<Location> findByCity(Long cityCode);

}
