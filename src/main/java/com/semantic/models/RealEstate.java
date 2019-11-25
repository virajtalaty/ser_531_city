package com.semantic.models;

public class RealEstate {
    String name;
    String street;
    String amount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "RealEstate{" +
                "name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
