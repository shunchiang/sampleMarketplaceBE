package com.lambdaschool.african_market_place.services;

import com.lambdaschool.african_market_place.exceptions.ResourceNotFoundException;
import com.lambdaschool.african_market_place.models.City;
import com.lambdaschool.african_market_place.models.Country;
import com.lambdaschool.african_market_place.repositories.CityRepository;
import com.lambdaschool.african_market_place.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Transactional
@Service(value = "countryService")
public class CountryServiceImpl implements CountryService
{
    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CityRepository cityRepository;

    @Override
    public Country findCountryById(long countrycode) throws
            ResourceNotFoundException
    {
        return countryRepository.findById(countrycode)
                .orElseThrow(() -> new ResourceNotFoundException("We can't find that"));
    }

    @Override
    public List<Country> findAllCountries()
    {
        List<Country> countries = new ArrayList<>();
        countryRepository.findAll().iterator().forEachRemaining(countries :: add);
        return countries;
    }

    @Transactional
    @Override
    public Country save(Country country) throws
            ResourceNotFoundException
    {
        Country newCountry = new Country();

        if(country.getCountrycode() != 0)
        {
            countryRepository.findById(country.getCountrycode())
                    .orElseThrow(() -> new ResourceNotFoundException("Country " + country.getCountrycode() + " not found!"));
            newCountry.setCountrycode(country.getCountrycode());
        }

        newCountry.setName(country.getName());
        for(City c : country.getCities() )
        {

            newCountry.getCities().add(cityRepository.findById(c.getCitycode())
                    .orElseThrow(() -> new ResourceNotFoundException("City " + c.getCitycode() + " not found!")));
        }

        return countryRepository.save(newCountry);
    }
}
