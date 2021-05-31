<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ page import="com.classes.model.bean.entity.AcquistoBean" %>
<%@ page import="com.classes.model.bean.products.RettaBean" %>
<%@ page import="com.classes.model.bean.users.User" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.classes.model.bean.staff.StaffBean" %>
<%@ page import="com.classes.model.bean.users.IscrittoUserBean" %>
<%@ page import="com.classes.model.bean.entity.SquadraBean" %>
<%
    User user = (User) request.getSession().getAttribute("user");
    if (user == null) {
        request.setAttribute("destination", "/adminPage.jsp");
        request.setAttribute("error", Boolean.FALSE);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    } else if (!(User.isAdmin(user))) {
        response.sendError(response.SC_FORBIDDEN, "Non sei admin!");
        return;
    }

    Collection<StaffBean> staff = (Collection<StaffBean>) request.getAttribute("staff");
    if(staff==null){
        request.setAttribute("destination", "/adminPage.jsp");
        request.getRequestDispatcher("StaffController").forward(request, response);
        return;
    }

    Collection<IscrittoUserBean> iscritti = (Collection<IscrittoUserBean>) request.getAttribute("iscritti");
    if(iscritti==null){
        request.setAttribute("destination", "/adminPage.jsp");
        request.getRequestDispatcher("IscrittoController").forward(request, response);
        return;
    }

    Collection<RettaBean> rette = (Collection<RettaBean>) request.getAttribute("rette");
    Collection<AcquistoBean> acquisti = (Collection<AcquistoBean>) request.getAttribute("acquisti");
    Integer entrateS = (Integer) request.getAttribute("entrateS");
    Integer entrateI = (Integer) request.getAttribute("entrateI");
    if(entrateS==null||entrateI==null||rette==null||acquisti==null){
        request.getRequestDispatcher("EntrateController").forward(request, response);
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
    <title>Admin's Console</title>
    <link rel="stylesheet" type="text/css" href="style/admin.css">

</head>
<body>

<jsp:include page="header.jsp"/>

<div class=container-fluid>
	<div class=row>
	<div class=column id=table>
    <div class=EntrateTotali>
    <div class=back>
        <h1>Entrate Totali</h1>
        </div>
        <table>
        <thead>
            <tr>
                <th>Entrate Iscrizioni</th>
                <th>Entrate Store</th>
	
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><%= entrateI %> $
                </td>
                <td><%= entrateS %> $
                </td>
            </tr>
            </tbody>
        </table>

	</div>
    </div>
    <div class=column id=table>
    <div class=Acquisti>
    	<div class=back>
        <h1>Acquisti</h1>
        </div>
        <table>
        	<thead>
            <tr>
                <th>User</th>
                <th>Prodotto</th>
                <th>Quantità</th>
                <th>Codice acquisto</th>
            </tr>
            </thead>
            <tbody>
            
            <% //PurchaseView
                Iterator iterator = null;
                if (acquisti != null && acquisti.size() > 0) {
                    iterator = acquisti.iterator();
                    while (iterator.hasNext()) {
                        AcquistoBean bean = (AcquistoBean) iterator.next();%>
            
            <tr>
                <td><%=bean.getUtente().getUsername()%>
                </td>
                <td><%=bean.getProdotto().getName()%>
                </td>
                <td><%=bean.getQuantita()%>
                </td>
                <td><%=bean.getCodAcquisto()%>
                </td>
            </tr>
            

            <%
                }%>
              </tbody>  
        </table>
       </div>
    </div>
    <% } else {%>
    <p>No purchase available</p>
    <% } %>
    <div class=column id=table>
    <div class=Iscritti>
  		<div class=back>
        <h1>Iscritti</h1>
        	<div class=column>
        	  <form action="/changeStats.jsp">
                    <button type="submit">Cambia le statistiche</button>
                </form>
                </div>
          </div>
        <table>
        	<thead>
            <tr>
                <th>Nome</th>
                <th>Cognome</th>
                <th>Età</th>
                <th>Codice Fiscale</th>
                <th>Squadra</th>
                <th>Goal</th>
                <th>Minuti</th>
                <th>Assist</th>
            </tr>
            </thead>
            <tbody>
            
            <%
                if (iscritti != null && iscritti.size() > 0) {
                    for (IscrittoUserBean iscritto : iscritti) {
            %>
            <tr>
                <td><%=iscritto.getNome()%>
                </td>
                <td><%=iscritto.getCognome()%>
                </td>
                <td><%=iscritto.getEta()%>
                </td>
                <td><%=iscritto.getCodFis()%>
                </td>
                <td><a href="SquadraController?nomeSquadra=<%=iscritto.getSquadra().getName()%>"><%=iscritto.getSquadra().getName()%></a>
                </td>
                <td><%=iscritto.getGoal()%>
                </td>
                <td><%=iscritto.getMinuti()%>
                </td>
                <td><%=iscritto.getAssist()%>
                </td>
            </tr>
            <%}%>
          
            </tbody>
        </table>
        </div>
    </div>
    <% } else {%>
    <p>No subscribers available</p>
    <% } %>
	<div class=column id=table>
    <div class=Iscrizioni>
    	
    	<div class=back>
        <h1>Iscrizioni</h1>
        </div>
        <table>
      		<thead>
            <tr>
                <th>User</th>
                <th>Ammontare</th>
                <th>Data inizio</th>
                <th>Data fine</th>
                <th>Codice retta</th>
            </tr>
          </thead>
           <tbody>
            <% //SubscriptionView
                if (rette != null && rette.size() > 0) {
                    iterator = rette.iterator();
                    while (iterator.hasNext()) {
                        RettaBean beanIscrizioni = (RettaBean) iterator.next();
                        Calendar calInizio = Calendar.getInstance();
                        calInizio.setTime(beanIscrizioni.getDataInizio());
                        Calendar calFine = Calendar.getInstance();
                        calFine.setTime(beanIscrizioni.getDataFine());
            %>
            <tr>
                <td><%=beanIscrizioni.getIscritto().getUsername()%>
                </td>
                <td><%=beanIscrizioni.getImporto()%>
                </td>
                <td><%= calInizio.get(Calendar.DAY_OF_MONTH)%>-<%= calInizio.get(Calendar.MONTH) + 1%>-<%=calInizio.get(Calendar.YEAR)%>
                </td>
                <td><%=calFine.get(Calendar.DAY_OF_MONTH)%>-<%=calFine.get(Calendar.MONTH) + 1%>-<%=calFine.get(Calendar.YEAR)%>
                </td>
                <td><%=beanIscrizioni.getProgressivo()%>
                </td>
            </tr>
            
            <%
                }%>
        </tbody>
        </table>
        </div>
        <% } else {%>
        <p>No subscription available</p>
        <% } %>
    </div>
	<div class=column id=table>
    <div class=Staff>
    	<div class=back>
        <h1>Staff</h1>
        <div class=row>
        	<div class=column style=margin-left:auto;margin-right:auto;>
        	  <form action="/addStaff.jsp">
                    <button type="submit">Aggiungi Staff</button><br>
                </form>
               </div>
               <div class=column style=margin-left:auto;margin-right:auto;>
                <form action="/modifyStaff.jsp">
                    <button type="submit">Modifica Staff</button><br>
                </form>
                </div>
                <div class=column style=margin-left:auto;margin-right:auto;>                
                <form action="/removeStaff.jsp">
                    <button type="submit">Rimuovi Staff</button><br>
                </form>
                </div>
               
		</div>
		</div>
		<table>
      		<thead>
        
            <tr>
                <th>Nome</th>
                <th>Cognome</th>
                <th>Codice fiscale</th>
                <th>Squadra</th>
                <th>Ruolo</th>
            </tr>
            </thead>
          	<tbody>
            <%
                if (staff != null && staff.size() > 0) {
                    for (StaffBean s : staff) {
            %>
            <tr>
                <td><%=s.getName()%>
                </td>
                <td><%=s.getSurname()%>
                </td>
                <td><%=s.getCodFis()%>
                </td>
                <td><%=s.getSquadra().getName()%>
                </td>
                <td><%=s.getRole()%>
                </td>
            </tr>

            <%
                }%>
         </tbody>        
        </table>
        </div>
        <% } else {%>
        <% } %>
</div>	
		<div class=column id=table>
        <div class=Squadre>
        <div class=back>
        <h1>Squadre</h1>
        	<div class=column>
			 <form action="/addSquad.jsp">
                    <button type="submit">Aggiungi Squadra</button>
                </form>
               </div>
    	<table>
      		<thead>
        
            <tr>
                <th>Nome</th>
                <th>Età</th>
                <th>Categoria</th>
            </tr>
            
            </thead>
            <tbody>
            <%
                if (squadreView != null && squadreView.size() > 0) {
                    for (SquadraBean s : squadreView) {
            %>
            <tr>
                <td><%=s.getName()%>
                </td>
                <td><%=s.getAge_range()%>
                </td>
                <td><%=s.getCategory()%>
                </td>
            </tr>
            <%
                }%>
            <tr>
               
            </tr>  
         </tbody>      
        </table>
        </div>
        <% } else {%>
        <% } %>
    </div>
    </div>
</div>
</div>
</body>
</html>