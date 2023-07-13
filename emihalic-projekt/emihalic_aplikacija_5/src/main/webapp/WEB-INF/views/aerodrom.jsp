<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Aerodrom</title>
<h1>Aerodrom</h1>
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
<tr>
	<c:forEach var="podatak" items="${requestScope.podaci}">
		<td>${podatak}</td>
	</c:forEach>
	</br></br>
	<a href="${pageContext.servletContext.contextPath}/mvc/nadzor/index">Naslovnica</a>
	</br></br>
</tr>
</head>
<body>
<table class="display" style="text-align:center">
		<thead>
			<tr>
				<th>icao</th>
				<th>ime</th>
				<th>država</th>
				<th>g.širina</th>
				<th>g.dužina</th>
				<th>izlazak sunca</th>
				<th>zalazak sunca</th>
				<th>temperatura</th>
				<th>m.j. temperature</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>${aerodrom.icao}</a></td>
				<td>${aerodrom.naziv}</td>
				<td>${aerodrom.drzava}</td>
				<td>${aerodrom.lokacija.longitude}</td>
				<td>${aerodrom.lokacija.latitude}</td>
				<td>${meteo.sunRise}</td>
				<td>${meteo.sunSet}</td>
				<td>${meteo.temperatureValue}</td>
				<td>${meteo.temperatureUnit}</td>
			</tr>
		</tbody>
	</table>
</body>
</html>