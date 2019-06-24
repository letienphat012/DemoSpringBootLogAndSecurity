<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="main-content">
	<c:if test="${param.error ne null}">
		<div class="alert alert-danger">Invalid email or password</div>
	</c:if>
	<c:if test="${param.logout ne null}">
		<div class="alert alert-success">You have been logged out</div>
	</c:if>

	<c:if test="${success ne null}">
		<div class="alert alert-success">${success}</div>
	</c:if>
	<h3>Login to continue</h3>
	<c:url value="/login" var="loginUrl"></c:url>
	<form:form name="loginForm" action="${loginUrl }" method="post"
		onsubmit="return validateForm();">
		<div class="form-group">
			<input type="text" name="email" placeholder="Your email"
				class="form-control"><br> <input type="password"
				name="password" placeholder="Your password" class="form-control">
		</div>
		<input type="submit" value="Login" class="btn btn-success">
	</form:form>
	<script type="text/javascript">
		function validateForm() {
			var email = document.forms["loginForm"]["email"];
			var password = document.forms["loginForm"]["password"];
			if (email.value == null || email.value == "") {
				alert("Email must not blank");
				email.focus();
				return false;
			}

			if (password.value == null || password.value == "") {
				alert("Password must not blank");
				password.focus();
				return false;
			}
		}
	</script>
</div>