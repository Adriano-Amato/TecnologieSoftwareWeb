<%@ page import="com.classes.model.bean.products.ProductBean" %>
<%@ page import="com.classes.model.bean.users.User " %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.classes.model.bean.users.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.classes.model.dao.SquadraModel" %>
<%@ page import="com.classes.model.bean.entity.SquadraBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%
    Collection<?> products = (Collection<?>) request.getAttribute("products");
    String error = (String) request.getAttribute("error");
    if (products == null && error == null) {
        /*vuol dire che fin'ora non siamo MAI passati per la Servlet*/
        response.sendRedirect(response.encodeRedirectURL("ProductController"));
    }

    String label;
    String destination;
    User user = (User) request.getSession().getAttribute("user");
    if (user == null) {
        label = "Login";
        destination = request.getContextPath() + "/login.jsp";
    } else if (User.isAdmin(user)) {
        label = "Admin";
        destination = request.getContextPath() + "/adminPage.jsp";
    } else {
        label = "Area personale";
        destination = request.getContextPath() + "/personalPage.jsp";
    }
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
    <title>Catalogo Prodotti</title>
    <link rel="stylesheet" type="text/css" href="style/catalogo.css">

</head>
<body>

<jsp:include page="header.jsp"/>

<div class=container-fluid id=products>

    <div class=row>
        <div class="card-deck">

            <% if (products != null && products.size() > 0) {
                int i = 1;
                Iterator iterator = products.iterator();
                while (iterator.hasNext()) {
                    ProductBean bean = (ProductBean) iterator.next();
            %>

            <div class="column">
                <div class="card">
                    <img class="card-img-top" src="<%=response.encodeURL("PictureControl?id=" + bean.getCode())%>"
                         alt="Card image cap">
                    <div class="card-body">
                        <p class="card-text">
                            <form id="<%=i%>" action="<%=response.encodeURL("CarrelloController")%>" method="get"
                                  class="myForms">
                                <input name="action" value="addCart" style="visibility: hidden">
                                <input name="id" value="<%=bean.getCode()%>" style="visibility: hidden">
                                <p><%=bean.getName()%></p>
                                <p><%=bean.getDescription()%></p>
                                <p>Prezzo: <%=bean.getPrice()%></p>
                                <p>Quantità disponibile:
                                <p id="<%="disp" + i%>"><%=bean.getQuantity()%>
                                </p></p>
                                <p>Quanti ne vuoi?
                                    <input id="<%="compro" + i++%>" class="myNumberInputs" min="0"
                                                          max="<%=bean.getQuantity()%>"
                                                          type="number" name="quantity" value="1" style=width:20%>
                                </p>
                                <button id=cart type="submit"><span>Aggiungi al carrello</span></button>
                            </form>
                    </div>
                </div>
            </div>

            <%
                }
            } else {%>
        </div>
    </div>
    <p>Non ci sono prodotti disponibili</p>
</div>
<% } %>
<%
    User us = (User) session.getAttribute("user");
    if (us != null && User.isAdmin(us)) {%>
<div class=container-fluid>
    <form method="post" action="<%=response.encodeURL("aOcProduct.jsp")%>">
        <input hidden name="operation" value="aggiungi prodotto">
        <button style="margin-top:2%;margin-left:0;" class="product" type="submit" name="operation"><span>Aggiungi Prodotto</span></button>
    </form>


    <form method="post" action="<%=response.encodeURL("aOcProduct.jsp")%>">
        <input hidden name="operation" value="modifica prodotto">
        <button style="margin-top:0%;margin-left:0;" class="product" type="submit" name="operation"><span>Modifica Prodotto</span></button>
    </form>
</div>
<%} %>


<button id=op onClick="location.href = '<%=response.encodeURL("CarrelloController?action=showCart")%>' "><span>Vai al carrello</span>
</button>


<script>
    $(".myForms").submit(function (e) {

        e.preventDefault(); // avoid to execute the actual submit of the form.

        let form = $(this);
        let url = form.attr('action');
        let id = form.attr('id');
        let dispId = "#disp" + id;
        let comproID = "#compro" + id;
        let dispInt = parseInt($(dispId).html());
        let comproVal = $(comproID).val();
        let somma = dispInt - comproVal;

        $.ajax({
            type: "GET",
            url: url,
            data: form.serialize()
        }).done(function () {
            $(".myNumberInputs").val('1');
            $(dispId).html(somma); //aggiorna live le q.tà disponibili (ma solo localmente fino al buy)
            $(comproID).attr('max', somma);
        });
    });
</script>

</body>
</html>
