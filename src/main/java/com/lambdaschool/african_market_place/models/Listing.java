package com.lambdaschool.african_market_place.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "listings")
public class Listing extends Auditable{

    //#region fields/constructors

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long listingid;

    private String listingname;

    private String description;

    private String imageurl;

    private double price;

    private boolean available = true;

    private int quantity;

    private String category;

//    @Id
    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnoreProperties(value = "listings", allowSetters = true)
    private User user;

    @OneToMany(mappedBy = "ordercode",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private Set<Order> listingOrders;

    public Listing() {
    }

    public Listing(String listingname, String description, double price, int quantity, String category, User user, String imageurl) {
        this.listingname = listingname;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.user = user;
        this.imageurl = imageurl;
    }

    //#endregion

    //#region getters/setters
    public String getListingname() {
        return listingname;
    }

    public void setListingname(String listingname) {
        this.listingname = listingname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Transient
    @JsonIgnoreProperties(value = "hasvalueforprice")
    public boolean hasvalueforprice = false;
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        hasvalueforprice = true;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public long getListingid() {
        return listingid;
    }

    public void setListingid(long listingid) {
        this.listingid = listingid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Transient
    @JsonIgnoreProperties(value = "hasvalueforquantity")
    public boolean hasvalueforquantity = false;
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        hasvalueforquantity = true;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Set<Order> getListingOrders() {
        return listingOrders;
    }

    public void setListingOrders(Set<Order> listingOrders) {
        this.listingOrders = listingOrders;
    }

    public boolean isHasvalueforprice() {
        return hasvalueforprice;
    }

    public void setHasvalueforprice(boolean hasvalueforprice) {
        this.hasvalueforprice = hasvalueforprice;
    }

    public boolean isHasvalueforquantity() {
        return hasvalueforquantity;
    }

    public void setHasvalueforquantity(boolean hasvalueforquantity) {
        this.hasvalueforquantity = hasvalueforquantity;
    }
    //#endregion
}
