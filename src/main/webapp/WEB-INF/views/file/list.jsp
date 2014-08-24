<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ page session="false"%>


<div class="page-header text-center">
	<h1>
		<spring:message code="file.h1.my.files" /> <small><spring:message code="file.small.upload" /></small>
	</h1>
</div>

<div class="container">
	<c:if test="${successMessage != null}">
		<div class="row">
			<div
				class="alert alert-success alert-dismissable col-md-4 col-md-offset-4">
				<button type="button" class="close" data-dismiss="alert"
					aria-hidden="true">&times;</button>
				<strong><spring:message code="file.success" /> </strong><br /><spring:message code="file.the.file" /> ${successMessage } <spring:message code="file.successfully.uploaded" />
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
				<form:button type="submit" class="btn btn-default"><spring:message code="file.view.data" /></form:button>
			</form:form>
		</display:column>
		<display:column>
			<spring:message code="file.preprocess" var="preprocess" />
			<a href="../preform/create?fileId=${row.id}"> <input
				class="btn btn-default" type="button" value="${preprocess }"
				onclick="self.location.href = ../data/create?fileId=${row.id}" />
			</a>
		</display:column>
		<display:column>
			<spring:message code="file.delete" var="delete" />
			<a href="delete?fileId=${row.id}"> <input class="btn btn-default"
				type="button" value="${delete }"
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
				<h3><spring:message code="file.data.upload" /></h3>
				<p><spring:message code="file.data.upload.description" /></p>
			</div>
			<div class="row">
				<input type="file" name="file" class="filestyle">
			</div>
			<br />
			<div class="row">
				<div class="col-md-12 text-center">
					<a href="#uploadModal" data-toggle="modal"
						data-target="#uploadModal"><button type="button"
							class="btn btn-primary btn-lg"><spring:message code="file.upload" /></button> </a>
					<div class="modal fade" id="uploadModal" tabindex="-1"
						role="dialog" aria-labelledby="uploadModalLabel"
						aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true"></button>
									<h4 class="modal-title" id="uploadModalLabel"><spring:message code="file.format.confirmation" /></h4>
								</div>
								<div class="modal-body">
									<div class="row"></div>
									<p><spring:message code="file.format.question" /></p>
									<br />
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-danger"
										data-dismiss="modal">No</button>
									<button type="submit" class="btn btn-success"
										value="Upload"><spring:message code="file.yes" /></button>
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