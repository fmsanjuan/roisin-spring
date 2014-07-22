<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ page session="false"%>


<div class="page-header text-center">
	<h1>
		<spring:message code="preprocessing.header" />
		<small>Select your data</small>
	</h1>
</div>

<display:table uid="dietListTable" keepStatus="false" name="examples"
	pagesize="5" class="table table-hover" requestURI="${requestURI}"
	id="row">
	<display:column property="filePath" title="Path" sortable="true" />
	<display:column property="processes.size" title="Process"
		sortable="true" />
</display:table>

<div class="container">
	<form:form method="post" enctype="multipart/form-data"
		modelAttribute="uploadedFile" action="upload">
		<div class="col-md-6 col-md-offset-3">
			<c:if test="${error == true}">
				<div class="row">
					<div class="alert alert-danger alert-dismissable">
						<button type="button" class="close" data-dismiss="alert"
							aria-hidden="true">&times;</button>
						<strong>Error! </strong>
						<form:errors path="file" />
					</div>
				</div>
			</c:if>
			<div class="row">
				<h3>Data upload</h3>
				<p>First, you need to upload a file with the information you
					want to process.</p>
			</div>
			<div class="row">
				<input type="file" name="file" class="filestyle">
			</div>
			<br />
			<div class="row">
				<div class="col-md-12 text-center">
					<button class="btn btn-primary" type="submit" value="Upload">Upload</button>
				</div>
			</div>
		</div>
	</form:form>
	<br />
</div>