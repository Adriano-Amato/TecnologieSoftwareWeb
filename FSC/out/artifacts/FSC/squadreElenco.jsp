<%@ page import="com.classes.model.bean.entity.SquadraBean" %>
<%@ page import="java.util.Collection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.classes.model.bean.entity.SquadraBean" %>
<%@ page import="java.util.Iterator" %>
<%--
USA QUESTA PAGINA PER MOSTRARE L'ELENCO DI TUTTE LE SQUADRE
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Collection<SquadraBean> squadre = (Collection<SquadraBean>) request.getAttribute("collezioneSquadra");
    String error = (String) request.getAttribute("error");
    if (squadre == null && error == null) {
        /*vuol dire che fin'ora non siamo MAI passati per la Servlet*/
        response.sendRedirect(response.encodeRedirectURL("SquadraController"));
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
    <title>Elenco Squadre</title>
    <link rel="stylesheet" type="text/css" href="style/squadre.css">

</head>
<body>

<jsp:include page="header.jsp"/>

<table>
    <tr>
        <th>Nome <a href="<%=response.encodeURL("SquadraController?sort=nome")%>">Sort</a></th>
        <th>Categoria <a href="<%=response.encodeURL("SquadraController?sort=categoria")%>">Sort</a></th>
        <th>Età <a href="<%=response.encodeURL("SquadraController?sort=etabambini")%>">Sort</a></th>
    </tr>
    <% if (squadre != null && squadre.size() > 0) {
        Iterator<?> iterator = squadre.iterator();
        while (iterator.hasNext()) {
            SquadraBean bean = (SquadraBean) iterator.next();
    %>
    <tr>
        <td><%=bean.getName()%>
        </td>
        <td><%=bean.getCategory()%>
        </td>
        <td><%=bean.getAge_range()%>
        </td>
    </tr>

    <%
        }
    } else {%>
    <tr>
        <td colspan="5">No teams available</td>
    </tr>
    <% } %>
</table>

<p>
    <%
        if (error != null) {
    %>
    Error: <%= error%>
    <%}%>
</p>
</body>
</html>
