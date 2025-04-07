package com.adressbook.model;

import java.util.ArrayList;
import java.util.List;

public class AddressBook {
    private String name;
    private List<Address> addresses;

    public AddressBook(String name) {
        this.name = name;
        this.addresses = new ArrayList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public void register(Address address) throws AddressBookException {
        if (address == null) {
            throw new AddressBookException("Address cannot be null");
        }
        addresses.add(address);
    }

    public void delete(String personName) throws AddressBookException {
        boolean removed = addresses.removeIf(address -> address.getPersonName().equals(personName));
        if (!removed) {
            throw new AddressBookException("Address not found for person: " + personName);
        }
    }

    public Address search(String personName) throws AddressBookException {
        for (Address address : addresses) {
            if (address.getPersonName().equals(personName)) {
                return address;
            }
        }
        throw new AddressBookException("Address not found for person: " + personName);
    }

    public List<Address> list() {
        return new ArrayList<>(addresses);
    }
}
