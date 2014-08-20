<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ page session="false"%>


<div class="page-header text-center">
	<h1>
		My Files <small>Upload your data</small>
	</h1>
</div>

<div class="container">
	<c:if test="${successMessage != null}">
		<div class="row">
			<div
				class="alert alert-success alert-dismissable col-md-4 col-md-offset-4">
				<button type="button" class="close" data-dismiss="alert"
					aria-hidden="true">&times;</button>
				<strong>Success! </strong><br /> ${successMessage }
			</div>
		</div>
	</c:if>
	<display:table uid="filesTable" keepStatus="false" name="files"
		pagesize="5" class="table table-hover" requestURI="${requestURI}"
		id="row">
		<display:column property="name" title="Name" />
		<display:column>
			<form:form method="post" action="../data/view" modelAttribute="form"
				role="form" class="horizontal-form">
				<form:hidden path="fileId" value="${row.getId() }" />
				<form:button type="submit" class="btn btn-default">View Data</form:button>
			</form:form>
		</display:column>
		<display:column>
			<a href="../preform/create?fileId=${row.id}"> <input
				class="btn btn-default" type="button" value="Preprocess"
				onclick="self.location.href = ../data/create?fileId=${row.id}" />
			</a>
		</display:column>
		<display:column>
			<a href="delete?fileId=${row.id}"> <input class="btn btn-default"
				type="button" value="Delete"
				onclick="self.location.href = delete?dietId=${row.id}" />
			</a>
		</display:column>
	</display:table>

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
					<a href="#uploadModal" data-toggle="modal"
						data-target="#uploadModal"><button type="button"
							class="btn btn-primary btn-lg">Upload</button> </a>
					<div class="modal fade" id="uploadModal" tabindex="-1"
						role="dialog" aria-labelledby="uploadModalLabel"
						aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true"></button>
									<h4 class="modal-title" id="uploadModalLabel">File format
										confirmation</h4>
								</div>
								<div class="modal-body">
									<div class="row"></div>
									<p>Does your file have the attribute names in the first
										row?</p>
									<br />
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-danger"
										data-dismiss="modal">No</button>
									<button type="submit" class="btn btn-success"
										value="Upload">Yes</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form:form>
	<br />
</div>