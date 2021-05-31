<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.classes.model.bean.staff.StaffBean" %>
<%@ page import="com.classes.model.bean.entity.SquadraBean" %>
<%
    Collection<StaffBean> staff = (Collection<StaffBean>) request.getAttribute("staff");
    if (staff == null) {
        request.setAttribute("destination", "/removeStaff.jsp");
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
    <title>Rimuovi Staff</title>
    <link rel="stylesheet" type="text/css" href="style/add.css">

</head>
<body>

<jsp:include page="header.jsp"/>

<h1>Rimuovi Staff</h1>
<div class=container-fluid id=add>
    <form action="<%=response.encodeURL("StaffManagementController")%>">
        <input type="hidden" name="action" value="remove">
        <div class="row">
            <label>Staff da eliminare</label>
            <div class="col">
                <select required id="staff" name="member" style=width:80%>
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
                </select>
            </div>            
		</div>
		<button id=test type="submit">Conferma</button>
    </form>
</div>
</body>
</html>