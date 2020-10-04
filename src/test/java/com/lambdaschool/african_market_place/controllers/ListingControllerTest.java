package com.lambdaschool.african_market_place.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.african_market_place.AfricanMarketPlaceApplicationTest;
import com.lambdaschool.african_market_place.models.*;
import com.lambdaschool.african_market_place.services.*;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AfricanMarketPlaceApplicationTest.class)
//@AutoConfigureMockMvc( = false)
@WithMockUser(username = "admin", roles = {"MERCHANT", "USER", "ADMIN"})
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ListingControllerTest
{

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private ListingService listingService;

    private List<Listing> listingList;

    @Before
    public void setUp() throws Exception {
        listingList = new ArrayList<>();

        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();


        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("merchant");

        Country country1 = new Country("Rwanda");

        City c1 = new City(country1, "Wakanda");


        Location loc1 = new Location(c1, "12345", "example Test Lane");

        User u1 = new User("username test",
                "111-1111",
                "lameguy@email.com",
                "Boris",
                "Yeltsin",
                "RussiaRules",
                loc1);
        u1.setUserid(5);
        u1.getRoles().add(new UserRoles(u1, r1));
        u1.getRoles().add(new UserRoles(u1, r2));
        u1.getRoles().add(new UserRoles(u1, r3));

        Listing listing1 = new Listing("Listy Listy",
                "I could really use a list right now",
                600.0,
                6,
                "Rap song",
                u1,
                "exampleabc.com/coolshit.jpg");
        listing1.setListingid(50);
        Listing listing2 = new Listing("Uzbekistan",
                "Poor and white",
                600.0,
                6,
                "Nation State",
                u1,
                "exampleabc.com/coolshit.jpg");
        listing1.setListingid(51);
        u1.getListings().add(listing1);
        u1.getListings().add(listing2);

        listingList.add(listing1);
        listingList.add(listing2);
    }

    @After
    public void tearDown() throws Exception {
    }

    private void assertEquals(String jsonResult, String usersAsJson)
    {
    }

    @Test
    public void getAllListings() throws Exception
    {
        var url = "/listings/listings";
        Mockito.when(listingService.findAll()).thenReturn(listingList);

        var builder = get(url).accept(MediaType.APPLICATION_JSON);
        var result = mockMvc.perform(builder).andReturn();
        var jsonResult = result.getResponse().getContentAsString();

        var jsonLists = new ObjectMapper().writeValueAsString(listingList);

        assertEquals(jsonResult, jsonLists);

    }

    @Test
    public void getListingById() throws Exception
    {
        var url = "/listings/listing/50";
        Mockito.when(listingService.findById(50)).thenReturn(listingList.get(0));

        var builder = get(url).accept(MediaType.APPLICATION_JSON);
        var result = mockMvc.perform(builder).andReturn();
        var jsonResult = result.getResponse().getContentAsString();

        var jsonList = new ObjectMapper().writeValueAsString(listingList.get(0));

        assertEquals(jsonResult, jsonList);
    }

    @Test
    public void postNewListing() throws Exception
    {
        var url = "/listings/listing";

        Country country1 = new Country("USA");

        City c1 = new City(country1, "Hellville");

        Location loc2 = new Location(c1, "666", "123 devil's lane" );

        User u2 = new User("teaNcrumpets",
                "111-1111",
                "lameguy@email.com",
                "Winston",
                "Churchill",
                "UKRules",
                loc2);
        u2.setUserid(5);


        Listing testNewListing = new Listing();
        testNewListing.setListingid(52);
        testNewListing.setListingname("New Listing");
        testNewListing.setDescription("Very shiny");
        testNewListing.setCategory("Shoes");
        testNewListing.setPrice(6000.00);
        testNewListing.setQuantity(3);
        testNewListing.setUser(u2);
        testNewListing.setImageurl("zappos.com/fancy.jpg");

        ObjectMapper mapper = new ObjectMapper();
                String newListing = mapper.writeValueAsString(testNewListing);

        Mockito.when(listingService.save(any(Listing.class))).thenReturn(testNewListing);

        var builder = MockMvcRequestBuilders
            .post(url).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newListing);

        mockMvc.perform(builder).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void editPartListing() throws Exception
    {
        String url = "/listings/listing/51";

        Country country1 = new Country("USA");

        City c1 = new City(country1, "Hellville");

        Location loc2 = new Location(c1, "666", "123 devil's lane" );

        User u2 = new User("teaNcrumpets",
                "111-1111",
                "lameguy@email.com",
                "Winston",
                "Churchill",
                "UKRules",
                loc2);
        u2.setUserid(5);

        Listing newListing = new Listing("New Listing",
                "very shiny",
                500.00,
                50,
                "Shoes",
                u2,
                "etcetc");

        ObjectMapper mapper = new ObjectMapper();
        String listingString = mapper.writeValueAsString(newListing);

        Mockito.when(listingService.save(any(Listing.class))).thenReturn(newListing);

        RequestBuilder builder = MockMvcRequestBuilders.put(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(listingString);
        mockMvc.perform(builder).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void editEntireListing() throws Exception
    {
        String url = "/listings/listing/51";

        Country country1 = new Country("USA");

        City c1 = new City(country1, "Hellville");

        Location loc2 = new Location(c1, "666", "123 devil's lane" );

        User u2 = new User("teaNcrumpets",
                "111-1111",
                "lameguy@email.com",
                "Winston",
                "Churchill",
                "UKRules",
                loc2);
        u2.setUserid(5);

        Listing newListing = new Listing("New Listing",
                "very shiny",
                500.00,
                50,
                "Shoes",
                u2,
                "etcetc");

        ObjectMapper mapper = new ObjectMapper();
        String listingString = mapper.writeValueAsString(newListing);

        Mockito.when(listingService.save(any(Listing.class))).thenReturn(newListing);

        RequestBuilder builder = MockMvcRequestBuilders.put(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(listingString);
        mockMvc.perform(builder).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteListingById() throws Exception
    {
        var url = "/listings/listing/50";
        var builder = MockMvcRequestBuilders.delete(url)
                                                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(builder).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
    }
}