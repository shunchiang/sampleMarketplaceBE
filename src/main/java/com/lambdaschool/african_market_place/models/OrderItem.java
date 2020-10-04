package com.lambdaschool.african_market_place.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "orderitems")
public class OrderItem
        extends Auditable
        implements Serializable {

    //#region fields/constructors
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderitemid;

    @ManyToOne
    @JoinColumn(name = "ordercode")
    @JsonIgnoreProperties(value = "order")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "orderitems")
    private Listing listing;

    @Column(nullable = false)
    private int quantity;

    public OrderItem() {
    }

    public OrderItem(Order order,Listing listing, int quantity) {
        this.order = order;
        this.listing = listing;
        this.quantity = quantity;
    }

    //#endregion

    //#region getters/setters


    public Long getOrderitemid() {
        return orderitemid;
    }

    public void setOrderitemid(Long orderitemid) {
        this.orderitemid = orderitemid;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Listing getListing() {
        return listing;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    //#endregion
}
