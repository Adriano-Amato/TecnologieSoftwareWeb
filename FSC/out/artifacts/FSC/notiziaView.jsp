<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String titolo = (String) request.getAttribute("titolo");
String articolo = (String) request.getAttribute("articolo");%>
<html>
<head>
    <link rel="shortcut icon" href="images/favico.png" type="image/x-icon"/>
    <title><%=titolo%></title>

</head>

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
    
    <link rel="stylesheet" type="text/css" href="style/notizia.css">
    
    
<body>
<jsp:include page="header.jsp"/>

	<div class=container-fluid id=1>
    <header><%=titolo%></header>
    <article><%=articolo%></article>
</div>
</body>
</html>
