<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="com.classes.model.bean.products.ProductBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.classes.model.bean.users.User" %>
<!DOCTYPE html>
<%
    User user = (User) request.getSession().getAttribute("user");
    if (user == null) {
        request.setAttribute("destination", "/aOcProduct.jsp");
        request.setAttribute("error", Boolean.FALSE);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    } else if (!(User.isAdmin(user))) {
        response.sendError(response.SC_FORBIDDEN, "Non sei admin!");
    }

    Collection<ProductBean> prodotti = (Collection<ProductBean>) request.getAttribute("products");
    if(prodotti==null){
        request.setAttribute("destination", "/aOcProduct.jsp");
        request.getRequestDispatcher("ProductController").forward(request, response);
        return;
    }
    Iterator<?> iterator;
    
    String error = (String) request.getAttribute("errorAoC");
%>
<html>
<head>
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
    <title>Modifica/Aggiunta prodotti</title>
	<link rel="stylesheet" type="text/css" href="style/add.css">
</head>

<body>

<jsp:include page="header.jsp"/>

<div class=container-fluid id=add>
	<%if ( error != null ) { %>
	<div id="fadeError">
    	<p id="errorPlaceholder" style="color: red; display: none"></p>
	</div>
	<% } %>
	
	<form id="form" name="aOcProd" method="post" action="<%=response.encodeURL("ProductModifyController")%>" enctype="multipart/form-data" onsubmit="event.preventDefault(); validateForm(this)">
    	<div>
        	<% if (request.getParameter("operation").equalsIgnoreCase("Aggiungi Prodotto")) {%>
        	<label>Nome: </label>
        	<input id="nome" type="text" name="nome"><br>
        	<label>Descrizione: </label>
        	<input id="desc" type="text" name="desc"><br>
        	<label>Prezzo: </label>
        	<input type="number" min="0" value="0" step="1" name="prezzo"><br>
        	<label>Quantità: </label>
        	<input type="number" min="0" value="0" name="quantita"><br>
        	<input type="file" name="file" size="50" value="Immagine"><br>
        	<input class="button-like" type="submit" value="Conferma">
        	<% } else if (request.getParameter("operation").equalsIgnoreCase("Modifica Prodotto")) {
        	%>
        	<label>Prodotto da modificare: </label>
        	<select required id="selectProdotti" name="oldProd" style="width: 100px;">
        	    <option disabled selected value=>Seleziona un prodotto</option>
            	<%
                	iterator = prodotti.iterator();
                	while (iterator.hasNext()) {
                    	ProductBean bean1 = (ProductBean) iterator.next();
            	%>
            	<option class="optionClass" value="<%=bean1.getCode()%>"><%=bean1.getName()%> (ID: <%=bean1.getCode()%>)
            	</option>
            	<% } %>
        	</select><br>
        	<label>Nome: </label>
        	<input id="nome" type="text" name="nome"><br>
        	<label>Descrizione: </label>
        	<input id="desc" type="text" name="desc"><br>
        	<label>Prezzo: </label>
        	<input id="prezzo" type="number" min="0" value="0" step=".01" name="prezzo" name="prezzo"><br>
        	<label>Quantità: </label>
        	<input id="quantita" type="number" min="0" value="0" name="quantita"><br>
        	<label for="file">Nuova immagine: (Lascia vuoto per lasciare la vecchia)</label><br>
        	<input id="file" type="file" name="file" size="50" value="Immagine"><br>
        	<input class="button-like" type="submit" value="Conferma">
        	<% }%>
    	</div>
	</form>
</div>
<script>
    //riempe i campi del form alla selezione di un prodotto dal dropdown menu
    $("#selectProdotti").on("change", function () {
        let value = $("#selectProdotti option:selected").attr('value'); //id di cosa hai cliccato
        $.ajax({
            type: "GET",
            url: "ProductController",
            data: "id=" + value
        }).done(function (data) {
            data = JSON.parse(data);
            $("#nome").attr('value', data.nome);
            $("#desc").attr('value', data.descrizione);
            $("#quantita").attr('value', data.quantita);
            $("#prezzo").attr('value', data.prezzo);
        });
    });
</script>

<script>
	function validateName(nameTxt) {
		const nomeProdRegEx = /[A-Za-z]+$/;
		
		return nameTxt.match(nomeProdRegEx);
	}
	
	function validateDesc(descTxt) {
		const descRegEx = /[A-Za-z ]+$/;
		
		return descTxt.match(descRegEx);
	}
	
	function validateForm(obj) {
		var nomeProd = $("#nome").val();
		var descProd = $("#desc").val();
		
		var valid = true;
		
		var nomeErr = document.getElementsByName("nome")[0];
		if ( !validateName(nomeProd) ) {
			valid = false;
			nomeErr.classList.add("error");
		} else {
			nomeErr.classList.remove("error");
		}
		
		var descErr = document.getElementsByName("desc")[0];
		if ( !validateDesc(descProd) ) {
			valid = false;
			descErr.classList.add("error");
		} else {
			descErr.classList.remove("error");
		}
		
		if (valid) obj.submit();
	}
</script>
</body>
</html>