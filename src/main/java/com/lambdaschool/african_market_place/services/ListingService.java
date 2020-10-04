package com.lambdaschool.african_market_place.services;

import com.lambdaschool.african_market_place.models.Listing;

import java.util.List;

public interface ListingService {
    List<Listing> findAll();

    Listing findById(long listingid);

    Listing save(Listing newListing);

    List<Listing> findListingsByUser(long userid);

    Listing update(long listingid, Listing updateListing);

    void delete(long listingid);

    void deleteAllListings();
}
