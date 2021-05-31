<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="com.classes.model.bean.users.User" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.classes.model.bean.users.IscrittoUserBean" %>
<%
    User user = (User) request.getSession().getAttribute("user");
    if (user == null) {
        request.setAttribute("destination", "/adminPage.jsp");
        request.setAttribute("error", Boolean.FALSE);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    } else if (!(User.isAdmin(user))) {
        response.sendError(response.SC_FORBIDDEN, "Non sei admin!");
    }

    Collection<IscrittoUserBean> iscritti = (Collection<IscrittoUserBean>) request.getAttribute("iscritti");
    if (iscritti==null){
        request.setAttribute("destination", "/changeStats.jsp");
        request.getRequestDispatcher("IscrittoController").forward(request, response);
        return;
    }
%>
<!DOCTYPE html>
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

    <link rel="shortcut icon" href="images/favico.png" type="image/x-icon"/>
    <meta charset="ISO-8859-1">
    <title>Cambia statistiche per Iscritti</title>
    <link rel="stylesheet" type="text/css" href="style/add.css">

</head>

<body>

<jsp:include page="header.jsp"/>

<h1>Cambia statistiche iscritti</h1>
<div class=container-fluid id=add>
	<form name="changeStat" method="post" action="<%=response.encodeURL("ChangeStatsController")%>" onsubmit="return validateForm()">
    	<label> Di quale iscritto vuoi cambiare le statistiche?</label>
    	<select id="iscritto" name="iscritto" required style=width:35%>
        	<option disabled selected value=>Seleziona un Iscritto</option>
        	<%
            	Iterator iterator = iscritti.iterator();
            	while (iterator.hasNext()) {
                	IscrittoUserBean bean = (IscrittoUserBean) iterator.next();
        	%>
        	<option value="<%=bean.getUsername()%>"><%=bean.getNome()%> <%=bean.getCognome()%>
        	</option>
        	<%} %>
    	</select><br>
    	<label for="goals">Inserire numero di gol da aggiungere</label>
    	<input id="goals" type="number" name="goals" min="0" value="0" required>
    	<label for="assists">Inserire numero di assist da aggiungere</label>
    	<input id="assists" type="number" name="assists" min="0" value="0" required>
    	<label for="minutes">Inserire numero di minuti da aggiungere</label>
    	<input id="minutes" type="number" name="minutes" min="0" value="0" required>
    	<button type="submit">Conferma</button>
	</form>
</div>
</body>
</html>