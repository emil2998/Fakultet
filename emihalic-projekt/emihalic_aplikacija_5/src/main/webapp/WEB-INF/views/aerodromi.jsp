<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="org.foi.nwtis.podaci.Aerodrom" %>
    <%@ page import="java.util.List, java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Svi aerodromi</title>
<h1>Svi aerodromi</h1>
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
<%

	int brojStranice = (int) request.getAttribute("brojStranice");
	String traziNaziv = (String) request.getAttribute("traziNaziv");
	String traziDrzavu = (String) request.getAttribute("traziDrzavu");
	String contextPath = getServletContext().getContextPath();
	
 	String pocetnaStranica = contextPath+"/mvc/aerodromi/aerodromi"+"?traziNaziv="+traziNaziv+"&traziDrzavu="+traziDrzavu;
  	
	String prethodnaStranica = contextPath+"/mvc/aerodromi/aerodromi?brojStranice=" + (brojStranice-1)+"&traziNaziv="+traziNaziv+"&traziDrzavu="+traziDrzavu;
  	String sljedecaStranica = contextPath+"/mvc/aerodromi/aerodromi?brojStranice=" + (brojStranice+1)+"&traziNaziv="+traziNaziv+"&traziDrzavu="+traziDrzavu;
  
  	String dohvatiJedanAerodrom = contextPath+"/mvc/aerodromi/";
%>
<a href="<%= pocetnaStranica %>">Početak</a>
<%
  if (brojStranice > 1) {
%>
  <a href="<%= prethodnaStranica %>">Prethodna stranica</a>
<%
  }
%>
<a href="<%= sljedecaStranica %>">Sljedeća stranica</a>
	<a href="${pageContext.servletContext.contextPath}/mvc/nadzor/index">Naslovnica</a>
	</br></br>
	<form action = "${pageContext.servletContext.contextPath}/mvc/aerodromi/aerodromi"+${traziNaziv}+${traziDrzavu}+${requestScope.brojStranice}>
	  	<label for="traziNaziv">Naziv:</label>
	  	<input type="text" id="traziNaziv" name="traziNaziv">
		<br/>
	 	<label for="traziDrzavu">Država:</label>
		<input type="text" id="traziDrzavu" name="traziDrzavu">
		<br/>
	  	<button type="submit">Traži</button>
	</form>
	</br></br>
	<table class="display" style="text-align:center">
		<thead>
			<tr>
				<th>ICAO &nbsp;</th>
				<th>NAZIV &nbsp;</th>
				<th>DRŽAVA &nbsp;</th>
			</tr>
		</thead>
		<tbody>
			<%
			  List<Aerodrom> aerodromi = (List<Aerodrom>) request.getAttribute("aerodromi");
			  if (aerodromi != null && !aerodromi.isEmpty()) {
			    for (Aerodrom aerodrom : aerodromi) {
			%>
				<tr>
					<td><a href= "<%= dohvatiJedanAerodrom + aerodrom.getIcao() %>"><%= aerodrom.getIcao() %></a></td>
					<td><%= aerodrom.getNaziv() %></td>
					<td><%= aerodrom.getDrzava() %></td>
				</tr>
				<%}}%>
		</tbody>
	</table>
</body>
</html>