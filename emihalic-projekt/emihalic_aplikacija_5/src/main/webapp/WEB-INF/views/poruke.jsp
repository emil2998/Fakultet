<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List, java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Poruke</title>
<h1>Poruke</h1>
<tr>
	<c:forEach var="podatak" items="${requestScope.podaci}">
		<td>${podatak}</td>
	</c:forEach>
	</br></br>
	<a href="${pageContext.servletContext.contextPath}/mvc/aerodromi/pocetak">Naslovnica</a>
	</br></br>
</tr>
</head>
<body>
<%
  ArrayList<String> poruke = (ArrayList<String>) request.getAttribute("poruke");
  if (poruke != null && !poruke.isEmpty()) {
    for (String poruka : poruke) {
%>
<a><%= poruka %></a><br/><br>
<%}}%>
</body>
</html>