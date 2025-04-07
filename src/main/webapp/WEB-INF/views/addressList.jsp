<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Address Book</title>
</head>
<body>
<h1>Address Book</h1>

<c:if test="${not empty message}">
    <div style="color: green;">${message}</div>
</c:if>

<h2>Add New Address</h2>
<form action="address" method="post">
    <input type="hidden" name="action" value="add">
    <label>Person Name: <input type="text" name="personName" required></label><br>
    <label>Street Number: <input type="text" name="streetNumber"></label><br>
    <label>Street Name: <input type="text" name="streetName"></label><br>
    <label>City: <input type="text" name="cityName"></label><br>
    <button type="submit">Add Address</button>
</form>

<h2>Search Address</h2>
<form action="address" method="get">
    <input type="hidden" name="action" value="search">
    <label>Person Name: <input type="text" name="personName" required></label>
    <button type="submit">Search</button>
</form>

<h2>Delete Address</h2>
<form action="address" method="post">
    <input type="hidden" name="action" value="delete">
    <label>Person Name: <input type="text" name="personName" required></label>
    <button type="submit">Delete</button>
</form>

<h2>Address List</h2>
<c:choose>
    <c:when test="${empty addresses}">
        <p>No addresses found.</p>
    </c:when>
    <c:otherwise>
        <table border="1">
            <tr>
                <th>Person</th>
                <th>Street Number</th>
                <th>Street Name</th>
                <th>City</th>
            </tr>
            <c:forEach var="address" items="${addresses}">
                <tr>
                    <td>${address.personName}</td>
                    <td>${address.streetNumber}</td>
                    <td>${address.streetName}</td>
                    <td>${address.cityName}</td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
</body>
</html>
