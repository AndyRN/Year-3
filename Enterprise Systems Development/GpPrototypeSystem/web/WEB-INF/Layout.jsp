<%-- 
    Document   : Layout
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
                    <a href="<%=application.getContextPath()%>/Front?direction=Account&action=Logout" class="pull-right">
                        <button type="button" class="btn btn-warning">Logout</button></a>
                </div>
            <% }%>
            <br>
            <br>
            <br>
        </div>
        <jsp:include page="${page}" flush="true" />
    </body>
</html>
