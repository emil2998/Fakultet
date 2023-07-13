<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Naslovnica</title>
<h1>Naslovnica</h1>
<style>
ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
  width: 200px;
  background-color: #D3D3D3;
}

li a {
  display: block;
  color: #000;
  padding: 8px 16px;
  text-decoration: none;
}

li a:hover {
  background-color: #444;
  color: white;
}
</style>
	<ul>
		<li><a href="${pageContext.servletContext.contextPath}/mvc/korisnici/korisniciMenu">Korisnici</a></li>
		<li><a href="${pageContext.servletContext.contextPath}/mvc/nadzor/nadzor">Nadzor</a></li>
		<li><a href="${pageContext.servletContext.contextPath}/mvc/aerodromi/aerodromiMenu">Aerodromi</a></li>
	</ul>
</head>
<body>
</body>
</html>