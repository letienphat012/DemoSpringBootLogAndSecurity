<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<h2>
	<sec:authorize access="isAuthenticated()"> Hi 
	<sec:authentication property="principal.username" />
	Roles: <span><sec:authentication
				property="principal.authorities" /> </span>
		<c:url value="/logout" var="logoutUrl"></c:url>
		<a href="${logoutUrl}" value="Logout" class="btn btn-danger">Log out</a>
	</sec:authorize>
</h2>

