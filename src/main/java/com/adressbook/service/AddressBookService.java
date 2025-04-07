package com.adressbook.service;

import com.adressbook.model.Address;
import com.adressbook.model.AddressBookException;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBookService {
    private static AddressBookService instance;

    private AddressBookService() {}

    public static synchronized AddressBookService getInstance() {
        if (instance == null) {
            instance = new AddressBookService();
        }
        return instance;
    }

    public void registerAddress(String personName, String streetNumber, String streetName, String cityName) throws AddressBookException {
        if (personName == null || personName.trim().isEmpty()) {
            throw new AddressBookException("Person name cannot be empty");
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO addresses (person_name, street_number, street_name, city_name) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, personName);
            stmt.setString(2, streetNumber);
            stmt.setString(3, streetName);
            stmt.setString(4, cityName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new AddressBookException("Error registering address: " + e.getMessage());
        }
    }

    public void deleteAddress(String personName) throws AddressBookException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM addresses WHERE person_name = ?")) {
            stmt.setString(1, personName);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new AddressBookException("Address not found for person: " + personName);
            }
        } catch (SQLException e) {
            throw new AddressBookException("Error deleting address: " + e.getMessage());
        }
    }

    public Address searchAddress(String personName) throws AddressBookException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM addresses WHERE person_name = ?")) {
            stmt.setString(1, personName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Address(
                        rs.getString("person_name"),
                        rs.getString("street_number"),
                        rs.getString("street_name"),
                        rs.getString("city_name")
                );
            } else {
                throw new AddressBookException("Address not found for person: " + personName);
            }
        } catch (SQLException e) {
            throw new AddressBookException("Error searching address: " + e.getMessage());
        }
    }

    public List<Address> listAddresses() {
        List<Address> addresses = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM addresses")) {
            while (rs.next()) {
                addresses.add(new Address(
                        rs.getString("person_name"),
                        rs.getString("street_number"),
                        rs.getString("street_name"),
                        rs.getString("city_name")
                ));
            }
        } catch (SQLException e) {
            // Return empty list on error
            System.err.println("Error listing addresses: " + e.getMessage());
        }
        return addresses;
    }
}
