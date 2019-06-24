<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div style="background: #E0E0E0; height: 55px; padding: 5px;">
	<div style="float: left">

		<div>
			<h1>My Site</h1>
			<sec:authorize access="isAuthenticated()">
				<c:url value="/logout" var="logoutUrl"></c:url>
				<a href="${logoutUrl}" class="btn btn-danger">Logout</a>
			</sec:authorize>
			<sec:authorize access="!isAuthenticated()">
				<c:url value="/login" var="loginUrl"></c:url>
				<c:url value="/register" var="registerUrl"></c:url>
				<a href="${loginUrl}" class="btn btn-success">Login</a>
				<a href="${registerUrl}" class="btn btn-primary">Register</a>
			</sec:authorize>
			


		</div>

	</div>

	<div style="float: right; padding: 10px; text-align: right;">

		Search <input name="search">

	</div>

</div>