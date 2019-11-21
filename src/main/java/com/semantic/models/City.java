package com.semantic.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class City implements Serializable {

    List<String> restaurants = new ArrayList<>();
    List<String> events = new ArrayList<>();
    List<String> realEstate = new ArrayList<>();

    public List<String> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<String> restaurants) {
        this.restaurants = restaurants;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    public List<String> getRealEstate() {
        return realEstate;
    }

    public void setRealEstate(List<String> realEstate) {
        this.realEstate = realEstate;
    }
}
