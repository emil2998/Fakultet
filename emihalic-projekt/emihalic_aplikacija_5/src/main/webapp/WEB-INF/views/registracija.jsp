<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registracija</title>
</head>
<body>
	<a href="${pageContext.servletContext.contextPath}/mvc/nadzor/index">Početna</a>
	</br></br>
	<c:choose>
	<c:when test="${prijava==true}">
		<a href="${pageContext.servletContext.contextPath}/mvc/korisnici/odjava">Odjava</a>	
 	</c:when>    
   	<c:otherwise>
   		 <form action = "${pageContext.servletContext.contextPath}/mvc/korisnici/registriraj" method="POST">
   		 	<a>Morate popuniti sva polja!</a>
   		 	</br>
		  	<label for="korime">Korisničko ime:</label>
		  	<input type="text" id="korime" name="korime" required>
		 	<label for="lozinka">Lozinka:</label>
			<input type="password" id="lozinka" name="lozinka" required>
			<label for="ime">Ime:</label>
			<input type="text" id="ime" name="ime" required>
			<label for="prezime">Prezime:</label>
			<input type="text" id="prezime" name="prezime" required>
		  	<button type="submit">Registracija</button>
		</form>
    </c:otherwise>
    </c:choose>
</body>
</html>