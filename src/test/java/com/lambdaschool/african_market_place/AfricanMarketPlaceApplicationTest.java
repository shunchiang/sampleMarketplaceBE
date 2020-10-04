package com.lambdaschool.african_market_place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

//@EnableJpaAuditing
@SpringBootApplication
public class AfricanMarketPlaceApplicationTest {

//    @Autowired
//    private static Environment env;
//
//    private static boolean stop = false;
//
//    private static void checkEnvironmentVariable(String envvar)
//    {
//        if (System.getenv(envvar) == null)
//        {
//            stop = true;
//        }
//    }

    public static void main(String[] args) {
        // Check to see if the environment variables exists. If they do not, stop execution of application.
//        checkEnvironmentVariable("OAUTHCLIENTID");
//        checkEnvironmentVariable("OAUTHCLIENTSECRET");
//
//        if (!stop)
//        {
            SpringApplication.run(AfricanMarketPlaceApplicationTest.class,
                    args);
//        }
    }

}
