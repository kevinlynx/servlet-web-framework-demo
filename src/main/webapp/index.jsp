<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>index page</title>
</head>
<body>
<h2>hello ${name}</h2>
<p>
<h4>By EL</h4>
<c:forEach var="lang" items="${langs}">
  <span>${lang}</span>|
</c:forEach>
<br/>
<c:forEach var="lang" items="${langList}">
  <span>${lang}</span>|
</c:forEach>
</p>
<p>
<h4>Old style</h4>
<% String[] langs = (String[]) request.getAttribute("langs"); %>
<% if (langs != null) { %>
<% for (String lang : langs) { %>
  <span><%= lang %></span>|
<% } } %>
</p>
</body>
</html>

