<%-- 
    Document   : registerPage
    Created on : Nov. 25, 2021, 12:55:37 p.m.
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
        <link href="style.css" rel="stylesheet" type="text/css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Tinos:wght@700&display=swap" rel="stylesheet">
    </head>
    <body>
        <h1>Register New Account</h1>
        <form action="UserServices" method="POST">
            Username:<input type="text" name="registerUsername"><br>
            Password:<input type="password" name="registerPassword"><br>
            Confirm password: <input type="password" name="confirmPassword"><br>
            <input type="submit" value="Register"><br>
            ${requestScope.message}<br>
            <a href="UserServices?forward=login">Login</a>
        </form>
    </body>
</html>
