<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html>
<%
    //Usato per formare una stringa che rappresenta l'attuale combinazione anno-mese, da utilizzare
    //come parametro min dell'input type month nel form
    Date data = new Date();
    String year = Integer.toString(data.getYear() + 1900);
    String month = "";
    if (data.getMonth() < 8)
        month = "0" + Integer.toString(data.getMonth() + 2);
    else
        month = Integer.toString(data.getMonth() + 2);

    String min = year + "-" + month;
    /*------------------------------------*/

    /*-------Controllo del flusso---------*/
    String typeSub = request.getParameter("typeSub");
    String sub = request.getParameter("sub");
    if (typeSub == null || sub == null) {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ci sono errori nei parametri della richiesta!");
        return;
    }
    
    String error = (String) request.getAttribute("errorPayRetta");
    /*------------------------------------*/
%>
<head>

    <link rel="shortcut icon" href="images/favico.png" type="image/x-icon"/>
    <meta charset="ISO-8859-1">
    <title>Pagamento Retta</title>
    <link rel="stylesheet" type="text/css" href="style/add.css">

    <script src="libraries/jquery.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
            integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
            crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>

</head>
<body>

<jsp:include page="header.jsp"/>

<h1>Pagamento</h1>
<div class=container-fluid id=add>
	<%if ( error != null ) { %>
        <div id="error">
            <p class="errTextInterior"><%=error%></p>
        </div>
	<% } %>
    <form name="payRetta" method="post" action="<%=response.encodeURL("RettaPayController")%>"
          onsubmit="event.preventDefault(); validateForm(this)">

        <label for="cname">Titolare della carta</label>
        <input required type="text" id="cname" name="cname" placeholder="Vincenzo Loia"
               title="Può contenere solo lettere">
        <p id="errCName" class="errTextInterior"></p>
        <label for="ccnum">Numero della carta</label>
        <input required type="text" id="ccnum" name="ccnum" placeholder="1111-2222-3333-4444"
               title="Usare un numero valido di carta di credito">
        <p id="errCard" class="errTextInterior"></p>
        <label for="expyear">Anno e mese di scadenza</label>
        <input required type="month" min="<%=min%>" id="expyear"><br>            
                <label for="cvv">CVV</label>
                <input required type="number" placeholder="352" id="cvv" name="cvv">
                <p id="errCVV" class="errTextInterior"></p>
                <input type="hidden" value="<%=typeSub%>" name="typeSub">
                <input type="hidden" value="<%=sub%>" name="sub">
           
      
        <button id=test type="submit">Conferma</button>
    </form>
    <script>
        function validateCVV(cvvTxt) {
            const cvvRegEx = /^[0-9]{3}$/;

            return cvvRegEx.test(cvvTxt);
        }

        function validateName(nameTxt) {
            const nameRegEx = /[A-Za-z ]+$/;

            return nameRegEx.test(nameTxt);
        }

        function validateCC(ccTxt) {
            const creditCardRegEx = /^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\d{3})\d{11})$/;

            return creditCardRegEx.test(ccTxt);
        }

        function validateForm(obj) {
            var valid = true;

            var name = document.forms["payRetta"]["cname"].value;
            var creditCard = document.forms["payRetta"]["ccnum"].value;
            var cvv = document.forms["payRetta"]["cvv"].value;

            var nameErr = document.forms["payRetta"]["cname"];
            if (!(validateName(name))) {
                valid = false;
                nameErr.classList.add("error");
                document.getElementById('errCName').innerHTML = "Destinatario non valido (Solo lettere, spazio ammesso)";
            } else {
                nameErr.classList.remove("error");
                document.getElementById('errCName').innerHTML = "";
            }


            var creditCardErr = document.forms["payRetta"]["ccnum"];
            if (!(validateCC(creditCard))) {
                valid = false;
                creditCardErr.classList.add("error");
                document.getElementById('errCard').innerHTML = "Intestatario carta non valido (Utilizzare un numero di carta di credito valida)";
            } else {
                creditCardErr.classList.remove("error");
                document.getElementById('errCard').innerHTML = "";
            }

            var cvvErr = document.forms["payRetta"]["cvv"];
            if (!(validateCVV(cvv))) {
                valid = false;
                cvvErr.classList.add("error");
                document.getElementById('errCVV').innerHTML = "CVV non valido (Solo tre numeri, sono dietro la carta di credito)";
            } else {
                cvvErr.classList.remove("error");
                document.getElementById('errCVV').innerHTML = "";
            }

            if (valid) obj.submit();
        }
    </script>
</div>
</body>
</html>