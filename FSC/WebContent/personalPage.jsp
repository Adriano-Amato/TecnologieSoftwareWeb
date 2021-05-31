<%@ page import="com.classes.model.bean.entity.AcquistoBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.classes.model.bean.users.IscrittoUserBean" %>
<%@ page import="com.classes.model.bean.products.RettaBean" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.classes.model.bean.users.User" %>
<%
    User user = (User) request.getSession().getAttribute("user");
    if (user == null) {
        request.setAttribute("destination", "/personalPage.jsp");
        request.setAttribute("error", Boolean.FALSE);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }
    if (User.isAdmin(user)) {
        request.getRequestDispatcher("/adminPage.jsp").forward(request, response);
    }

    Collection<AcquistoBean> acquistiUser = (Collection<AcquistoBean>) request.getAttribute("purchase");
    if (acquistiUser == null) {
        request.setAttribute("action", "byUser");
        request.setAttribute("user", user.getUsername());
        request.setAttribute("destination", "/personalPage.jsp");
        request.getRequestDispatcher("AcquistoController").forward(request, response);
        return;
    }

    Collection<RettaBean> rette = (Collection<RettaBean>) request.getAttribute("subscription");
    if(rette==null){
        request.setAttribute("destination", "/personalPage.jsp");
        request.getRequestDispatcher("RettaController").forward(request, response);
        return;
    }
%>
<html>
<head>
    <link rel="shortcut icon" href="images/favico.png" type="image/x-icon"/>
    <title>Pagina personale</title>
    <link rel="stylesheet" type="text/css" href="style/personal.css">

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

<div class=container-fluid>
<div class=row>
<div class=column>
    <div class=dati>
        <h1>Dati Personali</h1>        	
        <table>
        <thead>
            <tr>
                <th>Nome</th>
                <th>Cognome</th>
                <th>Username</th>
                <th>Password</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><%=user.getNome()%>
                </td>
                <td><%=user.getCognome()%>
                </td>
                <td><%=user.getUsername()%>
                </td>
                <td><%=user.getPassword()%>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    </div>

    	
            <% if (acquistiUser != null && acquistiUser.size() > 0) {%>
            <div class=column>
           <div class=acquisti>
        		<div class=prodotto>
            <h1>Acquisti</h1>
            <table>
            <thead>
                <tr>
                    <th>Nome prodotto</th>
                    <th>Quantità acquistata</th>
                    <th>Codice acquisto</th>
                </tr>
                </thead>
                <tbody>
                <%
                    for (AcquistoBean ab : acquistiUser) {
                %>
                <tr>
                    <td><%=ab.getProdotto().getName()%>
                    </td>
                    <td><%=ab.getQuantita()%>
                    </td>
                    <td><%=ab.getCodAcquisto()%>
                    </td>
                </tr>
                <%}%>
                </tbody>
            </table>
        </div>
        </div>
        </div>
        <%
        } else {
        %>
    
        <%}%>
        
    <%
        if (user instanceof IscrittoUserBean) {
            IscrittoUserBean iscritto = (IscrittoUserBean) user;
            Iterator<RettaBean> iter = rette.iterator();
            Date dataScad = null;
            Date dataNow = new java.util.Date();
            RettaBean appRett;


            while (iter.hasNext()) {
                appRett = iter.next();
                if (appRett.getIscritto().getUsername().equals(iscritto.getUsername()))
                    dataScad = appRett.getDataFine();
            }

            String pattern = "dd/MM/yyyy ";
            //Create an instance of SimpleDateFormat used for formatting
            //the string representation of date according to the chosen pattern
            DateFormat df = new SimpleDateFormat(pattern);

            //Using DateFormat format method we can create a string
            //representation of a date with the defined format.
            String dataOut = null;
            if (dataScad != null)
                dataOut = df.format(dataScad);
    %>
	<div class=column>
    <div class=stat>
        <h1>Statistiche giocatore</h1>
        <table>
        <thead>
            <tr>
                <th>Minuti giocati</th>
                <th>Goal fatti</th>
                <th>Assist fatti</th>
                <th>Squadra</th>
                <th>Età</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td><%=iscritto.getMinuti()%>
                </td>
                <td><%=iscritto.getGoal()%>
                </td>
                <td><%=iscritto.getAssist()%>
                </td>
                <td><a href="SquadraController?nomeSquadra=<%=iscritto.getSquadra().getName()%>"><%=iscritto.getSquadra().getName()%></a>
                </td>
                <td><%=iscritto.getEta()%>
                </td>
            </tr>
            </tbody>
        </table>
	</div>
	</div>
    	
		
		 <div class=column>
        	<div class=scaduta>
        <% if (dataScad != null && dataNow.before(dataScad)) {%>
        	<p style="padding-top: 5%">La tua iscrizione scade il: <%=dataOut%>
        	</p>
        <%} else {%>
        <h1 style=background-color:transparent;color:#400080>ISCRIZIONE</h1>
        <p id="iscr">NON HAI UN ISCRIZIONE ATTIVA!</p>
        <form method="post" action="<%=response.encodeURL("/payRetta.jsp")%>">
            <input type="hidden" value="<%=iscritto.getUsername()%>" name="sub">
            <label class="testoIscrizione">Iscrizione mensile (50€ da scontare del 30%):</label>
            <input type="radio" value="mensile" name="typeSub"><br>
            <label class="testoIscrizione">Iscrizione annuale (600€ da scontare del 40%):</label>
            <input type="radio" value="annuale" name="typeSub"><br>
            <button id=test type="submit">Conferma</button>
        </form>
       
        <%}%>
         </div>
    	</div>
    	
    	
    <%}%>
</div>
    	</div>
</body>
</html>