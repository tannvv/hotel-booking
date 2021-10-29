<%-- 
    Document   : detailHotel
    Created on : Oct 14, 2021, 1:41:12 PM
    Author     : TanNV
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            .hotelImage{
                width: 200px;
                height: 300px;
            }
        </style>
    </head>
    <body>
        <h2>${requestScope.hotel.hotelName}<h2>
                <image class="hotelImage" src="images/${requestScope.hotel.image}"></br>
                Type of room : </bre>
                <c:forEach items="${listRoom}" var="room">
                    <form action="listCartAction" method="POST">
                        <c:if test="${room.type eq 1}">
                            <font>Single room :   </font><font>${room.price}</font>              
                            <input type="hidden" name="type" value="1">
                            <input type="hidden" name="hotelID" value="${requestScope.hotel.hotelID}">
                            <input type="hidden" name="id" value="${room.roomID}">
                            <input type="hidden" name="action" value="buy">
                            <input type="submit" value="Add to cart">
                        </c:if>
                        <c:if test="${room.type eq 2}">
                            <font>Double room :   </font><font>${room.price}</font>              
                            <input type="hidden" name="type" value="2">
                            <input type="hidden" name="hotelID" value="${requestScope.hotel.hotelID}">
                            <input type="hidden" name="id" value="${room.roomID}">
                            <input type="hidden" name="action" value="buy">
                            <input type="submit" value="Add to cart">
                        </c:if>
                        <c:if test="${room.type eq 3}">
                            <font>Family room :   </font><font>${room.price}</font>              
                            <input type="hidden" name="type" value="3">
                            <input type="hidden" name="hotelID" value="${requestScope.hotel.hotelID}">
                            <input type="hidden" name="id" value="${room.roomID}">
                            <input type="hidden" name="action" value="buy">
                            <input type="submit" value="Add to cart">
                        </c:if>
                    </form>
                </c:forEach>

                <footer>
                    <div class="container" style="background-color:#f1f1f1">
                        <a href="loadListAction"> View list hotel</a> -  
                    <hr>
                        <c:if test="${sessionScope.inforUser != null}">
                            <a href="listCartAction?action=view">View shopping cart</a>
                             </br>${sessionScope.inforUser.name}-<a href="loadListAction">
                                            <a href="logOutAction">Log out</a>
                        </c:if>
                          </br></br>
                    </div>
                </footer>
                </body>
                </html>
