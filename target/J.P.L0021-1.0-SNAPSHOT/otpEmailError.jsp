<%-- 
    Document   : otpEmailError
    Created on : Oct 16, 2021, 9:32:24 AM
    Author     : TanNV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Input OTP from email to confirm your orders : </h2>
        <font color="red">${requestScope.error}</font>
        <form action="payAction" method="POST">
            <input type="number" placeholder="Enter otp" name="otp" min="1000" max="9999" step="1">
            <input type="submit" value="Confirm">
        </form>
    </body>
</html>
