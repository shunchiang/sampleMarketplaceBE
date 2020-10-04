package com.lambdaschool.african_market_place.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.african_market_place.AfricanMarketPlaceApplication;
import com.lambdaschool.african_market_place.AfricanMarketPlaceApplicationTest;
import com.lambdaschool.african_market_place.models.*;
import com.lambdaschool.african_market_place.services.ListingService;
import com.lambdaschool.african_market_place.services.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ContextConfiguration(locations = "classpath*:/spring/applicationContext*.xml")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AfricanMarketPlaceApplicationTest.class)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
public class UserControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    private List<User> userList;
    private List<Listing> listingList;

    @Before
    public void setUp() throws
            Exception
    {
        userList = new ArrayList<>();
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
        User u1 = new User("username",
                "111-1111",
                "lameguy@email.com",
                "Boris",
                "Yeltsin",
                "RussiaRules",
                loc1);
        u1.setUserid(5);
        userList.add(u1);
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

    @Test
    public void listAllUsers() throws Exception{
        String apiUrl = "/users/users";

        Mockito.when(userService.findAll())
                .thenReturn(userList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);

        // the following actually performs a real controller call
        MvcResult r = mockMvc.perform(rb)
                .andReturn(); // this could throw an exception
        String tr = r.getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void getUserById() throws Exception{
        String apiUrl = "/users/user/5";

        Mockito.when(userService.findUserById(5))
                .thenReturn(userList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
                .andReturn(); // this could throw an exception
        String tr = r.getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList.get(0));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void getUserByName() throws
            Exception
    {
        String apiUrl = "/users/user/name/username";

        Mockito.when(userService.findByName("username"))
                .thenReturn(userList.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
                .andReturn(); // this could throw an exception
        String tr = r.getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList.get(0));

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void getUserLikeName() throws Exception{
        String apiUrl = "/users/user/name/like/user";

        Mockito.when(userService.findByNameContaining(any(String.class)))
                .thenReturn(userList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);

        // the following actually performs a real controller call
        MvcResult r = mockMvc.perform(rb)
                .andReturn(); // this could throw an exception
        String tr = r.getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(userList);

        System.out.println("Expect: " + er);
        System.out.println("Actual: " + tr);

        assertEquals("Rest API Returns List", er, tr);
    }

    @Test
    public void addNewUser() throws Exception{
        String apiUrl = "/users/register";

        // build a user
        User u1 = new User("derp", "password");
        u1.setUserid(100);
//        u1.setUsername("derp");
//        u1.setPassword("ILuvSleep!");

        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(u1);

        Mockito.when(userService.save(any(User.class)))
                .thenReturn(u1);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(userString);

        mockMvc.perform(rb)
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateFullUser() throws Exception{
        String apiUrl = "/users/user/{userid}";

        // build a user
        User u1 = new User();
        u1.setUserid(100);
        u1.setUsername("tigerUpdated");
        u1.setPassword("ILoveFood!");

        Mockito.when(userService.update(u1, 100))
                .thenReturn(userList.get(0));

        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(u1);

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl, 100L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(userString);

        mockMvc.perform(rb)
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateUser() throws Exception {
        String apiUrl = "/users/user/{userid}";

        // build a user
        User u1 = new User();
        u1.setUserid(100);
        u1.setUsername("tigerUpdated");
        u1.setPassword("ILoveFood!");

        Mockito.when(userService.update(u1, 100))
                .thenReturn(userList.get(0));

        ObjectMapper mapper = new ObjectMapper();
        String userString = mapper.writeValueAsString(u1);

        RequestBuilder rb = MockMvcRequestBuilders.put(apiUrl, 100L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(userString);

        mockMvc.perform(rb)
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteUserById() throws Exception{
        String apiUrl = "/users/user/{userid}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, "5")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb)
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteUserSelf() throws Exception{
        String apiUrl = "/users/user/{userid}";

        RequestBuilder rb = MockMvcRequestBuilders.delete(apiUrl, "100")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(rb)
                .andExpect(status().is2xxSuccessful())
                .andDo(MockMvcResultHandlers.print());
    }

//    @Test
//    public void getCurrentUserInfo() throws Exception{
//        String apiUrl = "/users/getuserinfo";
//
//        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
//                .accept(MediaType.APPLICATION_JSON);
//        MvcResult r = mockMvc.perform(rb)
//                .andReturn(); // this could throw an exception
//        String tr = r.getResponse()
//                .getContentAsString();
//
//        ObjectMapper mapper = new ObjectMapper();
//        String er = mapper.writeValueAsString(userList.get(0));
//
//        System.out.println("Expect: " + er);
//        System.out.println("Actual: " + tr);
//
//        assertEquals("Rest API Returns List", er, tr);
//    }

//    @Test
//    public void changeRole() {
//    }
}