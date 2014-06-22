<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>


<div class="page-header text-center">
	<h1>
		<spring:message code="preprocessing.header" />
		<small>Select your data</small>
	</h1>
</div>

<div class="container">
	<c:choose>
		<c:when test="${uploaded == false}">
			<form:form method="post" enctype="multipart/form-data"
				modelAttribute="uploadedFile" action="upload">

				<div class="row">
					<h3>Data upload</h3>
				</div>
				<div class="row">
					<input type="file" name="file" class="filestyle">
					<form:errors path="file" />
				</div>
				<br />
				<div class="row">
					<button class="btn btn-default" type="submit" value="Upload">Upload</button>
				</div>
			</form:form>
		</c:when>
		<c:when test="${uploaded == true }">
			<br />
			<%@include file="includes/viewData.jsp"%>
		</c:when>
	</c:choose>
</div>