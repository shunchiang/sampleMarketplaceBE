package com.lambdaschool.african_market_place.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends Auditable
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ordercode;


    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
    private User user;

    @OneToMany(mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Column(nullable = false)
    @JsonIgnoreProperties(value = "order", allowSetters = true)
    private Set<OrderItem> orderItems;

    @ManyToOne
    @JoinColumn(name = "locationcode")
    @JsonIgnoreProperties(value = "orders", allowSetters = true)
    private Location location;

    public Order() {
    }

    public Order(Set<OrderItem> orderItems, Location location) {
        this.orderItems = orderItems;
        this.location = location;
    }

    public Order(Location location) {
        this.location = location;
    }

//#region getters/setters

    public long getOrdercode() {
        return ordercode;
    }

    public void setOrdercode(long ordercode) {
        this.ordercode = ordercode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


    //#endregion
}
