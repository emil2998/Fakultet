<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pregled korisnika</title>
</head>
<body>
	<a href="${pageContext.servletContext.contextPath}/mvc/nadzor/index">Početna</a>
	</br></br>
	<c:choose>
	<c:when test="${prijava==true}">
		<form action = "${pageContext.servletContext.contextPath}/mvc/korisnici/pregledajKorisnike" method="POST">
		  	<label for="ime">Ime:</label>
		  	<input type="text" id="ime" name="ime" >
			<br/>
		 	<label for="prezime">Prezime:</label>
			<input type="text" id="prezime" name="prezime" >
			<br/>
		  	<button type="submit">Traži</button>
		</form>
 	</c:when>    
   	<c:otherwise>
   		 <a href="${pageContext.servletContext.contextPath}/mvc/korisnici/prijava">Prijavi se</a>	
    </c:otherwise>
    </c:choose>
</body>
</html>