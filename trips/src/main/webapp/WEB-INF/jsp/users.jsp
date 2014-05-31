<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Users</title>
</head>
<body>
	<table>
		<tr>
			<th>ID</th>
			<th>Email</th>
			<th>Enabled</th>
			<th>DeviceId</th>
			<th>ClientId</th>
			<th>ClientSecret</th>
		</tr>
		<c:forEach items="${users}" var="user">
			<tr>
				<td style="padding-right: 23px;">${user.id}</td>
				<td style="padding-right: 23px;">${user.email}</td>
				<td style="padding-right: 23px;">${user.enabled}</td>
				<td style="padding-right: 23px;">${user.deviceId}</td>
				<td style="padding-right: 23px;">${user.clientId}</td>
				<td style="padding-right: 23px;">${user.clientSecret}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
