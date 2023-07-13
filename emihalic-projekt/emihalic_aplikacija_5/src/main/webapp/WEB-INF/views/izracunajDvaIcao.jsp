<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Udaljenosti</title>
<style>
table {
  font-family: alata;
  border-collapse: collapse;
  width: 100%;
}

td, th {
  border: 5px solid #cccccc;
  text-align: left;
  padding: 10px;
}

tr:nth-child(even) {
  background-color: #eeeeee;
}
</style>
</head>
<body>
	<a href="${pageContext.servletContext.contextPath}/mvc/nadzor/index">Početna</a>
	</br></br>
	
	<a>Udaljenost: ${requestScope.udaljenost} km</a>
</body>
</html>