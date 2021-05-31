<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="com.classes.model.bean.users.User" %>
<%
    User user = (User) request.getSession().getAttribute("user");
    if (user == null) {
        request.setAttribute("destination", "/adminPage.jsp");
        request.setAttribute("error", Boolean.FALSE);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    } else if (!(User.isAdmin(user))) {
        response.sendError(response.SC_FORBIDDEN, "Non sei admin!");
        return;
    }
    String error = (String) request.getAttribute("errorSquad");
%>
<!DOCTYPE html>
<html>
<head>
	<style>
		.error {
			box-shadow: 0 0 3px #CC0000; margin: 10px
		}
		
		.errTextInterior  {
				color: red;
				text-align: center;
		}
	</style>

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
            
            
    <meta charset="ISO-8859-1">
    <title>Aggiungi Squadra</title>
    <link rel="stylesheet" type="text/css" href="style/add.css">
    <script src="libraries/jquery.js"></script>

</head>
<body>

<jsp:include page="header.jsp"/>

<h1>Aggiungi squadra</h1>
<div class=container-fluid id=add>
	<%if ( error != null ) { %>
        <div id="error">
            <p class="errTextInterior"><%=error%></p>
        </div>
	<% } %>
	
	<form name="addSquad" method="post" action="<%=response.encodeURL("AddSquadraController")%>" onsubmit="event.preventDefault(); validateForm(this)">
    	<label for="squadName">Nome Squadra</label>
    	<input type="text" id="squadName" name="squadName" required><br>
    	<p id="errName" class="errTextInterior"></p>
    	<label for="category">Categoria</label>
    	<input type="text" id="category" name="category" required><br>
    	<p id="errCategory" class="errTextInterior"></p>
    	<label for="age">Età</label>
    	<input type="number" id="age" name="age" min="3" max="17" required><br>
    	<p id="errEta" class="errTextInterior"></p>
    	<button type="submit">Conferma</button>
	</form>
	<script>
	function validateName(nameTxt) {
		const squadRegEx = /[A-Za-z ]+$/;

		return nameTxt.match(squadRegEx);
	}
	
	function validateCategory(categoryTxt) {
		const categoryRegEx = /[A-Za-z ]+$/;
		
		return categoryTxt.match(categoryRegEx);
	}
	
	function validateAge(ageTxt) {
		const ageRegEx = /^[0-9]+$/;

		return ageTxt.match(ageRegEx);
	}
	
	
    function validateForm(obj) {
    	var nameSquad = $("#squadName").val();
    	var category = $("#category").val();
    	var age = $("#age").val();
    	
    	var valid = true;

    	var nameErr = document.getElementsByName("squadName")[0];
    	if ( !validateName(nameSquad) ) {
    		valid = false;
    		nameErr.classList.add("error");
			document.getElementById('errName').innerHTML="Nome non valido (Solo lettere, spazio accettato)";
    	} else {
    		nameErr.classList.remove("error");
    		document.getElementById('errName').innerHTML="";
    	}
    	
    	var categoryErr = document.getElementsByName("category")[0];
    	if ( !validateCategory(category) ) {
    		valid = false;
    		categoryErr.classList.add("error");
    		document.getElementById('errCategory').innerHTML="Nome categoria non valido (Solo lettere, spazio accettato)";
    	} else {
    		categoryErr.classList.remove("error");
    		document.getElementById('errCategory').innerHTML="";
    	}
    	
    	var ageErr = document.getElementsByName("age")[0];
    	if ( !validateAge(age) ) {
    		valid = false;
    		ageErr.classList.add("error");
    		document.getElementById('errEta').innerHTML="Età compresa fra 3 e 17 anni (numeri non negativi)";
    	} else {
    		ageErr.classList.remove("error");
    		document.getElementById('errEta').innerHTML="";
    	}
    	
    	if (valid) obj.submit();
    }
	</script>
</div>
</body>
</html>