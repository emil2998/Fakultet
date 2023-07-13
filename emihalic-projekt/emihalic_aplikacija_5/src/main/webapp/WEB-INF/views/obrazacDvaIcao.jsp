<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Udaljenosti</title>
</head>
<body>
	<a href="${pageContext.servletContext.contextPath}/mvc/nadzor/index">Početna</a>
	</br></br>
	 <form action = "${pageContext.servletContext.contextPath}/mvc/aerodromi/udaljenosti" method="GET">
	 	</br>
	  	<label for="icaoOd">icao od:</label>
	  	<input type="text" id="icaoOd" name="icaoOd" required>
	 	<label for="icaoDo">icao do:</label>
		<input type="text" id="icaoDo" name="icaoDo" required>
	  	<button type="submit">Traži</button>
	</form>
</body>
</html>