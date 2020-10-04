package com.lambdaschool.african_market_place.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity /*** table of fields ID,locations(one-to many),  Country(many-to-one), */
@Table(name = "cities")
public class City {
    //#region fields/constructors
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long citycode;

    @ManyToOne
    @JoinColumn(name = "countrycode", nullable = false)
    @JsonIgnoreProperties(value = "cities", allowSetters = true)
    private Country country;

//    @Column(nullable = false)
    private String cityname;

    @OneToMany(mappedBy = "city",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    @JsonIgnoreProperties(value = "city", allowSetters = true)
    private Set<Location> locations = new HashSet<>();

    public City() {
    }

    public City(String cityname) {
        this.cityname = cityname;
    }

    public City(Country country, String cityname) {
        this.country = country;
        this.cityname = cityname;
    }

    //#endregion

    //#region getters/setters

    public long getCitycode() {
        return citycode;
    }

    public void setCitycode(long citycode) {
        this.citycode = citycode;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
    //#endregion

}
