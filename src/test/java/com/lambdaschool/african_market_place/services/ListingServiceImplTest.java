package com.lambdaschool.african_market_place.services;

import com.lambdaschool.african_market_place.AfricanMarketPlaceApplicationTest;
import com.lambdaschool.african_market_place.exceptions.ResourceNotFoundException;
import com.lambdaschool.african_market_place.models.Listing;
import com.lambdaschool.african_market_place.models.User;
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
public class ListingServiceImplTest {

    @Autowired
    private ListingService listingService;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws
            Exception {
        MockitoAnnotations.initMocks(this);

        List<Listing> initList = new ArrayList<>();
        listingService.findAll().iterator().forEachRemaining(initList :: add);
        for( Listing l : initList){
            System.out.println(l.getListingid() + " " + l.getListingname());
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void a_findAll() {
        assertEquals(2, listingService.findAll().size());
    }

    @Test
    public void findById() {
        assertEquals("Beef Stew", listingService.findById(10).getListingname());
    }

    @Test
    public void b_findListingsByUser() {
        assertEquals(2, listingService.findListingsByUser(6).size());
    }

    @Test
    public void save() {
        Listing newListing = new Listing("listingname",
                "test success",
                6.90, 420,
                "deviances",
                userService.findUserById(6),
                "https://localhost:69");
        Listing saveListing = listingService.save(newListing);
        assertEquals(3, listingService.findAll().size());
    }


    @Test(expected = NullPointerException.class)
    public void y_delete() {
        listingService.delete(11);
        assertEquals(null, listingService.findById(11));
    }

    @Test
    public void z_deleteAllListings() {
        listingService.deleteAllListings();
        assertEquals(0, listingService.findAll().size());
    }
}