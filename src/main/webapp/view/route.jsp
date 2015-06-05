<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="route" uri="/myweb-router" %>
<html>
<head>
<title>route test page</title>
</head>
<body>
<a href='<route:reverse action="com.codemacro.webdemo.test.TestController.hello" name="kevin"/>'>index</a>
</body>
</html>


