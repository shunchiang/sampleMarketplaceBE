package com.lambdaschool.african_market_place.services;

import com.lambdaschool.african_market_place.AfricanMarketPlaceApplicationTest;
import com.lambdaschool.african_market_place.models.City;
import com.lambdaschool.african_market_place.models.Country;
import com.lambdaschool.african_market_place.models.Listing;
import com.lambdaschool.african_market_place.models.Location;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AfricanMarketPlaceApplicationTest.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocationServiceImplTest {
    @Autowired
    LocationService locationService;
    @Autowired
    CountryService countryService;
    @Autowired
    CityService cityService;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        List<Location> initList = new ArrayList<>();
        locationService.getAllLocations().iterator().forEachRemaining(initList::add);
        for( Location l : initList){
            System.out.println(l.getLocationcode() + " " + l.getAddress());
        }
        List<City> cityList = new ArrayList<>();
        cityService.findAllCities().iterator().forEachRemaining(cityList::add);
        for( City c : cityList){
            System.out.println(c.getCitycode() + " " + c.getCityname());
        }
        List<Country> countries = new ArrayList<>();
        countryService.findAllCountries().iterator().forEachRemaining(countries::add);
        for( Country co : countries){
            System.out.println(co.getCountrycode() + " " + co.getCountrycode());
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void a_getAllLocations() {
        assertEquals(1, locationService.getAllLocations().size());
    }

    @Test
    public void b_findLocationById() {
        assertEquals("12345 address", locationService.findLocationById(9)
                .getAddress());
    }

    @Test
    public void c_findLocationByUserId() {
        assertEquals("12345 address", locationService.findLocationByUserId(6)
                .getAddress());
    }

//    @Test
//    public void d_findLocationByOrderId() {
//    }

    @Test
    public void e_findLocationByCityId() throws Exception{
        List<City> cities = new ArrayList<>();
        cityService.findAllCities().iterator().forEachRemaining(cities::add);
        System.out.println(locationService.findLocationByCityId(cities.get(0).getCitycode()).get(0).getAddress());
        assertEquals("12345 address", locationService.findLocationByCityId(cities.get(0).getCitycode()).get(0).getAddress());
    }

    @Test
    public void f_findCityByCountryName() {
        List<City> cities = new ArrayList<>();
        cityService.findAllCities().iterator().forEachRemaining(cities::add);
        assertEquals("Wakanda", cities.get(0).getCityname());

    }

    @Test(expected = NullPointerException.class)
    public void g_save() {
        City city = new City(countryService.findCountryById(4),"new City");
        city = cityService.save(city);
        Location location = new Location(city, "test", "test");
        location = locationService.save(location);
        assertEquals(2, locationService.getAllLocations().size());
    }

    @Test
    public void h_deleteLocationById() {
        locationService.deleteLocationById(9);
        assertEquals(0, locationService.getAllLocations().size());
    }

    @Test
    public void i_deleteAllLocations() {
        locationService.deleteAllLocations();
        assertEquals(0, locationService.getAllLocations().size());
    }
}