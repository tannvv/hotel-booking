<%-- 
    Document   : listCartNull
    Created on : Oct 16, 2021, 10:17:41 PM
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
        <h2 align="center">List search</h2>
         <font color="red">${error}</font></br>
        <footer align="right">
            <div class="container" style="background-color:#f1f1f1">
                </br>Hello ${inforUser.name}- <a href="loadListAction">View list hotel</a>

                <c:if test = "${sessionScope.inforUser.name != null}">
                    - <a href="logOutAction">Log out</a>   </br></br>
                </c:if>
            </div>
        </footer>
    </body>
</html>
