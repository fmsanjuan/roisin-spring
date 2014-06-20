<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>

<style>
.table-responsive {
	height:180px;
}
</style>

<h1>
	<spring:message code="preprocessing.header" />
</h1>

<div class="container">
	<c:choose>
		<c:when test="${uploaded == false}">
			<form:form method="post" enctype="multipart/form-data"
				modelAttribute="uploadedFile" action="upload">
				<table>
					<tr>
						<td>Upload File:</td>
						<td><input type="file" name="file" /></td>
						<td style="color: red; font-style: italic;"><form:errors
								path="file" /></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Upload" /></td>
						<td></td>
					</tr>
				</table>
			</form:form>
		</c:when>
		<c:when test="${uploaded == true }">
			<h2>Fichero cargado de forma correcta</h2>
			<p>Total ${exampleSize}</p>
		</c:when>
	</c:choose>
	
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>

	<!-- Creación de tabla de prueba para mostrar la información cargada en la aplicación -->
	<div class="container">
		<div class="row">
			<div class="table-responsive">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>#</th>
							<th>Table heading</th>
							<th>Table heading</th>
							<th>Table heading</th>
							<th>Table heading</th>
							<th>Table heading</th>
							<th>Table heading</th>
						</tr>
					</thead>
					<tbody id="myTable">
						<tr>
							<td>1</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
						</tr>
						<tr>
							<td>2</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
						</tr>
						<tr>
							<td>3</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
						</tr>
						<tr>
							<td>4</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
						</tr>
						<tr class="success">
							<td>5</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
						</tr>
						<tr>
							<td>6</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
						</tr>
						<tr>
							<td>7</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
						</tr>
						<tr>
							<td>8</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
						</tr>
						<tr>
							<td>9</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
						</tr>
						<tr>
							<td>10</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
							<td>Table cell</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-md-12 text-center">
				<ul class="pagination pagination-lg pager" id="myPager"></ul>
			</div>
		</div>
	</div>




	<!-- Aquí acaba la creación de la tabla de prueba -->
</div>