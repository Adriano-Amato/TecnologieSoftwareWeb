<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="com.classes.model.bean.users.User" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.classes.model.bean.staff.StaffBean" %>
<%@ page import="com.classes.model.bean.entity.SquadraBean" %>
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
    
    Collection<StaffBean> staff = (Collection<StaffBean>) request.getAttribute("staff");
    if (staff == null) {
        request.setAttribute("destination", "/addStaff.jsp");
        request.getRequestDispatcher("StaffController").forward(request, response);
        return;
    }

    Collection<SquadraBean> squadreView = (Collection<SquadraBean>) request.getAttribute("collezioneSquadra");
    if (squadreView == null) {
        request.getRequestDispatcher("SquadraController").forward(request, response);
        return;
    }

    String error = (String) request.getAttribute("errorStaff");
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
    <title>Aggiungi Staff</title>
     <link rel="stylesheet" type="text/css" href="style/add.css">
    <script src="libraries/jquery.js"></script>

</head>
<body>

<jsp:include page="header.jsp"/>

<h1>Aggiungi staff</h1>
<div class=container-fluid id=add>
	<%if ( error != null ) { %>
        <div id="fadeError">
            <p class="errTextInterior"><%=error%></p>
        </div>
	<% } %>
	
	<form name="addStaff" method="post" action="<%=response.encodeURL("StaffManagementController?action=add")%>" onsubmit="event.preventDefault(); validateForm(this)">
    	<label for="nameStaff">Nome</label>
    	<input type="text" name="nameStaff" id="nameStaff" placeholder="Mario" title="Può contenere solo lettere" required>
    	<p id="errName" class="errTextInterior"></p>
    	<label for="surnameStaff">Cognome</label>
    	<input type="text" name="surnameStaff" id="surnameStaff" placeholder="Rossi" title="Può contenere solo lettere" required><br>
    	<p id="errSurname" class="errTextInterior"></p>
    	<label for="codFiscStaff">Codice Fiscale</label>
    	<input type="text" name="codFiscStaff" id="codFiscStaff" title="Rispettare i canoni del Codice Fiscale Italiano" required><br>
    	<p id="errCodFisc" class="errTextInterior"></p>
    	<label for="ruolo">Ruolo</label>
    	<select id="ruolo" name="ruolo" required>
        	<option disabled selected value=>Seleziona un ruolo</option>
        	<option value="Allenatore">Allenatore</option>
        	<option value="Preparatore">Preparatore</option>
        	<option value="Vice allenatore">Vice Allenatore</option>
        	<option value="Allenatore Portieri">Allenatore portieri</option>
    	</select><br>
    	<label for="squadDest">Squadra a cui si vuole assegnare</label>
    	<select id="squadDest" name="squadDest" style="width: 200px;" required>
        	<option disabled selected value=>Seleziona una squadra</option>
        	<%
            	if (squadreView != null && squadreView.size() > 0) {
                	for (SquadraBean s : squadreView) {
        	%>
        	<option value="<%=s.getName()%>"><%=s.getName()%>
        	</option>
        	<%
                	}
            	}
        	%>
    	</select><br>
    	<button type="submit">Conferma</button>
	</form>
	<script>
		function validateName(nameTxt) {
			const nameRegEx = /[A-Za-z]+$/;
			
			return nameTxt.match(nameRegEx);
		}
		
		function validateSurname(surnameTxt) {
			const nameRegEx = /[A-Za-z]+$/;
			
			return surnameTxt.match(nameRegEx);
		}
		
		function validateCodFisc(codFiscTxt) {
			const codFiscRegEx = /[A-Z]{6}\d{2}[A-Z]\d{2}[A-Z]\d{3}[A-Z]$/;
			
			return codFiscTxt.match(codFiscRegEx);
		}
	
		function validateForm(obj) {
			var nameStaff = $("#nameStaff").val();
			var surnameStaff = $("#surnameStaff").val();
			var codFiscStaff = $("#codFiscStaff").val();
			
			var valid = true;
			
			var nomeErr = document.getElementsByName("nameStaff")[0];
			if ( !validateName(nameStaff) ) {
				valid = false;
				nomeErr.classList.add("error");
				document.getElementById('errName').innerHTML="Nome non valido (Solo lettere)";
			} else {
				nomeErr.classList.remove("error");
				document.getElementById('errName').innerHTML="";
			}
			
			var surnameErr = document.getElementsByName("surnameStaff")[0];
			if ( !validateSurname(surnameStaff) ) {
				valid = false;
				surnameErr.classList.add("error");
				document.getElementById('errSurname').innerHTML="Cognome non valido (Solo lettere)";
			} else {
				surnameErr.classList.remove("error");
				document.getElementById('errSurname').innerHTML="";
			}
			
			var codFiscErr = document.getElementsByName("codFiscStaff")[0];
			if ( !validateCodFisc(codFiscStaff) ) {
				valid = false;
				codFiscErr.classList.add("error");
				document.getElementById('errCodFisc').innerHTML="Codice Fiscale non valido";
			} else {
				codFiscErr.classList.remove("error");
				document.getElementById('errCodFisc').innerHTML="";
			}
			
	
			if (valid) obj.submit();
		}
	</script>
</div>
</body>
</html>