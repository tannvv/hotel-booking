<%-- 
    Document   : viewCart
    Created on : Jul 5, 2021, 9:03:40 PM
    Author     : Tan Nguyen
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart</title>
        <style type="text/css">

            section{
                padding-top: 30px;
            }
            .img{
                text-align: center;
                border: 1px solid green;
                padding:5px;
                margin : 5px;
                height: 250px;
                width: 200px;
                float: left;
            }
            footer{
                padding-top: 30px;
                clear: both;
                height: 120px;
                align-items: center;
            }
        </style>
    </head>
    <body>
        <font color="red">${error}</font>
        <h2 align="center">List cart</h2>
        <section>
            Check in : <font>${sessionScope.checkIn}</font> </br>
            Check out :  <font>${sessionScope.checkOut}</font></br>

            Update date to check in and check out :
            <form action="updateDateAction" method="POST">
                <font>       </font>Check in : <input id="checkIn" type="date" name="checkIn" value="${requestScope.checkInData}"> 
                Check out : <input id="checkOut" type="date" name="checkOut" value="${requestScope.checkOutData}">
                <input type="submit" value="Update">
            </form>
                <table border="1px solid green" align="center" width="90%">
                    <tr>
                        <th>Status</th>
                        <th>Hotel name </th>
                        <th>Room type</th>
                        <th>Amount </th>
                        <th>Price</th>
                        <th>Total</th>
                    </tr>

                    <c:forEach items="${sessionScope.listCart}" var="cart">
                        <form action="listCartAction" method="POST">
                            <tr>
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="roomID" value="${cart.roomID.roomID}">
                            <td><input type="checkbox" name="status" ${cart.status eq true ? "checked" : ""} value="true"></td>
                            <th>${cart.hotelID.hotelName}</th>
                            <td align="center">
                                <c:if test="${cart.roomID.type eq 1}">
                                    Single room 
                                </c:if>
                                <c:if test="${cart.roomID.type eq 2}">
                                    Double room
                                </c:if>
                                <c:if test="${cart.roomID.type eq 3}">
                                    Family room 
                                </c:if>                
                            </td>             
                            <td><input type="number" min="1" step="1" value="${cart.quantity}" name="quantity" ></td>

                            <td>${cart.roomID.price}</td>
                            <td>${cart.quantity * cart.roomID.price * sessionScope.numberDate}</td>
                            <td align="center">
                                <input type="submit" value="Update" >
                                <a href="listCartAction?action=remove&id=${cart.roomID.roomID}"
                                   onclick="return confirm('Are you sure to delete !!!')" >Remove</a>
                            </td>
                            </tr>
                        </form>
                    </c:forEach>
                    <tr>
                        <th colspan="1">Total cost</th>
                        <th colspan="2"><input type="number" readonly="" name="totalCost" value="${totalCost}"></th>
                    </tr>
                </table>
                <form action="requestEmailAction" method="GET">
                    <input type="submit" value="Pay">
                </form>

        </section>
        <footer align="right">
            <div class="container" style="background-color:#f1f1f1">
                </br>Hello ${inforUser.name}- <a href="loadListAction">View list hotel</a>

                <c:if test = "${sessionScope.inforUser.name != null}">
                    - <a href="logOutAction">Log out</a>   </br></br>
                </c:if>
            </div>
        </footer>
    </body>
    <script>
        var checkInDate = document.getElementById('checkIn');
        checkInDate.min = new Date().toISOString().split("T")[0];
        var checkOutDate = document.getElementById('checkOut');
        checkOutDate.min = new Date().toISOString().split("T")[0];
    </script>
</html>
