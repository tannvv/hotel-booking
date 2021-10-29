<%-- 
    Document   : indexUser
    Created on : Sep 11, 2021, 9:39:09 PM
    Author     : TanNV
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style type="text/css">
            header{
                height: 120px;
                align-items: center;
            }
            section{
                padding-left: 5%;
                padding-bottom: 40px;
                padding-top: 10px;
                clear: both;
                height: 100vh;
            }
            .img{
                text-align: center;
                border : 1px solid green;
                padding: 5px;
                margin: 5px;
                height: 350px;
                width: 200px;
                float: left;
            }
            footer{
                padding-top: 50px;
                clear:both;
                height: 120px;
                align-items: center;
            }
            #watch{
                padding-bottom: 10px;
                padding-top: 10px;
            }
            .header{
                padding-left: 13%;
                padding-bottom: 30px;
            }
            h2{
                padding-left: 40%;
                padding-bottom: 30px;
            }
            .search{
                padding-left: 7%;
                padding-bottom: 20px;
            }
            font{
                padding-left: 70px;
                font-size: 20px;
            }
            #main{
                display: block;
          
            }
        </style>
    </head>
    <body>
        <a href="loginPage">Login</a><>
                <a href="registerPage">Register</a></br>

        <h2>Booking hotel</h2>
        <form action="searchHotelNameAction" method="POST">
            <label class="search">Search hotel name : </label>
            Hotel name : <input type="text" name="hotelName" value="${hotelName}">
            <input type="submit" value="Search">
        </form>
        <form action="searchNumberRoomAction" method="POST">
             <input type="hidden" name="action" value="searchNumberOfRoom">
            <label class="search">Search amount of room and date check in check out : </label></br>
            <font>       </font>Check in : <input id="checkIn" type="date" name="checkIn" value="${requestScope.checkInData}" required=""> 
            Check out : <input id="checkOut" type="date" name="checkOut" value="${requestScope.checkOutData}" required="">
            Number of room : <input type="number" name="numberRoom" value="${requestScope.numberRoom}" min="1" step="1" required="">
            <input type="submit" value="Search">
        </form>
        <font color="red">${error}</font>
        <font color="green">${notifi}</font>
        <section id="main">
            <c:forEach items="${listHotel}" var="hotel">
                <div class="img">
                    ${hotel.hotelName}</br>
                    <img id="watch" src="images/${hotel.image}" width="120" height="150"></br>
                    Address : ${hotel.address}</br>
                    <a href="detailHotelAction?hotelID=${hotel.hotelID}">See rooms to book</a>
                </div>
            </c:forEach>
        </section>
             <c:forEach begin="1" end="${numberPage}" var="i">
                <a href="loadListAction?page=${i}">${i}</a>
            </c:forEach>
        </br></br></br>

        <footer>
            <div class="container" style="background-color:#f1f1f1">
                </br><a href="loadListAction">View list product</a> - <a href="listCartAction?action=view">View shopping cart</a> 
                 </br></br>
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

