<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html>
<%
    Date data = new Date();
    String year = Integer.toString(data.getYear() + 1900);
    String month = "";
    if (data.getMonth() < 8)
        month = "0" + Integer.toString(data.getMonth() + 2);
    else
        month = Integer.toString(data.getMonth() + 2);

    String min = year + "-" + month;
    String error = (String) request.getAttribute("errorPay");
%>
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
    <meta charset="ISO-8859-1">
    <title>Pagamento</title>
    <link rel="stylesheet" type="text/css" href="style/add.css">

</head>
<body>

<jsp:include page="header.jsp"/>

<div class=container-fluid id=add>
<%
    String totAsString = request.getParameter("tot");
    if(totAsString!=null){
        Integer tot = Integer.parseInt(totAsString);
%>
	<%if ( error != null ) { %>
        <div id="error">
            <p class="errTextInterior"><%=error%></p>
        </div>
	<% } %>

     
            <form name="payForm" method="post" action="<%=response.encodeURL("CarrelloController?action=buy")%>" 
    		  onsubmit="event.preventDefault(); validateForm(this)">
                
				<h3>Indirizzo di fatturazione e spedizione</h3>
				<label for="fName">Nome destinatario: </label>
    				<input id="fName" type="text" name="fName" placeholder="Vincenzo Loia" required><br>
    				<p id="errFName" class="errTextInterior"></p>
    			<label for="indirizzo">Indirizzo</label>
    				<input id="indirizzo" type="text" name="indirizzo" placeholder="" required><br>
    				<p id="errAddr" class="errTextInterior"></p>
    			<label for="city">Città</label>
    				<input id="city" type="text" name="city" placeholder="Fisciano" required><br>
    				<p id="errCity" class="errTextInterior"></p>
    			<label for="regione">Regione e Provincia</label>
					<select id="regione" name="regione" required>
					<option disabled selected value=>Seleziona una regione (e provincia)</option>
						<optgroup label="Piemonte">
							<option value="TO">Torino</option>
							<option value="VC">Vercelli</option>
							<option value="NO">Novara</option>
							<option value="CN">Cuneo</option>
							<option value="AT">Asti</option>
							<option value="AL">Alessandria</option>
							<option value="BI">Biella</option>
							<option value="VB">Verbano-Cusio-Ossola</option>
						</optgroup>
						<optgroup label="Valle d'Aosta">
							<option value="AO">Valle d'Aosta/Vallèe d'Aoste</option>
						</optgroup>
						<optgroup label="Lombardia">
							<option value="VA">Varese</option>
							<option value="CO">Como</option>
							<option value="SO">Sondrio</option>
							<option value="MI">Milano</option>
							<option value="BG">Bergamo</option>
							<option value="BS">Brescia</option>
							<option value="PV">Pavia</option>
							<option value="CR">Cremona</option>
							<option value="MN">Mantova</option>
							<option value="LC">Lecco</option>
							<option value="LO">Lodi</option>
							<option value="MB">Monza e della Brianza</option>
						</optgroup>
						<optgroup label="Trentino-Alto Adige">
							<option value="BZ">Bolzano/Bozen</option>
							<option value="TN">Trento</option>
						</optgroup>
						<optgroup label="Veneto">
							<option value="VR">Verona</option>
							<option value="VI">Vicenza</option>
							<option value="BL">Belluno</option>
							<option value="TV">Treviso</option>
							<option value="VE">Venezia</option>
							<option value="PD">Padova</option>
							<option value="RO">Rovigo</option>
						</optgroup>
						<optgroup label="Friuli-Venezia Giulia">
							<option value="UD">Udine</option>
							<option value="GO">Gorizia</option>
							<option value="TS">Trieste</option>
							<option value="PN">Pordenone</option>
						</optgroup>
						<optgroup label="Liguria">
							<option value="IM">Imperia</option>
							<option value="SV">Savona</option>
							<option value="GE">Genova</option>
							<option value="SP">La Spezia</option>
						</optgroup>
						<optgroup label="Emilia-Romagna">
							<option value="PC">Piacenza</option>
							<option value="PR">Parma</option>
							<option value="RE">Reggio nell'Emilia</option>
							<option value="MO">Modena</option>
							<option value="BO">Bologna</option>
							<option value="FE">Ferrara</option>
							<option value="RA">Ravenna</option>
							<option value="FC">Forlì-Cesena</option>
							<option value="RN">Rimini</option>
						</optgroup>
						<optgroup label="Toscana">
							<option value="MS">Massa-Carrara</option>
							<option value="LU">Lucca</option>
							<option value="PT">Pistoia</option>
							<option value="FI">Firenze</option>
							<option value="LI">Livorno</option>
							<option value="PI">Pisa</option>
							<option value="AR">Arezzo</option>
							<option value="SI">Siena</option>
							<option value="GR">Grosseto</option>
							<option value="PO">Prato</option>
						</optgroup>
						<optgroup label="Umbria">
							<option value="PG">Perugia</option>
							<option value="TR">Terni</option>
						</optgroup>
						<optgroup label="Marche">
							<option value="PU">Pesaro e Urbino</option>
							<option value="AN">Ancona</option>
							<option value="MC">Macerata</option>
							<option value="AP">Ascoli Piceno</option>
							<option value="FM">Fermo</option>
						</optgroup>
						<optgroup label="Lazio">
							<option value="VT">Viterbo</option>
							<option value="RI">Rieti</option>
							<option value="RM">Roma</option>
							<option value="LT">Latina</option>
							<option value="FR">Frosinone</option>
						</optgroup>
						<optgroup label="Abruzzo">
							<option value="AQ">L'Aquila</option>
							<option value="TE">Teramo</option>
							<option value="PE">Pescara</option>
							<option value="CH">Chieti</option>
						</optgroup>
						<optgroup label="Molise">
							<option value="CB">Campobasso</option>
							<option value="IS">Isernia</option>
						</optgroup>
						<optgroup label="Campania">
							<option value="CE">Caserta</option>
							<option value="BN">Benevento</option>
							<option value="NA">Napoli</option>
							<option value="AV">Avellino</option>
							<option value="SA">Salerno</option>
						</optgroup>
						<optgroup label="Puglia">
							<option value="FG">Foggia</option>
							<option value="BA">Bari</option>
							<option value="TA">Taranto</option>
							<option value="BR">Brindisi</option>
							<option value="LE">Lecce</option>
						<option value="BT">Barletta-Andria-Trani</option>
									</optgroup>
						<optgroup label="Basilicata">
							<option value="PZ">Potenza</option>
							<option value="MT">Matera</option>
						</optgroup>
						<optgroup label="Calabria">
							<option value="CS">Cosenza</option>
							<option value="CZ">Catanzaro</option>
							<option value="RC">Reggio di Calabria</option>
							<option value="KR">Crotone</option>
							<option value="VV">Vibo Valentia</option>
						</optgroup>
						<optgroup label="Sicilia">
							<option value="TP">Trapani</option>
							<option value="PA">Palermo</option>
							<option value="ME">Messina</option>
							<option value="AG">Agrigento</option>
							<option value="CL">Caltanissetta</option>
							<option value="EN">Enna</option>
							<option value="CT">Catania</option>
							<option value="RG">Ragusa</option>
							<option value="SR">Siracusa</option>
						</optgroup>
						<optgroup label="Sardegna">
							<option value="SS">Sassari</option>
							<option value="NU">Nuoro</option>
							<option value="CA">Cagliari</option>
							<option value="OR">Oristano</option>
							<option value="OT">Olbia-Tempio</option>
							<option value="OG">Ogliastra</option>
							<option value="VS">Medio Campidano</option>
							<option value="CI">Carbonia-Iglesias</option>
						</optgroup>
					</select><br>
				<label for="cap">CAP</label>
					<input id="cap" type="text" name="cap" placeholder="84084" required>
					<p id="errCAP" class="errTextInterior"></p>
				<h3>Informazioni per il pagamento</h3><br>
				<label for="cName">Titolare della carta</label>
					<input id="cName" type="text" name="cName" placeholder="Vincenzo Loia" required><br>
					<p id="errCName" class="errTextInterior"></p>
				<label for="card">Numero carta</label>
					<input id="card" type="text" name="card" placeholder="5333-1234-1234-1234" required><br>
					<p id="errCard" class="errTextInterior"></p>
				<label for="scad">Anno e mese di scadenza</label>
					<input id="scad" type="month" min="<%=min%>" id="expyear" name="expyear"><br>
					<p id="errCardDate" class="errTextInterior"></p>
				<label for="cvv">CVV</label>
					<input id="cvv" type="number" name="cvv" placeholder="352" required><br>
					<p id="errCVV" class="errTextInterior"></p>	
                 <div class=pay>
                    <p style=float:left>Totale da pagare:</p> 
                    <p style=font-weight:bold style=float:right;><%=tot%>$</p>
                </div>
                
                <input hidden name="action" value="buy">
                <button style="margin-bottom: 30px" type="submit" value="Conferma pagamento">Conferma pagamento</button>
                
               </form>
                
               
        <%}
	else {%>
    <p>Non c'è nulla da pagare!</p>
        <%}%>
        </div>
