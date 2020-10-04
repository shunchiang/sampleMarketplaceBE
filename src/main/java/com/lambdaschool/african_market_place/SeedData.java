package com.lambdaschool.african_market_place;

import com.github.javafaker.Faker;
import com.lambdaschool.african_market_place.models.*;
import com.lambdaschool.african_market_place.repositories.CityRepository;
import com.lambdaschool.african_market_place.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;


/**
 * SeedData puts both known and random data into the database. It implements CommandLineRunner.
 * <p>
 * CoomandLineRunner: Spring Boot automatically runs the run method once and only once
 * after the application context has been loaded.
 */
@Transactional
@Component
public class SeedData implements CommandLineRunner {
    /**
     * Connects the Restaurant Service to this process
     */
    @Autowired
    private ListingService listingService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CountryService countryService;

    @Autowired
    private OrderItemService orderItemService;

    /**
     * A Random generator is needed to randomly generate faker data.
     */
    private Random random = new Random();

    /**
     * Generates test, seed data for our application
     * First a set of known data is seeded into our database.
     * Second a random set of data using Java Faker is seeded into our database.
     * Note this process does not remove data from the database. So if data exists in the database
     * prior to running this process, that data remains in the database.
     *
     * @param args The parameter is required by the parent interface but is not used in this process.
     */
    @Transactional
    @Override
    public void run(String[] args) throws Exception {
        listingService.deleteAllListings();
        userService.deleteAll();
        roleService.deleteAll();
        locationService.deleteAllLocations();


        /** ROLES */

        Role r1 = new Role("admin");
        Role r2 = new Role("user");
        Role r3 = new Role("merchant");
        r1 = roleService.save(r1);
        r2 = roleService.save(r2);
        r3 = roleService.save(r3);

        /**COUNTRIES */
        Country country1 = new Country("Rwanda");
        country1 = countryService.save(country1);

        City c1 = new City(country1, "Wakanda");
        c1 = cityService.save(c1);

        country1.getCities().add(c1);

        /** CITIES */
        //Country country,
//        City c1 = new City(country1, "Wakanda");
//        country1.getCities().add(c1);
//        country1 = countryService.save(country1);


//        City c2 = new City(country1, "My City");
//        country1.getCities().add(c2);
//        c2 = cityService.save(c2);


        /** LOCATIONS */
        //City city, String zip, String address
        Location loc1 = new Location(c1, "123 Example St.", "12345 address");
//        loc1 = locationService.save(loc1);
        c1.getLocations().add(loc1);


        User admin = new User("username", null, null, null, null, "password", loc1);
        admin.getListings().add(new Listing("New listingasdf", "Listing description", 6.99, 14, "more food", admin, "https://pmcvariety.files.wordpress.com/2020/07/kanye-west-1-e1599269208476.jpg"));

        admin.getRoles().add(new UserRoles(admin, r1));
        admin = userService.save(admin);

        User vendor = new User("usernamer", null, null, null, null, "password", loc1);
        vendor.getRoles().add(new UserRoles(vendor, r3));

        vendor = userService.save(vendor);
        Listing l1 = new Listing("Eggs Benedict", "Listing description", 6.99, 14, "food", admin, "https://pmcvariety.files.wordpress.com/2020/07/kanye-west-1-e1599269208476.jpg");

        admin.getListings().add(l1);
        Listing l2 = new Listing("Beef Stew", "Listing description", 6.99, 14, "other food", admin, "https://pmcvariety.files.wordpress.com/2020/07/kanye-west-1-e1599269208476.jpg");

        admin.getListings().add(l2);

        User user = new User("ultimateuser", null, null, null, null, "password", loc1);
        user.getRoles().add(new UserRoles(user, r2));
//
//        Order o1 = new Order(loc1);
////        OrderItem oi1 = new OrderItem(o1, l1, 5);
////        oi1 = orderItemService.save(oi1);
//        o1.setUser(user);


//        o1.getOrderItems().add(new OrderItem(o1, l1, 5));
//        o1 = orderService.save(o1);
//        user.getOrders().add(o1);
//        o1 = orderService.save(o1);

        user = userService.save(user);


//        listingService.save(l1);
//        listingService.save(l2);


/**  using JavaFaker create a bunch of regular restaurants
 https://www.baeldung.com/java-faker
 https://www.baeldung.com/regular-expressions-java
 */

        Faker nameFaker = new Faker(new Locale("en-US"));

        // this section gets a unique list of namesry
        Set<String> countryNamesSet = new HashSet<>();
        for (int i = 0; i < 2; i++) {
            countryNamesSet.add(nameFaker.country().name());
        }

        Set<Country> countrySet = new HashSet<>();
        for (String countryName : countryNamesSet) {
            Country fakeCountry = new Country(countryName);
            fakeCountry = countryService.save(fakeCountry);

            int randomNumber = random.nextInt(3) + 1; // random number 1 through 10
            for (int j = 0; j < randomNumber; j++) {
                fakeCountry.getCities()

                        .add(new City(fakeCountry, nameFaker.gameOfThrones()
                                .city()));

                for (City c : fakeCountry.getCities()) {
                    int randomLocNumber = random.nextInt(3) + 1; // random number 1 through 10
                    for (int k = 0; k < randomLocNumber; k++) {
                        c.getLocations().add(new Location(c,
                                nameFaker.address().zipCode(),
                                nameFaker.address().streetAddress()));
                        for (Location l : c.getLocations()) {
                            int randomUserNumber = random.nextInt(3) + 1; // random number 1 through 10
                            for (int z = 0; z < randomUserNumber; z++) ;
                            {
                                User fakeUser = new User(
                                        nameFaker.company().name(),
                                        nameFaker.phoneNumber().phoneNumber(),
                                        nameFaker.internet().emailAddress(),
                                        nameFaker.name().firstName(),
                                        nameFaker.name().lastName(),
                                        "password",
                                        l);

                                fakeUser = userService.save(fakeUser);
                            }

                        }
                    }
                }
            }
        }

        List<String> categories = new ArrayList<>();
        categories.add("Animal Products");
        categories.add("Beans");
        categories.add("Cereals - Maize");
        categories.add("Cereals - Maize");
        categories.add("Cereals - OtherCereals - Rice");
        categories.add("Fruits");
        categories.add("Other");
        categories.add("Peas");
        categories.add("Roots & Tubers");
        categories.add("Seeds & Nuts");
        categories.add("Vegetables");

        List<String> commodityList = new ArrayList<>();

        commodityList.add("Eggs");
        commodityList.add("Exotic Eggs");
        commodityList.add("Local Eggs");
        commodityList.add("Milk");
        commodityList.add("Nile Perch");
        commodityList.add("Processed Honey");
        commodityList.add("Tilapia");
        commodityList.add("Unprocessed Honey");
        commodityList.add("Beef");
        commodityList.add("Goat Meat");
        commodityList.add("Pork");
        commodityList.add("Exotic Chicken");
        commodityList.add("Local Chicken");
        commodityList.add("Turkey");
        commodityList.add("Agwedde Beans");
        commodityList.add("Beans");
        commodityList.add("Beans (K132)");
        commodityList.add("Beans Canadian");
        commodityList.add("Beans Mwitemania");
        commodityList.add("Beans Rosecoco");
        commodityList.add("Black Beans (Dolichos)");
        commodityList.add("Dolichos (Njahi)");
        commodityList.add("Green Gram");
        commodityList.add("Kidney Beans");
        commodityList.add("Mixed Beans");
        commodityList.add("Mwezi Moja");
        commodityList.add("Nambale Beans");
        commodityList.add("Old Beans");
        commodityList.add("Red Beans");
        commodityList.add("Soya Beans");
        commodityList.add("White Beans");
        commodityList.add("Yellow Beans");
        commodityList.add("Dry Maize");
        commodityList.add("Green Maize");
        commodityList.add("Maize");
        commodityList.add("Maize Bran");
        commodityList.add("Maize Flour");
        commodityList.add("Barley");
        commodityList.add("Bulrush Millet");
        commodityList.add("Finger Millet");
        commodityList.add( "Millet Flour");
        commodityList.add("Millet Grain");
        commodityList.add("Pearl Millet");
        commodityList.add("White Millet");
        commodityList.add("Red Sorghum");
        commodityList.add("Sorghum");
        commodityList.add("Sorghum Flour");
        commodityList.add("Sorghum Grain");
        commodityList.add("White Sorghum");
        commodityList.add("Wheat");
        commodityList.add("Wheat Bran");
        commodityList.add("Wheat Flour");
        commodityList.add("Imported Rice");
        commodityList.add("Kahama Rice");
        commodityList.add("Kayiso Rice");
        commodityList.add("Kilombero Rice");
        commodityList.add("Mbeya Rice");
        commodityList.add("Morogoro Rice");
        commodityList.add("Paddy Rice");
        commodityList.add("Rice");
        commodityList.add("Rice Bran");
        commodityList.add("Super Rice");
        commodityList.add("Unprocessed/husked rice");
        commodityList.add("Upland Rice");
        commodityList.add("Avocado");
        commodityList.add("Apple Bananas");
        commodityList.add("Cavendish (Bogoya)");
        commodityList.add("Cooking Bananas");
        commodityList.add("Ripe Bananas");
        commodityList.add("Passion Fruits");
        commodityList.add("Lemons");
        commodityList.add("Limes");
        commodityList.add("Mangoes Local");
        commodityList.add("Pawpaw");
        commodityList.add("Mangoes Ngowe");
        commodityList.add("Oranges");
        commodityList.add("Pineapples");
        commodityList.add("Coffee (Arabica)");
        commodityList.add("Coffee (Robusta)");
        commodityList.add("Unprocessed Cotton");
        commodityList.add("Unprocessed Tea");
        commodityList.add("Tobacco");
        commodityList.add("Unprocessed Vanilla");
        commodityList.add("Chic Pea");
        commodityList.add("Cowpeas");
        commodityList.add("Dry Peas");
        commodityList.add( "Fresh Peas");
        commodityList.add("Green Peas");
        commodityList.add( "Peas");
        commodityList.add("Pigeon Peas");
        commodityList.add("Cassava Chips");
        commodityList.add("Cassava Flour");
        commodityList.add("Cassava Fresh");
        commodityList.add( "Dry Fermented Cassava");
        commodityList.add("Sun Dried Cassava");
        commodityList.add( "Red Irish Potatoes");
        commodityList.add("Round Potatoes");
        commodityList.add("Sweet Potatoes");
        commodityList.add("White Fleshed Sweet Potatoes");
        commodityList.add("White Irish Potatoes");
        commodityList.add( "Ground Nuts");
        commodityList.add("Simsim");
        commodityList.add("Sunflower Seed");
        commodityList.add("Sunflower Seed Cake");
        commodityList.add("Brinjal/Eggplant");
        commodityList.add("Cabbages");
        commodityList.add( "Capsicums");
        commodityList.add("Carrots");
        commodityList.add("Cauliflower");
        commodityList.add("Chillies");
        commodityList.add("Cucumber");
        commodityList.add("Ginger");
        commodityList.add("Kales");
        commodityList.add("Lettuce");
        commodityList.add("Onions Dry");
        commodityList.add("Spring Onions");
        commodityList.add("Tomatoes");


        List<User> userList = userService.findAll();
        for (User u : userList)
        {

            int randomListingNumber = random.nextInt(3) + 1; // random number 1 through 3

            for (int y = 0; y < randomListingNumber; y++){
                Listing newListing = new Listing(nameFaker.options().nextElement(commodityList),
                        nameFaker.lorem().sentence(),
                        nameFaker.number().randomDouble(2, 1, 100),
                        nameFaker.number().randomDigitNotZero(),
                        nameFaker.options().nextElement(categories),
                        u,
                        "https://ca.slack-edge.com/ESZCHB482-W015P677M1T-d9f28327bb26-512");

                newListing = listingService.save(newListing);

            }
        }
    }
}
