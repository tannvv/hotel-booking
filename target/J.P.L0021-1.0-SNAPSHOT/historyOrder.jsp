<%-- 
    Document   : trackingOrder
    Created on : Sep 13, 2021, 8:12:15 PM
    Author     : TanNV
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <form action="searchOrderNameAction" method="POST">
            Enter name hotel to tracking order : <input type="text" name="hotelName" value="${hotelName}">
            <input type="submit" value="Search">
        </form>
        </br></br>
        <font color="red">${error}</font>

        <c:if test="${listHistoryOrder != null}">
            <table border="1px solid">
                <tr>
                    <td>Hotel name</td>
                    <td>Order ID  </td>
                    <td>Order date  </td>
                    <td>Check in </td>
                    <td>Check out</td>
                    <td>List of room  </td>
                    <td>Total cost </td>
                </tr>
                <c:forEach items="${listHistoryOrder}" var="historyOrder">
                    <tr>
                        <td>${historyOrder.hotelName}</td>
                        <td>${historyOrder.orderID}</td>
                        <td>${historyOrder.createDate}</td>
                        <td>${historyOrder.checkIn}</td>
                        <td>${historyOrder.checkOut}</td>
                        <td>
                            <c:forEach items="${historyOrder.detailOrder}" var="orderDetail">
                                <c:if test="${orderDetail.roomID.type eq 1}">
                                    Single room - (${orderDetail.quantity})
                                </c:if>
                                <c:if test="${orderDetail.roomID.type eq 2}">
                                    Double room - (${orderDetail.quantity})
                                </c:if>
                                <c:if test="${orderDetail.roomID.type eq 3}">
                                    Family room - (${orderDetail.quantity})
                                </c:if>
                            </c:forEach>
                        </td>
                        <td>${historyOrder.totalCost}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

        <footer>
            <div class="container" style="background-color:#f1f1f1">
                </br>${inforUser.name}-<a href="loadListAction">View list hotel</a> - <a href="listCartAction?action=view">View shopping cart</a> 
                - <a href="logOutAction">Log out</a>   </br></br>
            </div>
        </footer>
    </body>
</html>
