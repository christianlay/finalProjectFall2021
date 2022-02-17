<%-- 
    Document   : mainPage
    Created on : Nov. 25, 2021, 7:12:42 p.m.
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main Page</title>
        <link href="style.css" rel="stylesheet" type="text/css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Tinos:wght@700&display=swap" rel="stylesheet">
    </head>
    <body>
        <h1>Main Page</h1>
        Welcome, ${sessionScope.username}<br>
        <a href="UserServices?forward=logout">Logout</a><br>
        <a href="UserServices?forward=changeUsername">Change username</a><br>
        ${requestScope.changedmessage}
        
        
        <form action="UserServices" method="POST" style="display:${requestScope.displayChange}">
        <h2>Change Username</h2>
        Enter new username: <input type="text" name="newUsername"><br>
        Confirm new username: <input type="text" name="confirmNewUsername"><br>
        <input type="submit" value="Change Username"><br>
        ${requestScope.message}
        </form>
        
   
        
       
    </body>
</html>
