package com.lambdaschool.african_market_place.controllers;

import com.lambdaschool.african_market_place.models.Listing;
import com.lambdaschool.african_market_place.services.HelperFunctions;
import com.lambdaschool.african_market_place.services.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

    @RestController
    @RequestMapping("/listings")
public class ListingController {

    @Autowired
    ListingService listservice;

    @Autowired
    HelperFunctions helperFunctions;

    // http://localhost:2020/listings/listings
    @GetMapping(value = "/listings", produces = "application/json")
    public ResponseEntity<?> getAllListings()
    {
        List<Listing> listings = listservice.findAll();
        return new ResponseEntity<>(listings, HttpStatus.OK);
    }

    // http://localhost:2020/listings/listing/{listingid}
    @GetMapping(value = "/listings/listing/{listingid}", produces = "application/json")
    public ResponseEntity<?> getListingById(@PathVariable long listingid)
    {
        Listing listing = listservice.findById(listingid);
        return new ResponseEntity<>(listing, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    // http://localhost:2020/listings/listing
    @PostMapping(value = "/listing", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> postNewListing(@Valid @RequestBody Listing newListing)
    {
        newListing.setListingid(0);
        newListing.setUser(helperFunctions.getCurrentUser());
        newListing = listservice.save(newListing);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newListingURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{listingid}")
                .buildAndExpand(newListing.getListingid())
                .toUri();
        responseHeaders.setLocation(newListingURI);

        return new ResponseEntity<>(null,
                responseHeaders,
                HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    // http://localhost:2020/listings/listing/{listingid}
    @PatchMapping(value = "/listing/{listingid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> editPartListing(@Valid @RequestBody Listing updateListing, @PathVariable long listingid)
    {
        updateListing = listservice.update(listingid,
                updateListing);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    // http://localhost:2020/listings/listing/{listingid}
    @PutMapping(value = "/listing/{listingid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> editEntireListing(@Valid @RequestBody Listing newListing, @PathVariable long listingid)
    {
        newListing.setListingid(listingid);
        listservice.save(newListing);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MERCHANT')")
    //http://localhost:2020/listings/listing/{listingid}
    @DeleteMapping(value = "/listing/{listingid}")
    public ResponseEntity<?> deleteListingById(@PathVariable long listingid)
    {
        listservice.delete(listingid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
