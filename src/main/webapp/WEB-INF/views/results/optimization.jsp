<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ page session="false"%>

<div class="page-header text-center">
	<h1>
		<spring:message code="results.h1.results" /> <small><spring:message code="results.small.auc" /></small>
	</h1>
</div>

<div class="container">
	<div class="row">
		<display:table uid="ruleTable" keepStatus="false" name="rules"
			pagesize="10" class="table table-hover" requestURI="${requestURI}"
			id="row">
			<display:column title="#">
				<c:out value="${row_rowNum}" />
			</display:column>
			<spring:message code="results.premise" var="premise" />
			<display:column property="premise" title="${premise }" />
			<spring:message code="results.conclusion" var="conclusion" />
			<display:column property="conclusion" title="${conclusion }" />
			<spring:message code="results.precision" var="precision" />
			<display:column property="rulePrecision" title="${precision }" />
			<spring:message code="results.support" var="support" />
			<display:column property="support" title="${support }" />
			<display:column property="tpr" title="TPR" />
			<display:column property="fpr" title="FPR" />
			<display:column property="tp" title="TP" />
			<display:column property="tn" title="TN" />
			<display:column property="fp" title="FP" />
			<display:column property="fn" title="FN" />
			<display:column title="AUC">
				<a href="../rule/view?ruleId=${row.getId() }">${row.getAuc() }</a>
			</display:column>

		</display:table>
	</div>
	<h2><spring:message code="results.removed.rules" /></h2>
	<div class="row">
		<display:table uid="ruleTable" keepStatus="false" name="removedRules"
			pagesize="10" class="table table-hover" requestURI="${requestURI}"
			id="row">
			<display:column title="#">
				<c:out value="${row_rowNum}" />
			</display:column>
			<spring:message code="results.premise" var="premise" />
			<display:column property="premise" title="${premise }" />
			<spring:message code="results.conclusion" var="conclusion" />
			<display:column property="conclusion" title="${conclusion }" />
			<spring:message code="results.precision" var="precision" />
			<display:column property="rulePrecision" title="${precision }" />
			<spring:message code="results.support" var="support" />
			<display:column property="support" title="${support }" />
			<display:column property="tpr" title="TPR" />
			<display:column property="fpr" title="FPR" />
			<display:column property="tp" title="TP" />
			<display:column property="tn" title="TN" />
			<display:column property="fp" title="FP" />
			<display:column property="fn" title="FN" />
			<display:column title="AUC">
				<a href="../rule/view?ruleId=${row.getId() }">${row.getAuc() }</a>
			</display:column>

		</display:table>
	</div>
	<div class="row">
		<div class="col-md-6 col-md-offset-2">
			<img alt="AUC Chart" src="${chart }" />
		</div>
		<div class="col-md-3">
			<br /> <br /> <br /> <br /> <br /> <br /><br /><br /> 
			<form:form method="post" class="form-horizontal"
				action="../results/exportoptimization" role="form" modelAttribute="results">
				<form:hidden path="id"/>
				<form:hidden path="version"/>
				<form:hidden path="auc" />
				<form:hidden path="process" />
				<form:button class="btn btn-primary btn-lg" type="submit"><spring:message code="results.export.optimization" /></form:button>
			</form:form>
			<br /> <a href="${chart }"><button
					class="btn btn-primary btn-lg"><spring:message code="results.download.chart" /></button></a>
		</div>
	</div>
</div>
