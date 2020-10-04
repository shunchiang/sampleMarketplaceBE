package com.lambdaschool.african_market_place.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity /*** Table of fields ID, City (many to one), & Zipcode */
@Table(name = "locations")
public class Location {
    //#region fields/constructors
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long locationcode;

    @ManyToOne
    @JoinColumn(name = "citycode", nullable = false)
    @JsonIgnoreProperties(value = "locations", allowSetters = true)
    private City city;


    @Column(nullable = false)
    private String zip;

    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "location",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    @JsonIgnoreProperties(value = "location", allowSetters = true)
    private Set<User> users;

    public Location() {
    }

    public Location(City city, String zip, String address) {
        this.city = city;
        this.zip = zip;
        this.address = address;
    }
    //#endregion

    //#region getters/setters

    public Long getLocationcode() {
        return locationcode;
    }

    public void setLocationcode(long locationcode) {
        this.locationcode = locationcode;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    //#endregion
}
