<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pregled aerodroma</title>
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
		<table class="display">
		<thead>
			<tr>
				<th>Korisničko ime</th>
				<th>Lozinka</th>
				<th>Ime</th>
				<th>Prezime</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="korisnik" items="${requestScope.odgovor}">
				<tr>
					<td>${korisnik.getKorime()}</a></td>
					<td>${korisnik.getLozinka()}</td>
					<td>${korisnik.getIme()}</td>
					<td>${korisnik.getPrezime()}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>