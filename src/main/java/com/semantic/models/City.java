package com.semantic.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class City implements Serializable {

    List<Restaurant> restaurants = new ArrayList<>();
    List<Event> events = new ArrayList<>();
    List<RealEstate> realEstate = new ArrayList<>();

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<RealEstate> getRealEstate() {
        return realEstate;
    }

    public void setRealEstate(List<RealEstate> realEstate) {
        this.realEstate = realEstate;
    }
}
