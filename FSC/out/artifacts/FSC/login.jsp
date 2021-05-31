<%@ page import="com.classes.model.bean.users.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.classes.model.bean.users.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User) request.getSession().getAttribute("user");
    if (user != null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
    String destination = (String) request.getAttribute("destination");
    Boolean error = (Boolean) request.getAttribute("error");
    if (error == null) {
        error = false;
    }
    if (destination == null) {
        destination = "/index.jsp";
    }
%>
<html>
<head>
    <link rel="shortcut icon" href="images/favico.png" type="image/x-icon"/>
    <script src="libraries/jquery.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
            integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" type="text/css" href="style/log.css">
    <meta charset="ISO-8859-1">
    <title>Login</title>

</head>

<body>

<jsp:include page="header.jsp"/>
    
    <div class=container-fluid id =log>
    
        <form action="<%=response.encodeURL("LoginController")%>" method="post">
        	<%if (error == true) {%>
            	<p id="errorMessage" style="color: red;">Username o password errate, ritenta.</p>
            <%}%>
            <label>USERNAME</label>
            <input required type="text" name="username" placeholder="Insert Username">

            <label>PASSWORD</label>
            <input required type="password" name="password" placeholder="Insert Password">

            <input name="destination" value="<%=destination%>" style="visibility: hidden">
            <input name="action" value="login" style="visibility:hidden;">
            <button type="submit">Login</button>
        </form>
</div>
<div class=container-fluid id=page>
<h1 style=text-align:center>Vuoi iscriverti?</h1>
	<form method="post" action="<%=response.encodeURL(request.getContextPath() + "/signup.jsp")%>">
            <button type="submit">Registrati</button>
        </form>
       <div class="logo"><img src="images/Cattura.png"></div>
</div>

</body>
</html>