package com.adressbook.web;
import java.io.IOException;

import com.adressbook.model.Address;
import com.adressbook.model.AddressBookException;
import com.adressbook.service.AddressBookService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;


@WebServlet("/address")
public class AddressServlet extends HttpServlet {
    private AddressBookService service = AddressBookService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("search".equals(action)) {
                String personName = request.getParameter("personName");
                Address address = service.searchAddress(personName);
                request.setAttribute("address", address);
                request.getRequestDispatcher("/WEB-INF/views/searchResult.jsp").forward(request, response);
            } else {
                // Default: list all addresses
                request.setAttribute("addresses", service.listAddresses());
                request.getRequestDispatcher("/WEB-INF/views/addressList.jsp").forward(request, response);
            }
        } catch (AddressBookException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                String personName = request.getParameter("personName");
                String streetNumber = request.getParameter("streetNumber");
                String streetName = request.getParameter("streetName");
                String cityName = request.getParameter("cityName");

                service.registerAddress(personName, streetNumber, streetName, cityName);
                request.setAttribute("message", "Address added successfully");
            } else if ("delete".equals(action)) {
                String personName = request.getParameter("personName");
                service.deleteAddress(personName);
                request.setAttribute("message", "Address deleted successfully");
            }

            // Redirect to the list view after any action
            request.setAttribute("addresses", service.listAddresses());
            request.getRequestDispatcher("/WEB-INF/views/addressList.jsp").forward(request, response);
        } catch (AddressBookException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
