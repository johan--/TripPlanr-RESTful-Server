<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head><title>Users</title></head>
<body>
<table>
<tr><th>ID</th><th>Email</th><th>Enabled</th></tr>
<c:forEach items="${users}" var="user">
<tr>
  <td>${user.id}</td>
  <td>${user.email}</td>
  <td>${user.enabled}</td>
</tr>
</c:forEach>
</table>
</body>
</html>
