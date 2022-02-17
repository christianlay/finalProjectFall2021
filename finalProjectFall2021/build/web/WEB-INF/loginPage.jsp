<%-- 
    Document   : loginPage
    Created on : Nov. 25, 2021, 12:38:25 p.m.
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" href="style.css" type="text/css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Tinos:wght@700&display=swap" rel="stylesheet">
    </head>
    <body>
        <h1>Order Application</h1>
        <form action="UserServices" method="POST">
            Username: <input type="text" name="username"><br>
            Password: <input type="password" name="password"><br>
            <input type="submit" value="Login"><br>
            ${requestScope.message}<br>
            <a href="UserServices?forward=register">Register New Account</a>
        </form>
    </body>
</html>
