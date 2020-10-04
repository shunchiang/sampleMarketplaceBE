package com.lambdaschool.african_market_place.services;

import com.lambdaschool.african_market_place.exceptions.ResourceNotFoundException;
import com.lambdaschool.african_market_place.models.City;
import com.lambdaschool.african_market_place.models.Location;
import com.lambdaschool.african_market_place.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "cityService")
public class CityServiceImpl implements CityService
{
    @Autowired
    CityRepository cityRepository;

    @Override
    public City findCityById(long cityCode) {
        return cityRepository.findById(cityCode)
                .orElseThrow(() -> new ResourceNotFoundException("Can't find that city!"));
    }

    @Override
    public List<City> findAllCities() {
        List<City> allCities = new ArrayList<>();
        cityRepository.findAll().iterator().forEachRemaining(allCities :: add);

        return allCities;
    }

    @Transactional
    @Override
    public City save(City city) {
       City newCity = new City();

       if (city.getCitycode() != 0)
       {
           cityRepository.findById(city.getCitycode())
                   .orElseThrow(() -> new ResourceNotFoundException("City " + city.getCitycode() + " not found!"));
           newCity.setCitycode(city.getCitycode());
       }
       newCity.setCityname(city.getCityname());
       newCity.setCountry(city.getCountry());
       for(Location l : city.getLocations())
       {
        newCity.getLocations().add(new Location(l.getCity(), l.getZip(), l.getAddress()));
       }

       return cityRepository.save(newCity);
    }
}
