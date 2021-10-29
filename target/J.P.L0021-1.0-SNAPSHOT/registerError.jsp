<%-- 
    Document   : registerAccount
    Created on : Oct 1, 2021, 8:16:36 PM
    Author     : TanNV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        <h2>Register new account</h2>
        <form action="registerAction" method="POST">
            Email : <input type="text" placeholder="Enter email" name="email" value="${requestScope.newAcc.email}"> 
            <font color="red">${requestScope.error.email}</font>
            </br>

            Password : <input id="password" type="password" placeholder="Enter Password" name="password" >
            <font color="red">${requestScope.error.password}</font>
            </br>

            Re-password : <input id="password" type="password" placeholder="Enter Re-password" name="rePassword">
            <font color="red">${requestScope.error.rePassword}</font>
            </br>
            
            Name : <input type="text" placeholder="Enter name" name="name" value="${requestScope.newUser.name}"> 
            <font color="red">${requestScope.error.name}</font>
            </br>
            Phone : <input type="text" placeholder="Enter phone" name="phone" value="${requestScope.newUser.phone}"> 
            <font color="red">${requestScope.error.phone}</font>
            </br>
            Address : <input type="text" placeholder="Enter address" name="address" value="${requestScope.newUser.address}"> 
            <font color="red">${requestScope.error.address}</font>
            </br>

            <button type="submit">Register</button>
        </form>
        </br></br>
         <footer>
            <div class="container" style="background-color:#f1f1f1">
                <a href="loadListAction">View hotel list</a>
            </div>
        </footer>
    </body>
    <script>
        document.getElementById("password").value = "${requestScope.newAcc.password}";   
        document.getElementById("rePassword").value = "${requestScope.rePassword}";
    </script>
</html>
