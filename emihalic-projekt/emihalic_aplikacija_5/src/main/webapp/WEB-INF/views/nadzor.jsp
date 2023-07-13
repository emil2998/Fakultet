<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nadzor</title>
</head>
<body>
	<a href="${pageContext.servletContext.contextPath}/mvc/nadzor/index">Početna</a>
	</br></br>
	<a>${requestScope.odgovor}</a>
	</br></br> 
	<button onclick='window.location.href="${pageContext.servletContext.contextPath}/mvc/nadzor/nadzor?naredba=STATUS"'>Prikaži status poslužitelja</button>
	</br></br> 
	<button onclick='window.location.href="${pageContext.servletContext.contextPath}/mvc/nadzor/nadzor?naredba=INFODA"'>Prikaži info u poslužitelju</button> 
	</br></br>
	<button onclick='window.location.href="${pageContext.servletContext.contextPath}/mvc/nadzor/nadzor?naredba=INFONE"'>Sakrij info u poslužitelju</button> 
	</br></br>
	<button onclick='window.location.href="${pageContext.servletContext.contextPath}/mvc/nadzor/nadzor?naredba=INIT"'>Aktiviraj poslužitelj</button> 
	</br></br>
	<button onclick='window.location.href="${pageContext.servletContext.contextPath}/mvc/nadzor/nadzor?naredba=PAUZA"'>Pauziraj poslužitelj</button> 
	</br></br>
	<button onclick='window.location.href="${pageContext.servletContext.contextPath}/mvc/nadzor/nadzor?naredba=KRAJ"'>Ugasi poslužitelj</button> 
	
</body>
</html>