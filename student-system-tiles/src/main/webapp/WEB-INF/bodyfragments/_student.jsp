<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div>
	<c:if test="${success ne null}">
		<div class="alert alert-success alert-dismissible fade show" role="alert">
			<span>${success}</span>
			<button class="close" type="button" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
	</c:if>
	<h3>
		Student List <a href="/student/addStudent"><i
			class="fas fa-plus-square text-success"></i></a>
	</h3>

	<table class="table table-striped table-hover">
		<th>Name</th>
		<th>Phone</th>
		<th>Birthday</th>
		<th>Registered on</th>
		<th></th>
		<th></th>
		<tbody>
			<c:forEach var="student" items="${students}">
				<tr>
					<td>${student.name}</td>
					<td>${student.phoneNumber}</td>
					<td>${student.birthDay}</td>
					<td>${student.registeredOn}</td>
					<td><a href="/student/${student.id}/edit">Edit</a></td>
					<td><a href="/student/${student.id}/delete">Delete</a></td>
				</tr>
			</c:forEach>

		</tbody>

	</table>
</div>