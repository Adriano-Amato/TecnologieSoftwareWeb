<%@ page import="com.classes.model.bean.entity.SquadraBean" %>
<%@ page import="com.classes.model.bean.users.IscrittoUserBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.classes.model.bean.staff.StaffBean" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.classes.model.bean.entity.SquadraBean" %>
<%--
UTILIZZA QUESTA PAGINA PER MOSTRARE I DETTAGLI DI UNA SOLA SQUADRA
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SquadraBean squadra = (SquadraBean) request.getAttribute("squadra");
    Collection<IscrittoUserBean> partecipanti = (Collection<IscrittoUserBean>) request.getAttribute("partecipantiSquadra");
    Collection<StaffBean> staff = (Collection<StaffBean>) request.getAttribute("staffSquadra");
    String error = (String) request.getAttribute("error");
    if (squadra == null && error == null) {
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

    <title>Dettagli Squadra</title>
    <link rel="stylesheet" type="text/css" href="style/squadre.css">

</head>
<body>

<jsp:include page="header.jsp"/>

<div class=container-fluid>

    <div class=squad>
        <table id=squad>
            <tr>
                <th>NOME: ${squadra.name}</th>
                <th>ETA': ${squadra.age_range}</th>
                <th>CATEGORIA: ${squadra.category}</th>
            </tr>
        </table>
    </div>
    <div class=bambini>
        <h1 style=text-align:center>Bambini</h1>
        <table>
            <tr>
                <th> Nome bambino</th>
                <th> Cognome bambino</th>
                <th> Età bambino</th>
            </tr>
            <%
                for (IscrittoUserBean i : partecipanti) {
            %>

            <tr>
                <td><%=i.getNome() %>
                </td>
                <td><%=i.getCognome() %>
                </td>
                <td><%=i.getEta() %>
                </td>
            </tr>
            <%
                }
            %>
        </table>
    </div>
    <div class=staff>
        <h1>Staff</h1>
        <table>
            <tr>
                <th> Nome</th>
                <th> Cognome</th>
                <th> Ruolo</th>
            </tr>
            <%
                for (StaffBean i : staff) {
            %>
            <tr>
                <td><%=i.getName()%>
                </td>
                <td><%=i.getSurname()%>
                </td>
                <td><%=i.getRole() %>
                </td>
            </tr>

            <%
                }
            %>
        </table>
    </div>
</div>
</body>
</html>
