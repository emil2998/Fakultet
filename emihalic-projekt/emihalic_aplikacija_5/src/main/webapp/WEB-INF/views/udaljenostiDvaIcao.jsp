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
	
	<a>Ukupno ima: ${requestScope.ukupno} km</a>
	</br>
		<table class="display">
		<thead>
			<tr>
				<th>država</th>
				<th>km</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="udaljenost" items="${requestScope.udaljenosti}">
				<tr>
					<td>${udaljenost.drzava()}</a></td>
					<td>${udaljenost.km()}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>