<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.classes.model.bean.entity.SquadraBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.classes.model.bean.entity.SquadraBean" %>
<%
    Collection<SquadraBean> squadre = (Collection<SquadraBean>) request.getAttribute("collezioneSquadra");
    if (squadre == null) {
        request.setAttribute("destination", "/signup.jsp");
        request.getRequestDispatcher("SquadraController").forward(request, response);
        return;
    }
    
    String error = (String) request.getAttribute("errorSignup");
%>
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
    <title>Registrati</title>
    <link rel="stylesheet" type="text/css" href="style/signup.css">
    <script src="libraries/jquery.js"></script>

</head>
<body>

<jsp:include page="header.jsp"/>

<div class=container-fluid id=reg>
    <div class=credenziali>
    	<%if ( error != null ) { %>
        <div id="fadeError">
            <p class="errTextInterior"><%=error%></p>
        </div>
       	<% } %>

        <form name="signupForm" method="post" action="<%=response.encodeURL("SignupController")%>"
              onsubmit="event.preventDefault(); validateForm(this)">
            <label for="nome">NOME: </label>
            <input id="nome" type="text" name="nome" placeholder="Mario" title="Può contenere solo lettere"
                   required><br>
           	<p id="errName" class="errTextInterior"></p>
            <label for="cognome">COGNOME: </label>
            <input id="cognome" type="text" name="cognome" placeholder="Rossi" title="Può contenere solo lettere"
                   required><br>
           	<div class="errorText"><p id="errSurname" class="errTextInterior"></p></div>
            <label for="username">USERNAME: </label>
            <input id="username" type="text" name="username" placeholder="MarioBros2"
                   title="Può contenere solo numeri e lettere (niente caratteri speciali)" required><br>
			<p id="errUsername" class="errTextInterior"></p>
            <label for="password">PASSWORD: </label>
            <input id="password" type="password" name="password"
                   title="Deve contenere almeno 8 caratteri, di cui una lettera minuscola, una maiuscola, un numero e un carattere speciale"
                   required><br>
           <p id="errPassword" class="errTextInterior"></p>
            <input class="utenteRadioButtons" type="radio" id="utente" name="typeUser" value="utente" checked>
            <label for="utente">Utente</label><br>
            <input class="utenteRadioButtons" type="radio" id="iscritto" name="typeUser" value="iscritto">
            <label for="iscritto">Iscritto</label><br>

            <div id="soloPerIscritto" style="display: none">
                <label for="codFisc">Codice Fiscale: </label>
                <input type="text" id="codFisc" name="codFisc"
                       title="Rispettare i canoni del Codice Fiscale Italiano"><br>
                <p id="errCodFisc" class="errTextInterior"></p>
                <label for="squadra"> In quale squadra vuole giocare?</label>
                <select id="squadra" name="squadra" style="width: 100px;">
                    <%
                        Iterator iterator = squadre.iterator();
                        while (iterator.hasNext()) {
                            SquadraBean bean = (SquadraBean) iterator.next();
                    %>
                    <option value="<%=bean.getName()%>"><%=bean.getName()%> (Età: <%=bean.getAge_range()%>)</option>
                    <%} %>
                </select><br>
            </div>
            <div class=butt>
                <button type="submit">Conferma</button>
                <button type="reset">Pulisci</button>
            </div>
        </form>
    </div>
</div>
	<script>
            $(".utenteRadioButtons").on("click", () => {
                if ($("#iscritto").is(':checked')) {
                    iscrittoFlag = true;
                    $("#soloPerIscritto").show();
                    $("#codFisc").prop('required', true);
                } else {
                    iscrittoFlag = false;
                    $("#soloPerIscritto").hide();
                    $("#codFisc").prop('required', false);
                }
            });
	</script>
	<script>
		var iscrittoFlag = false;

		function validateName(nameTxt) {
			const nameRegEx = /[A-Za-z]+$/;
			
			return nameTxt.match(nameRegEx);
		}
            
		function validateUsername(usernameTxt) {
			const usernameRegEx = /(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$/;
			
			return usernameTxt.match(usernameRegEx);
		}
            
		function validatePassword(passwordTxt) {
			const passwordRegEx = /(?=^.{8,}$)(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[^A-Za-z0-9]).*$/;
			
			
			return passwordTxt.match(passwordRegEx);
		}
            
		function validateCodFisc(codFiscTxt) {
			const codFiscRegEx = /[A-Z]{6}\d{2}[A-Z]\d{2}[A-Z]\d{3}[A-Z]$/;
            	
			return codFiscTxt.match(codFiscRegEx);
		}
            
		function validateForm(obj) {
			var valid = true;
            	
			var name = $("#nome").val();
			var surname = $("#cognome").val();
			var username = $("#username").val();
			var password = $("#password").val();
                
			var nomeErr =  document.getElementsByName("nome")[0];
			if ( !( validateName(name) ) ) {
				valid = false;
				nomeErr.classList.add("error");
				document.getElementById('errName').innerHTML="Nome non valido (Solo lettere)";
			} else {
				nomeErr.classList.remove("error");
				document.getElementById('errName').innerHTML="";
			}
                
			var surnameErr =  document.getElementsByName("cognome")[0];
			if ( !( validateName(surname) ) ) {
				valid = false;
				surnameErr.classList.add("error");
				document.getElementById('errSurname').innerHTML="Cognome non valido (Solo lettere)";
			} else {
				surnameErr.classList.remove("error");
				document.getElementById('errSurname').innerHTML="";
			}
                
			var usernameErr =  document.getElementsByName("username")[0];
			if ( ! ( validateUsername(username) ) ) {
				valid = false;
				usernameErr.classList.add("error");
				document.getElementById('errUsername').innerHTML="Username non valido (Niente caratteri speciali, minimo 8 caratteri e massimo 20)";
			} else {
				usernameErr.classList.remove("error");
				document.getElementById('errUsername').innerHTML="";
			}
                
			var passwErr =  document.getElementsByName("password")[0];
			if ( !( validatePassword(password) ) ) {
				passwErr.classList.add("error");
				document.getElementById('errPassword').innerHTML="Password non valida (Minimo 8 caratteri, una minuscola, una maiuscola, un numero e un carattere speciale)";
				valid = false;
			} else {
				passwErr.classList.remove("error");
				document.getElementById('errPassword').innerHTML="";
			}

			if (iscrittoFlag) {
				var codFisc = $("#codFisc").val();
                    
				var codErr =  document.getElementsByName("codFisc")[0];
				if ( !( validateCodFisc(codFisc) ) ) {
					valid = false;
					codErr.classList.add("error");
					document.getElementById('errCodFisc').innerHTML="Codice Fiscale non valido";
				} else {
					codErr.classList.remove("error");
					document.getElementById('errCodFisc').innerHTML="";
				}
			}
		
			if (valid) obj.submit();
		}
	</script>
</body>
</html>