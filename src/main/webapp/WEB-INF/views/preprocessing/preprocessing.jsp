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
			<h3>
				<span class="label label-success">Success</span>
			</h3>
			<br />

			<ul class="nav nav-tabs" id="myTab">
				<li class="active"><a href="#view" data-toggle="tab">View
						Data</a></li>
				<li><a href="#deleteRows" data-toggle="tab">Delete Rows</a></li>
				<li><a href="#deleteAttributes" data-toggle="tab">Delete
						Attributes</a></li>
				<li><a href="#options" data-toggle="tab">Options</a></li>
			</ul>

			<div class="tab-content">
				<div class="tab-pane active" id="view">
					<br />
					<%@include file="includes/viewData.jsp"%>
				</div>
				<div class="tab-pane" id="deleteRows">
					<br />
					<%@include file="includes/deleteRows.jsp"%>
				</div>
				<div class="tab-pane" id="deleteAttributes">
					<br />
					<%@include file="includes/deleteAttributes.jsp"%>
				</div>
				<div class="tab-pane" id="options">
					<br />
					<%@include file="includes/options.jsp"%>
				</div>
			</div>

			<script>
				$(function() {
					$('#myTab a:first').tab('show')
				})
			</script>
		</c:when>
	</c:choose>
</div>