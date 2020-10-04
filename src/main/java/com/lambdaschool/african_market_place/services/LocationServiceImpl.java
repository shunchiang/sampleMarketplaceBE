package com.lambdaschool.african_market_place.services;

import com.lambdaschool.african_market_place.exceptions.ResourceNotFoundException;
import com.lambdaschool.african_market_place.models.City;
import com.lambdaschool.african_market_place.models.Location;
import com.lambdaschool.african_market_place.models.Order;
import com.lambdaschool.african_market_place.models.User;
import com.lambdaschool.african_market_place.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "locationService")
public class LocationServiceImpl implements LocationService
{
    @Autowired
    LocationRepository locationRepository;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserService userService;

    @Override
    public List<Location> getAllLocations() throws
            ResourceNotFoundException
    {
        List<Location> allLocations = new ArrayList<>();
         locationRepository.findAll().iterator().forEachRemaining(allLocations :: add);

         return allLocations;
    }

    @Override
    public Location findLocationById(long locationCode) throws
            ResourceNotFoundException
    {
        Location location = locationRepository.findById(locationCode)
                .orElseThrow(() -> new ResourceNotFoundException("Location " + locationCode + " not found!"));
        return location;
    }

    @Override
    public Location findLocationByUserId(long userId) throws
            ResourceNotFoundException
    {
        User locationUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User " + userId + " not found!"));

        return locationUser.getLocation();
    }

    @Override
    public Location findLocationByOrderId(long orderCode) throws
            ResourceNotFoundException
    {
        Order locationOrder = orderRepository.findById(orderCode)
                .orElseThrow(() -> new ResourceNotFoundException("Order " + orderCode + " not found!"));
        return locationOrder.getLocation();
    }

    @Override
    public List<Location> findLocationByCityId(long cityCode) throws
            ResourceNotFoundException
    {
        List<Location> cityLocations = new ArrayList<>();

        for (Location l: cityRepository.findById(cityCode).get().getLocations())
        {
            cityLocations.add(l);
        }

        return cityLocations;
    }


    @Override
    public Location save(Location location) {
        Location newLocation = new Location();

        if(location.getLocationcode() != 0)
        {
            locationRepository.findById(location.getLocationcode())
                    .orElseThrow(() -> new ResourceNotFoundException("Location " + location.getLocationcode() + " not found!"));
            newLocation.setLocationcode(location.getLocationcode());
        }

            newLocation.setAddress(location.getAddress());
            newLocation.setCity(location.getCity());
            newLocation.setZip(location.getZip());

            location.getUsers().clear();
            for (User u : location.getUsers())
            {
                User user = userService.findUserById(u.getUserid());
                newLocation.getUsers().add(user);
            }

        return locationRepository.save(newLocation);
    }

    @Override
    public void deleteLocationById(long locationCode) {
        locationRepository.deleteById(locationCode);

    }

    @Override
    public void deleteAllLocations() {
        locationRepository.deleteAll();
    }
}
