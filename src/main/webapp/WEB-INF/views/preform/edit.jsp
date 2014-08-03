<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ page session="false"%>


<div class="page-header text-center">
	<h1>
		Preprocess <small>Filter your data</small>
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
					class="btn btn-primary btn-lg">Filter Examples</button> </a>
			<div class="modal fade" id="filterModal" tabindex="-1" role="dialog"
				aria-labelledby="ripperModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true"></button>
							<h4 class="modal-title" id="ripperModalLabel">Filter
								Attributes</h4>
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
								data-dismiss="modal">Go Back</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<form:form method="post" action="../data/save" modelAttribute="form"
			role="form" class="horizontal-form">
			<form:input path="dataId" value="${dataId}" hidden="true" />
			<div class="col-md-2 col-md-offset-6">
				<a href="#aboutExportModal" data-toggle="modal"
					data-target="#exportModal"><button type="button"
						class="btn btn-primary btn-lg">Export Data</button> </a>
				<div class="modal fade" id="exportModal" tabindex="-1" role="dialog"
					aria-labelledby="ripperModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true"></button>
								<h4 class="modal-title" id="ripperModalLabel">Select label
									and attributes</h4>
							</div>
							<div class="modal-body">
								<div class="form-group">
									<div class="row">
										<label for="attributeSelectionSelect"
											class="col-md-4 col-md-offset-2 control-label">Attribute
											Selection</label>
										<div class="col-md-5">
											<form:select class="form-control"
												id="attributeSelectionSelect" multiple="true"
												path="attributeSelection">
												<form:options items="${attributes }" />
											</form:select>
											<form:errors path="attributeSelection" />
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="row">
										<label for="exportFormatSelect"
											class="col-md-4 col-md-offset-2 control-label">Format</label>
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
									data-dismiss="modal">Go Back</button>
								<form:button type="submit" name="export" class="btn btn-default">Export Data</form:button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-2">
				<a href="#aboutProcessModal" data-toggle="modal"
					data-target="#processModal"><button type="button"
						class="btn btn-success btn-lg">Process Data</button> </a>
				<div class="modal fade" id="processModal" tabindex="-1"
					role="dialog" aria-labelledby="ripperModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true"></button>
								<h4 class="modal-title" id="ripperModalLabel">Select label
									and attributes</h4>
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
											class="col-md-4 col-md-offset-2 control-label">Name</label>
										<div class="col-md-5">
											<form:input type="text" path="name" class="form-control" />
											<form:errors path="name" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="form-group">
										<label for="attributeSelectionSelect"
											class="col-md-4 col-md-offset-2 control-label">Description</label>
										<div class="col-md-5">
											<form:input type="text" path="description"
												class="form-control" />
											<form:errors path="description" />
										</div>
									</div>
								</div>
								<br />
								<div class="row">
									<div class="form-group">
										<label for="labelSelect"
											class="col-sm-4 col-sm-offset-1 control-label">Label</label>
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
											class="col-sm-4 col-sm-offset-1 control-label">Attribute
											Selection</label>
										<div class="col-sm-4">
											<form:select class="form-control"
												id="attributeSelectionSelect" multiple="true"
												path="attributeSelection">
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
									data-dismiss="modal">Go Back</button>
								<form:button type="submit" name="process"
									class="btn btn-default">Process Data</form:button>
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
			<display:column title="Delete">
				<a href="deleterow?dataId=${dataId }&rowId=${row_rowNum-1 }"> <input
					class="btn btn-default" type="button" value="Delete"
					onclick="self.location.href = deleterow?dataId=${dataId }&rowId=${row_rowNum-1 }" />
				</a>
			</display:column>
		</display:table>
	</div>
</div>