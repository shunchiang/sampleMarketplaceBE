package com.lambdaschool.african_market_place.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "countries")
public class Country {

    //#region fields/costructors
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long countrycode;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "country",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    @JsonIgnoreProperties(value = "country", allowSetters = true)
    private Set<City> cities = new HashSet<>();

    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }
    //#endregion

    //#region getters/setters

    public long getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(long countrycode) {
        this.countrycode = countrycode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<City> getCities() {
        return cities;
    }

    public void setCities(Set<City> cities) {
        this.cities = cities;
    }

    //#endregion

}
