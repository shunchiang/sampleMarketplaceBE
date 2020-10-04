package com.lambdaschool.african_market_place.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lambdaschool.african_market_place.AfricanMarketPlaceApplicationTest;
import com.lambdaschool.african_market_place.models.Listing;
import com.lambdaschool.african_market_place.models.Role;
import com.lambdaschool.african_market_place.models.User;
import com.lambdaschool.african_market_place.models.UserRoles;
import com.lambdaschool.african_market_place.services.RoleService;
import com.lambdaschool.african_market_place.services.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AfricanMarketPlaceApplicationTest.class)
@WithMockUser(username = "admin", roles = {"ADMIN", "USER", "MERCHANT"})
public class RolesControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleService roleService;



    private List<Role> roles;

    @Before
    public void setUp() throws Exception {

    roles = new ArrayList<>();

        Role r5 = new Role("admin");
        r5.setRoleid(1);
        Role r6 = new Role("user");
        r6.setRoleid(2);
        Role r7 = new Role("merchant");
        r7.setRoleid(3);
        roles.add(r5);
        roles.add(r6);
        roles.add(r7);

        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

//
//        User admin = new User("username", "pasword");
//        admin.setUserid(10);
//        admin.getRoles().add(new UserRoles(admin, r5));
//        admin = userService.save(admin);
//
//        User vendor = new User("usernamer", "password");
//        vendor.setUserid(11);
//        vendor.getRoles().add(new UserRoles(vendor, r6));
//        vendor = userService.save(vendor);
//
//        User user = new User("ultimateuser", "password");
//        user.setUserid(12);
//        user.getRoles().add(new UserRoles(user, r7));
//        user = userService.save(user);


    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void listRoles() throws Exception{
        String apiUrl = "/roles/roles";
        Mockito.when(roleService.findAll()).thenReturn(roles);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(roles);

        assertEquals(er,tr);
    }

    @Test
    public void getRoleById() throws Exception{
        String apiUrl = "/roles/role/1";
        Mockito.when(roleService.findRoleById(1)).thenReturn(roles.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb).andReturn();
        String tr = r.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(roles.get(0));

        assertEquals(er,tr);
    }

    @Test
    public void getRoleByName() {
    }

    @Test
    public void addNewRole() throws Exception{
        String apiUrl = "/roles/role";


        Role r1 = new Role("useless");
        r1.setRoleid(24);

        ObjectMapper mapper = new ObjectMapper();
        String newRole = mapper.writeValueAsString(r1);

        Mockito.when(roleService.save(any(Role.class))).thenReturn(r1);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(newRole);
        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void putUpdateRole() throws Exception{
        String apiUrl = "/roles/role";

        Role r1 = new Role("useless");
        r1.setRoleid(24);

        ObjectMapper mapper = new ObjectMapper();
        String newRole = mapper.writeValueAsString(r1);

        Mockito.when(roleService.update(24, r1)).thenReturn(roles.get(0));

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(newRole);
        mockMvc.perform(rb).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
    }
}