<script>
	function validateName(nameTxt) {
    	const nameRegEx = /[A-Za-z ]+$/;
    	
    	return nameRegEx.test(nameTxt);
    }
    
    function validateCC(ccTxt) {
    	const creditCardRegEx = /^(?:4[0-9]{12}(?:[0-9]{3})?|[25][1-7][0-9]{14}|6(?:011|5[0-9][0-9])[0-9]{12}|3[47][0-9]{13}|3(?:0[0-5]|[68][0-9])[0-9]{11}|(?:2131|1800|35\d{3})\d{11})$/;
    	
    	return creditCardRegEx.test(ccTxt);
    }
    
    function validateAddress(addressTxt) {
    	const addressRegEx = /^(?:[A-Za-z]+)(?:[A-Za-z0-9 _]*)$/;
    	
    	return addressRegEx.test(addressTxt);
    }
    
    function validateCAP(capTxt) {
    	const CAPregEx = /^\d{5}$/;
    	
    	return CAPregEx.test(capTxt);
    }
    
    function validateCVV(cvvTxt) {
    	const cvvRegEx = /^[0-9]{3}$/;
    	
    	return cvvRegEx.test(cvvTxt);
    }
    
    function validateForm(obj) {
    	var valid = true;
    	
    	var nameSped = document.forms["payForm"]["fName"].value;
    	var address = document.forms["payForm"]["indirizzo"].value;
		var city = document.forms["payForm"]["city"].value;	 
    	var cap = document.forms["payForm"]["cap"].value;
    	var nameCard = document.forms["payForm"]["cName"].value;	 
    	var creditCard = document.forms["payForm"]["card"].value;
    	var cvv = document.forms["payForm"]["cvv"].value;
    	
    	var nameSpedErr =  document.forms["payForm"]["fName"];
		if ( !( validateName(nameSped) ) ) {
			valid = false;
			nameSpedErr.classList.add("error");
			document.getElementById('errFName').innerHTML="Destinatario non valido (Solo lettere, spazio ammesso)";
		} else {
			nameSpedErr.classList.remove("error");
			document.getElementById('errFName').innerHTML="";
		}
		
		var addressErr =  document.forms["payForm"]["indirizzo"];
		if ( !( validateAddress(address) ) ) {
			valid = false;
			addressErr.classList.add("error");
			document.getElementById('errAddr').innerHTML="Indirizzo non valido (Solo lettere e numero civico, spazio ammesso)";
		} else {
			addressErr.classList.remove("error");
			document.getElementById('errAddr').innerHTML="";
		}
		
		var cityErr =  document.forms["payForm"]["city"];
		if ( !( validateName(city) ) ) {
			valid = false;
			cityErr.classList.add("error");
			document.getElementById('errCity').innerHTML="Città non valida (Solo lettere, spazio ammesso)";
		} else {
			cityErr.classList.remove("error");
			document.getElementById('errCity').innerHTML="";
		}
		
		var capErr =  document.forms["payForm"]["cap"];
		if ( !( validateCAP(cap) ) ) {
			valid = false;
			capErr.classList.add("error");
			document.getElementById('errCAP').innerHTML="CAP non valido (Solo cinque numeri)";
		} else {
			capErr.classList.remove("error");
			document.getElementById('errCAP').innerHTML="";
		}
		
		var nameCardErr =  document.forms["payForm"]["cName"];
		if ( !( validateName(nameCard) ) ) {
			valid = false;
			nameCardErr.classList.add("error");
			document.getElementById('errCName').innerHTML="Intestatario carta non valido (Solo lettere, spazio ammesso)";
		} else {
			nameCardErr.classList.remove("error");
			document.getElementById('errCName').innerHTML="";
		}
		
		var creditCardErr =  document.forms["payForm"]["card"];
		if ( !( validateCC(creditCard) ) ) {
			valid = false;
			creditCardErr.classList.add("error");
			document.getElementById('errCard').innerHTML="Intestatario carta non valido (Utilizzare un numero di carta di credito valida)";
		} else {
			creditCardErr.classList.remove("error");
			document.getElementById('errCard').innerHTML="";
		}
		
		var cvvErr =  document.forms["payForm"]["cvv"];
		if ( !( validateCVV(cvv) ) ) {
			valid = false;
			cvvErr.classList.add("error");
			document.getElementById('errCVV').innerHTML="CVV non valido (Solo tre numeri, sono dietro la carta di credito)";
		} else {
			cvvErr.classList.remove("error");
			document.getElementById('errCVV').innerHTML="";
		}
		
		if (valid) obj.submit();
	}
</script>
</body>
</html>