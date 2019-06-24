<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div>
	<h3>Edit Student</h3>
	<form:form action="/student/update" method="post"
		modelAttribute="student" onsubmit="return validateForm();" name="edit-student-form">
		<table>
			<form:input type="hidden" path="id"></form:input>
			<tr>
				<td><form:label path="name">Name</form:label> <form:input
						path="name" name="name"></form:input></td>
				<td><form:errors cssClass="text-danger" path="name"></form:errors>
				</td>
			</tr>

			<tr>
				<td><form:label path="phoneNumber">Phone</form:label> <form:input
						path="phoneNumber"></form:input></td>
			</tr>

			<tr>
				<td><form:label path="birthDay">Birth Day</form:label> <form:input
						path="birthDay"></form:input></td>
				<td><form:errors cssClass="text-danger" path="birthDay"></form:errors>
				</td>
			</tr>
			<tr>
				<td><form:label path="registeredOn">Registered On</form:label>
					<form:input path="registeredOn"></form:input></td>
				<td><form:errors cssClass="text-danger" path="registeredOn"></form:errors>
				</td>
			</tr>

			<tr>
				<td><input type="submit" value="Save" /></td>
			</tr>
		</table>

	</form:form>
	<script type="text/javascript">
		function validateForm() {
			console.log('form validation triggered');
			var name = document.forms["edit-student-form"]["name"].value;
			if(name == null || name ==""){
				alert("Name must not blank");
				return false;
				}
		}
	</script>
</div>

