package com.adressbook.model;

public class Address {
    private String streetName;
    private String streetNumber;
    private String cityName;
    private String personName;

    // Constructor
    public Address(String personName, String streetNumber, String streetName, String cityName) {
        this.personName = personName;
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.cityName = cityName;
    }

    // Getters and setters
    public String getPersonName() { return personName; }
    public void setPersonName(String personName) { this.personName = personName; }

    public String getStreetNumber() { return streetNumber; }
    public void setStreetNumber(String streetNumber) { this.streetNumber = streetNumber; }

    public String getStreetName() { return streetName; }
    public void setStreetName(String streetName) { this.streetName = streetName; }

    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    @Override
    public String toString() {
        return personName + ": " + streetNumber + " " + streetName + ", " + cityName;
    }
}

