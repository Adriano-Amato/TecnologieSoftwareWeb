<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.classes.model.bean.staff.StaffBean" %>
<%@ page import="com.classes.model.dao.*" %>
<%@ page import="com.classes.model.bean.entity.SquadraBean" %>
<%
    Collection<StaffBean> staff = (Collection<StaffBean>) request.getAttribute("staff");
    if (staff == null) {
        request.setAttribute("destination", "/modifyStaff.jsp");
        request.getRequestDispatcher("StaffController").forward(request, response);
        return;
    }

    Collection<SquadraBean> squadreView = (Collection<SquadraBean>) request.getAttribute("collezioneSquadra");
    if (squadreView == null) {
        request.getRequestDispatcher("SquadraController").forward(request, response);
        return;
    }
%>
<!DOCTYPE html>
<html>
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

    <meta charset="ISO-8859-1">
    <title>Modifica Staff</title>
    <link rel="stylesheet" type="text/css" href="style/add.css">

</head>
<body>

<jsp:include page="header.jsp"/>

<h1>Modifica staff</h1>
<div class=container-fluid id=add>
    <form action="<%=response.encodeURL("StaffManagementController")%>">
        <input type="hidden" name="action" value="modify">
        <div class="row">
            <label>Staff da modificare</label>
            <div class="col">
                <select required name="member" style=width:100%>
                    <option disabled selected value=>Seleziona una persona dello Staff</option>
                    <%
                        if (staff != null && staff.size() > 0) {
                            for (StaffBean s : staff) {
                    %>
                    <option value="<%=s.getCodFis()%>"><%=s.getName()%> <%=s.getSurname()%>, <%=s.getRole()%>
                        in <%=s.getSquadra().getName()%>
                    </option>
                    <%
                            }
                        }
                    %>
                </select><br>
            </div>
            <div class="col">
                <select required name="ruolo" style=width:100%>
                    <option disabled selected value=>Seleziona un ruolo</option>
                    <option value="Allenatore">Allenatore</option>
                    <option value="Preparatore">Preparatore</option>
                    <option value="Vice allenatore">Vice Allenatore</option>
                    <option value="P. Portieri">Preparatore Portieri</option>
                </select><br>
            </div>
        </div>
        <label>Squadra a cui si vuole assegnare</label>
        <select required name="squadDest" style=width:40%>
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
</div>
</body>
</html>