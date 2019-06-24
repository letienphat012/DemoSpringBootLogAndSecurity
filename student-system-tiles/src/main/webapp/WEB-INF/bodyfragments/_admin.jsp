<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<h2>
	<sec:authorize access="isAuthenticated()"> Welcome back, admin 
	<sec:authentication property="principal.username" />
	Roles: <span><sec:authentication
				property="principal.authorities" /> </span>
		<form action="/logout" method="post">
			<input type="submit" value="Logout" class="btn btn-danger">
		</form>
	</sec:authorize>
</h2>

