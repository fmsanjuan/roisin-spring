<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ page session="false"%>


<div class="page-header text-center">
	<h1>
		<spring:message code="converter.h1" /> <small><spring:message code="converter.small" /></small>
	</h1>
</div>

<div class="container">
	<c:choose>
		<c:when test="${form==null }">
			<div class="row">
				<form:form method="post" enctype="multipart/form-data"
					modelAttribute="uploadedFile" action="upload"
					class="horizontal-form" role="form">
					<div class="col-md-6 col-md-offset-3">
						<c:if test="${error == true}">
							<div class="row">
								<div class="alert alert-danger alert-dismissable">
									<button type="button" class="close" data-dismiss="alert"
										aria-hidden="true">&times;</button>
									<strong><spring:message code="converter.error" /> </strong>
									<form:errors path="file" />
									<form:errors path="outputFormat" />
								</div>
							</div>
						</c:if>
						<div class="row">
							<h3><spring:message code="converter.h1" /></h3>
							<p><spring:message code="converter.choose.file" /></p>
						</div>
						<div class="row">
							<input type="file" name="file" class="filestyle">
						</div>
						<br /> <br />
						<div class="row">
							<div class="form-group">
								<label for="outputFormatId"
									class="col-md-3 col-md-offset-3 control-label"><spring:message code="converter.output.format" /></label>
								<div class="col-md-3">
									<form:select path="outputFormat" class="form-control"
										id="outputFormatId">
										<form:option value="xlsx">xlsx</form:option>
										<form:option value="xlsx">xls</form:option>
										<form:option value="csv">CSV</form:option>
										<form:option value="arff">Arff</form:option>
										<form:option value="xrff">Xrff</form:option>
									</form:select>
								</div>
							</div>
						</div>
						<br /> <br />
						<div class="row">
							<div class="col-md-12 text-center">
								<button class="btn btn-primary btn-lg" type="submit"
									value="Convert File"><spring:message code="converter.convert.file" /></button>
							</div>
						</div>
					</div>
				</form:form>
			</div>
		</c:when>
		<c:when test="${form!=null }">
			<div class="col-md-6 col-md-offset-3">
				<h3 class="text-center"><spring:message code="converter.conversion.finished" /></h3>
				<br />
				<div class="row">
					<div class="text-center">
						<form:form method="post" action="download" modelAttribute="form"
							role="form" class="horizontal-form">
							<form:hidden path="hash" />
							<form:hidden path="outputFormat" />
							<form:hidden path="fileName" />
							<button class="btn btn-success btn-lg" type="submit"
								value="Download"><spring:message code="converter.download.file" /></button>
						</form:form>
					</div>
				</div>
			</div>
		</c:when>
	</c:choose>
</div>