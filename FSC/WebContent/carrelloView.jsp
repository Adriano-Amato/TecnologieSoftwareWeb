<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.classes.model.Carrello" %>
<%@ page import="com.classes.model.bean.products.ProductBean" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Carrello cart = (Carrello) request.getSession().getAttribute("carrello");

    if (cart == null) {
        response.sendRedirect(response.encodeRedirectURL("CarrelloController"));
        return;
    }
%>
<html>
<script>
$('#elimina').click(function(){
    $("#realElimina").click();
})
</script>
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

    <title>Carrello</title>

    <link rel="shortcut icon" href="IMAGE/favico.png?" type="image/x-icon"/>
    <title>Carrello</title>
    <link rel="stylesheet" href="style/carrello.css">
</head>

<body>

<jsp:include page="header.jsp"/>

<%
    HashMap<ProductBean, Integer> prodcart = cart.getItems();

    if (prodcart.size() > 0) {
        int totale = 0;%>

	<div class=container-fluid>
     <div class=container-fluid id=fullcart>
                <%
                    for (Map.Entry<ProductBean, Integer> entry : prodcart.entrySet()) {
                %>
                <div class=product>
                    <h5><%=entry.getKey().getName()%>
                    </h5>
                    <p>Quantità: <%=entry.getValue()%><br>
                    <form id="clear" action="<%=response.encodeURL("CarrelloController?action=deleteCart&id=" + entry.getKey().getCode())%>" method="POST">
                        	<button id="elimina">Elimina dal carrello</button>
                        	<input id="realElimina" type="submit" style="display: none;">
					</form>
                </div>
                <%
                        totale += (entry.getKey().getPrice() * entry.getValue());
                    }
                    request.setAttribute("tot", totale);
                %>
            </div>
            <div class=container-fluid id=cassa>
                <div class="cassa">
                    <form id="clearCart" action="<%=response.encodeURL("CarrelloController")%>">
                        <input hidden name="action" value="clearCart">
                        <button id=cassa1 type="submit">Svuota carrello</button>
                    </form>
                    <form id="buyCart" action="<%=response.encodeURL("CarrelloController")%>" method="post">
                        <input hidden name="tot" value="<%=totale%>">
                        <input hidden name="action" value="pay">
                        <button id=cassa2 type="submit">Compra</button>
                    </form>
                    <p id=tot>Totale: <%=totale %>€</p>
                </div>
            </div>
       
        <div class="w-100"></div>


<% } else {
%>


<div class=container-fluid id=cart>
    <div class="emptycart">
        <p>Non ci sono elementi nel carrello</p>
        <img src=images/cart.png>
        <form action="<%=response.encodeURL("productView.jsp")%>">
            <button><span>Torna allo shop</span></button>
        </form>
    </div>
</div>

	 
</div>
<%
    }
%>


</body>
</html>
	
	
	
	
	
	

	
	
	
	
	
	
	
