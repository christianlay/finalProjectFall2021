<%-- 
    Document   : adminPage
    Created on : Nov. 25, 2021, 8:12:10 p.m.
    Author     : Administrator
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
        <link href="style.css" rel="stylesheet" type="text/css">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Tinos:wght@700&display=swap" rel="stylesheet">
    </head>
    <body>
        <h1>Administration Page</h1>
        Welcome, ${sessionScope.username}<br>
        <a href="AdminServices?forward=logout">Logout</a>
        <form action="AdminServices" method="POST">
            <h2>User Accounts</h2>
                     
            <table border="1" cellspacing="0" cellpadding="4">
                <tr>
                    <th>Username</th>
                    <th>User Type</th>
                    <th>Delete</th>
                    <th>Lock/Unlock</th>
                </tr>
            
                <c:forTokens var="user" delims=";" items="${requestScope.userList}">
                    <tr>
                        <td>${fn:substring(user,0,fn:indexOf(user,","))}</td>
                        <td><a href="AdminServices?toggleUserType=${fn:substring(user,fn:indexOf(user,",")+1,fn:indexOf(user,"."))}&user=${fn:substring(user,0,fn:indexOf(user,","))}">${fn:substring(user,fn:indexOf(user,"!")+1,fn:indexOf(user,"@"))}</a></td>
                        <td><u onclick="confirmDelete('${fn:substring(user,0,fn:indexOf(user,","))}')">Delete</u></td>
                        <td><a href="AdminServices?toggleLocked=${fn:substring(user,fn:indexOf(user,".")+1,fn:indexOf(user,"!"))}&user=${fn:substring(user,0,fn:indexOf(user,","))}">${fn:substring(user,fn:indexOf(user,"@")+1,100)}</a></td>
                        
                    </tr>
                </c:forTokens>
                
            
            </table>
            <br>
            ${requestScope.message}
        </form>
        
        <script>
            function confirmDelete(deleteUser){
                if(confirm("Delete account?")){
                    location.replace("AdminServices?delete=" + deleteUser);
                }
            }
        </script>
    </body>
</html>
