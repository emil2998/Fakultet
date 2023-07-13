<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Prijava</title>
</head>
<body>
	<a href="${pageContext.servletContext.contextPath}/mvc/nadzor/index">Početna</a>
	</br></br>
	<c:choose>
	<c:when test="${odgovor==true}">
		<a>Uspješna prijava</a>	
 	</c:when>    
   	<c:otherwise>
   		 <a>Neuspješna prijava</a>	
    </c:otherwise>
    </c:choose>
</body>
</html>