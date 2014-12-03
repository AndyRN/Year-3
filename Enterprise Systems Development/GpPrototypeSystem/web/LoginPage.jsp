<%-- 
    Document   : LoginPage
    Created on : 20-Nov-2014, 16:58:21
    Author     : Andy
--%>

<%@page import="Helpers.SessionHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="x" %>
<!DOCTYPE html>
<html>
    <head>
        <title>GP Consultation Program</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css">
    </head>
    <body>
        <div class="row">
            <div class="col-md-8 col-md-offset-1">                    
                <h1>GP Consultation System</h1>
            </div>
            <% if (SessionHelper.isLoggedIn(request, "doctor")) {%>
            <div class="col-md-2" style="padding-top: 23px">
                <a href="<%=application.getContextPath()%>/Account?action=Logout" class="pull-right">
                    <button type="button" class="btn btn-warning">Logout</button></a>
            </div>
            <% }%>
            <br>
            <br>
            <br>
        </div>
        <div class="container" style="margin-top: 20px;">
            <div class="row">
                <div class="col-md-6 col-md-offset-3">       
                    <h1>Login</h1>
                </div>
            </div>

            <div class="row">
                <div class="col-md-6 col-md-offset-3">
                    <form method="post" action="<%=application.getContextPath()%>/Login?action=Login">
                        <div class="form-group">
                            <label for="username" class="form-label">Username</label>
                            <input type="text" name="username" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" name="password" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <input type="submit" value="Login" class="btn btn-success" />
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
