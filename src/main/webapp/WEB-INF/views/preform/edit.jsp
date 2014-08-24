<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ page session="false"%>


<div class="page-header text-center">
	<h1>
		<spring:message code="preform.h1.preprocess" />
		<small><spring:message code="preform.small.filter.data" /></small>
	</h1>
</div>

<div class="container">
	<c:if test="${error == true}">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="alert alert-danger alert-dismissable">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">&times;</button>
					<strong>Error! </strong>
					<c:if test="${nameError != null }">
						<br />${nameError }
			</c:if>

					<c:if test="${descriptionError != null }">
						<br />${descriptionError }
			</c:if>

					<c:if test="${labelError != null }">
						<br />${labelError }
			</c:if>

					<c:if test="${labelError != null }">
						<br />${attributeSelectionError }
			</c:if>
				</div>
			</div>
		</div>
	</c:if>

	<div class="row">
		<div class="col-md-2">
			<a href="#aboutFilterModal" data-toggle="modal"
				data-target="#filterModal"><button type="button"
					class="btn btn-primary btn-lg">
					<spring:message code="preform.filter.examples" />
				</button> </a>
			<div class="modal fade" id="filterModal" tabindex="-1" role="dialog"
				aria-labelledby="ripperModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true"></button>
							<h4 class="modal-title" id="ripperModalLabel">
								<spring:message code="preform.filter.attributes" />
							</h4>
						</div>
						<div class="modal-body">
							<c:choose>
								<c:when test="${hasNominal && !hasNumerical }">
									<%@include file="includes/nominalFilter.jsp"%>
								</c:when>
								<c:when test="${!hasNominal && hasNumerical }">
									<%@include file="includes/numericalFilter.jsp"%>
								</c:when>
								<c:otherwise>
									<%@include file="includes/nominalFilter.jsp"%>
									<%@include file="includes/numericalFilter.jsp"%>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">
								<spring:message code="preform.go.back" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<form:form method="post" action="../data/save" modelAttribute="form"
			role="form" class="horizontal-form">
			<form:input path="dataId" value="${dataId}" hidden="true" />
			<div class="col-md-4 col-md-offset-3">
				<a href="#aboutExportModal" data-toggle="modal"
					data-target="#exportModal"><button type="button"
						class="btn btn-primary btn-lg">
						<spring:message code="preform.export.data" />
					</button> </a>
				<div class="modal fade" id="exportModal" tabindex="-1" role="dialog"
					aria-labelledby="ripperModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true"></button>
								<h4 class="modal-title" id="ripperModalLabel">
									<spring:message code="preform.select.label.attributes" />
								</h4>
							</div>
							<div class="modal-body">
								<div class="form-group">
									<div class="row">
										<label for="attributeSelectionSelect"
											class="col-md-4 col-md-offset-2 control-label"><spring:message
												code="preform.attribute.selection" /></label>
										<div class="col-md-5">
											<form:select class="form-control"
												id="attributeSelectionSelect" multiple="true"
												path="exportAttributeSelection">
												<c:forEach items="${attributes }" var="attribute">
													<form:option value="${attribute.getName() }" />
												</c:forEach>
											</form:select>
											<form:errors path="exportAttributeSelection" />
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="row">
										<label for="exportFormatSelect"
											class="col-md-4 col-md-offset-2 control-label"><spring:message
												code="preform.format" /></label>
										<div class="col-md-5">
											<form:select class="form-control" id="exportFormatSelect"
												multiple="true" path="exportFormat">
												<form:option value="xlsx" />
												<form:option value="xls" />
												<form:option value="arff" />
												<form:option value="xrff" />
												<form:option value="csv" />
											</form:select>
											<form:errors path="exportFormat" />
										</div>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">
									<spring:message code="preform.go.back" />
								</button>
								<form:button type="submit" name="export" class="btn btn-default">
									<spring:message code="preform.export.data" />
								</form:button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-3">
				<a href="#aboutProcessModal" data-toggle="modal"
					data-target="#processModal"><button type="button"
						class="btn btn-success btn-lg">
						<spring:message code="preform.process.data" />
					</button> </a>
				<div class="modal fade" id="processModal" tabindex="-1"
					role="dialog" aria-labelledby="ripperModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true"></button>
								<h4 class="modal-title" id="ripperModalLabel">
									<spring:message code="preform.select.label.attributes" />
								</h4>
							</div>
							<div class="modal-body">
								<c:if test="${error == true}">
									<div class="row">
										<div class="col-md-8 col-md-offset-2">
											<div class="alert alert-danger alert-dismissable">
												<button type="button" class="close" data-dismiss="alert"
													aria-hidden="true">&times;</button>
												<strong>Error! </strong>
												<c:if test="${nameError != null }">
													<br />${nameError }
											</c:if>

												<c:if test="${descriptionError != null }">
													<br />${descriptionError }
											</c:if>

												<c:if test="${labelError != null }">
													<br />${labelError }
											</c:if>

												<c:if test="${labelError != null }">
													<br />${attributeSelectionError }
											</c:if>
											</div>
										</div>
									</div>
								</c:if>
								<div class="row">
									<div class="form-group">
										<label for="attributeSelectionSelect"
											class="col-md-4 col-md-offset-1 control-label"><spring:message
												code="preform.name" /></label>
										<div class="col-md-5">
											<form:input type="text" path="name" class="form-control" />
											<form:errors path="name" />
										</div>
									</div>
								</div>
								<br />
								<div class="row">
									<div class="form-group">
										<label for="attributeSelectionSelect"
											class="col-md-4 col-md-offset-1 control-label"><spring:message
												code="preform.description" /></label>
										<div class="col-md-5">
											<form:textarea path="description" class="form-control" />
											<form:errors path="description" />
										</div>
									</div>
								</div>
								<br />
								<div class="row">
									<div class="form-group">
										<label for="labelSelect"
											class="col-sm-4 col-sm-offset-1 control-label"><spring:message
												code="preform.label" /></label>
										<div class="col-sm-4">
											<form:select class="form-control" id="labelSelect"
												path="label">
												<c:forEach items="${attributes }" var="attribute">
													<form:option value="${attribute.getName() }" />
												</c:forEach>
											</form:select>
											<form:errors path="label" />
										</div>
									</div>
								</div>
								<br />
								<div class="row">
									<div class="form-group">
										<label for="attributeSelectionSelect"
											class="col-sm-4 col-sm-offset-1 control-label"><spring:message
												code="preform.attribute.selection" /></label>
										<div class="col-sm-4">
											<form:select class="form-control"
												id="attributeSelectionSelect" multiple="true"
												path="processAttributeSelection">
												<c:forEach items="${attributes }" var="attribute">
													<form:option value="${attribute.getName() }" />
												</c:forEach>
											</form:select>

										</div>
									</div>
								</div>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">
									<spring:message code="preform.go.back" />
								</button>
								<form:button type="submit" name="process"
									class="btn btn-default">
									<spring:message code="preform.process.data" />
								</form:button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form:form>
	</div>

	<br />
	<div class="row">
		<display:table uid="examplesTable" keepStatus="false" name="examples"
			pagesize="10" class="table table-hover"
			requestURI="${requestURI}?dataId=${dataId }" id="row">
			<display:column title="#">
				<c:out value="${row_rowNum}" />
			</display:column>
			<c:forEach items="${attributes }" var="attribute">
				<display:column title="${attribute.getName()}">
				${row.getValueAsString(attribute) } 
					</display:column>
			</c:forEach>
			<spring:message code="preform.delete" var="delete" />
			<display:column title="${delete }">
				<a href="deleterow?dataId=${dataId }&rowId=${row_rowNum-1 }"> <input
					class="btn btn-default" type="button" value="${delete }"
					onclick="self.location.href = deleterow?dataId=${dataId }&rowId=${row_rowNum-1 }" />
				</a>
			</display:column>
		</display:table>
	</div>
</div>