<%@ page import="com.classes.model.bean.users.User" %>
<%@ page import="com.classes.model.dao.SquadraModel" %>
<%@ page import="com.classes.model.bean.entity.SquadraBean" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%
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
        label = "Area Personale";
        destination = request.getContextPath() + "/personalPage.jsp";
    }
%>
<link rel="stylesheet" type="text/css" href="style/header.css">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="index.jsp">FSC</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item dropdown">
                <a style="color: white" class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    Le nostre squadre
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <%
                        SquadraModel sm = new SquadraModel();
                        Collection<SquadraBean> squadre = sm.doRetriveAll("nome");
                        Iterator<?> iterator = squadre.iterator();

                        while (iterator.hasNext()) {
                            SquadraBean bean = (SquadraBean) iterator.next();
                    %>
                    <span><a class=dropdown-item
                             href="SquadraController?nomeSquadra=<%=bean.getName()%>"><%=bean.getName()%></a></span>
                    <% } %>
                </div>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="<%=response.encodeURL("productView.jsp")%>">Shop<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="<%=response.encodeURL(destination)%>"><%=label%><span class="sr-only">(current)</span></a>
            </li>
            <%if(user!=null){%>
            <li class="nav-item active">
                <a class="nav-link" href="<%=response.encodeURL("LoginController?action=logout")%>">Log Out<span
                        class="sr-only">(current)</span></a>
            </li>
            <%}%>
        </ul>
    </div>
</nav>
