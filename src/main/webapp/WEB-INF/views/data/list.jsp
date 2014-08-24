<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ page session="false"%>


<div class="page-header text-center">
	<h1>
		<spring:message code="data.h1.data.from" /> <small>${file.getName() }</small>
	</h1>
</div>

<div class="container">
	<div class="row">
		<display:table uid="dataTable" keepStatus="false" name="forms"
			pagesize="5" class="table table-hover" requestURI="${requestURI}"
			id="row">
			<spring:message code="data.name" var="name" />
			<display:column property="name" title="${name }" />
			<spring:message code="data.description" var="description" />
			<display:column property="description" title="${description }" />
			<!-- Ejecuciones con Ripper -->
			<display:column>
				<form:form method="post" action="../process/details"
					modelAttribute="dataIdForm" role="form" class="horizontal-form">
					<form:hidden path="dataId" value="${row.getId() }" />
					<form:button type="submit" name="ripper" class="btn btn-default"><spring:message code="data.show.ripper" /></form:button>
				</form:form>
			</display:column>
			<!-- Ejecuciones con Subgroup Discovery -->
			<display:column>
				<form:form method="post" action="../process/details"
					modelAttribute="dataIdForm" role="form" class="horizontal-form">
					<form:hidden path="dataId" value="${row.getId() }" />
					<form:button type="submit" name="subgroup" class="btn btn-default"><spring:message code="data.show.subgroup" /></form:button>
				</form:form>
			</display:column>
			<!-- Ejecuciones con Tree to Rules -->
			<display:column>
				<form:form method="post" action="../process/details"
					modelAttribute="dataIdForm" role="form" class="horizontal-form">
					<form:hidden path="dataId" value="${row.getId() }" />
					<form:button type="submit" name="tree" class="btn btn-default"><spring:message code="data.show.tree" /></form:button>
				</form:form>
			</display:column>
			<display:column>
				<spring:message code="data.details" var="details"/>
				<a href="../data/details?dataId=${row.id}"> <input
					class="btn btn-default" type="button" value="${details }"
					onclick="self.location.href = ../data/details?dataId=${row.id}" />
				</a>
			</display:column>
		</display:table>
	</div>
</div>
