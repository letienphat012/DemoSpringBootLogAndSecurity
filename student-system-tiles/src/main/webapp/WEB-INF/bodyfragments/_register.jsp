<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
	<c:url value="/registerProcess" var="registerUrl"></c:url>
	<form:form action="${registerUrl}" method="post"
		modelAttribute="newUser">
		<div class="form-group">
			<form:label path="email">Email</form:label>
			<form:input path="email" />
			<form:errors path="email" cssClass="text-danger"></form:errors>
			<br>

			<form:label path="password">Password</form:label>
			<form:input path="password" type="password" />
			<form:errors path="password" cssClass="text-danger"></form:errors>
			<br>
		</div>
		<input type="submit" value="Register" />
	</form:form>
</div>