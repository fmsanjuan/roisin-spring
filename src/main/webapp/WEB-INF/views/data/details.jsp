<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ page session="false"%>


<div class="page-header text-center">
	<h1>
		<spring:message code="data.h1.data.details" /> <small> <spring:message code="data.small.results" /></small>
	</h1>
</div>
<div class="container">
	<div class="row">
		<div class="col-md-2 col-md-offset-10">
			<a href="#aboutProcessModal" data-toggle="modal"
				data-target="#processModal"><button type="button"
					class="btn btn-success btn-lg"><spring:message code="data.process.data" /></button> </a>
			<div class="modal fade" id="processModal" tabindex="-1" role="dialog"
				aria-labelledby="ripperModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal"
								aria-hidden="true"></button>
							<h4 class="modal-title" id="ripperModalLabel"><spring:message code="data.select.label.attributes" /></h4>
						</div>
						<form:form method="post" action="../process/create"
							modelAttribute="form" role="form" class="horizontal-form">
							<form:hidden path="dataId" />
							<div class="modal-body">
								<div class="row">
									<div class="form-group">
										<label for="labelSelect"
											class="col-sm-4 col-sm-offset-1 control-label"><spring:message code="data.label" /></label>
										<div class="col-sm-4">
											<form:select class="form-control" id="labelSelect"
												path="label">
												<c:forEach items="${attributes }" var="attribute">
													<form:option value="${attribute.getName() }" />
												</c:forEach>
											</form:select>
										</div>
									</div>
								</div>
								<br />
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal"><spring:message code="data.go.back" /></button>
								<form:button type="submit" name="process"
									class="btn btn-default"><spring:message code="data.process.data" /></form:button>
							</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<display:table uid="examplesTable" keepStatus="false" name="examples"
			pagesize="10" class="table table-hover" requestURI="${requestURI}"
			id="row">
			<display:column title="#">
				<c:out value="${row_rowNum}" />
			</display:column>
			<c:forEach items="${attributes }" var="attribute">
				<display:column title="${attribute.getName()}">
				${row.getValueAsString(attribute) } 
					</display:column>
			</c:forEach>
		</display:table>
	</div>
</div>