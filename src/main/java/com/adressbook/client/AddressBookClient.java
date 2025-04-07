package com.adressbook.client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddressBookClient {
    private static final String BASE_URL = "http://localhost:8081/addressbook/address";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n===== Address Book Client =====");
            System.out.println("1. Add Address");
            System.out.println("2. Delete Address");
            System.out.println("3. Search Address");
            System.out.println("4. List All Addresses");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        addAddress();
                        break;
                    case 2:
                        deleteAddress();
                        break;
                    case 3:
                        searchAddress();
                        break;
                    case 4:
                        listAddresses();
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (IOException e) {
                System.out.println("Error communicating with server: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void addAddress() throws IOException {
        System.out.println("\n--- Add New Address ---");

        System.out.print("Person Name: ");
        String personName = scanner.nextLine();

        System.out.print("Street Number: ");
        String streetNumber = scanner.nextLine();

        System.out.print("Street Name: ");
        String streetName = scanner.nextLine();

        System.out.print("City: ");
        String cityName = scanner.nextLine();

        Map<String, String> params = new HashMap<>();
        params.put("action", "add");
        params.put("personName", personName);
        params.put("streetNumber", streetNumber);
        params.put("streetName", streetName);
        params.put("cityName", cityName);

        String response = sendPostRequest(BASE_URL, params);
        System.out.println("Response: " + response);
    }

    private static void deleteAddress() throws IOException {
        System.out.println("\n--- Delete Address ---");

        System.out.print("Person Name: ");
        String personName = scanner.nextLine();

        Map<String, String> params = new HashMap<>();
        params.put("action", "delete");
        params.put("personName", personName);

        String response = sendPostRequest(BASE_URL, params);
        System.out.println("Response: " + response);
    }

    private static void searchAddress() throws IOException {
        System.out.println("\n--- Search Address ---");

        System.out.print("Person Name: ");
        String personName = scanner.nextLine();

        String url = BASE_URL + "?action=search&personName=" + URLEncoder.encode(personName, StandardCharsets.UTF_8.toString());
        String response = sendGetRequest(url);
        System.out.println("Response: " + response);
    }

    private static void listAddresses() throws IOException {
        System.out.println("\n--- List All Addresses ---");

        String response = sendGetRequest(BASE_URL);
        System.out.println("Response: " + response);
    }

    private static String sendGetRequest(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    private static String sendPostRequest(String urlStr, Map<String, String> params) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, String> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8.toString()));
            postData.append('=');
            postData.append(URLEncoder.encode(param.getValue(), StandardCharsets.UTF_8.toString()));
        }

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = postData.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }
}
