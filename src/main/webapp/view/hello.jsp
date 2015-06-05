<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>hello page</title>
</head>
<body>
<h2>hello ${name}</h2>
<form action='./hello' method='post'>
  <input type='text' name='name'>
  <input type='submit'>
</form>
</body>
</html>

