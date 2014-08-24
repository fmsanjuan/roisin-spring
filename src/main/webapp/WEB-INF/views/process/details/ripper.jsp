<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ page session="false"%>


<div class="page-header text-center">
	<h1>
		<spring:message code="process.h1.ripper" /> <small>${dataName }</small>
	</h1>
</div>

<div class="container">
	<display:table uid="filesTable" keepStatus="false" name="processes"
		pagesize="5" class="table table-hover" requestURI="${requestURI}"
		id="process">
		<spring:message code="process.label" var="label" />
		<display:column title="${label }">${process.getLabel().getName() }</display:column>
		<spring:message code="process.ripper.criterion" var="criterion" />
		<display:column title="${criterion }">
		${process.getRipperSettings().getRipperCriterion() }
		</display:column>
		<spring:message code="process.ripper.sample.ratio" var="sampleRatio" />
		<display:column title="${sampleRatio }">
		${process.getRipperSettings().getSampleRatio() }
		</display:column>
		<spring:message code="process.ripper.pureness" var="pureness" />
		<display:column title="${pureness }">
		${process.getRipperSettings().getPureness() }
		</display:column>
		<spring:message code="process.ripper.minimal.prune.benefit" var="minimalPB" />
		<display:column title="${minimalPB }">
		${process.getRipperSettings().getMinimalPruneBenefit() }
		</display:column>
		<display:column>
			<spring:message code="process.show.results" var="showResults" />
			<a
				href="../results/view?resultsId=${process.getResults().getId()}">
				<input class="btn btn-default" type="button" value="${showResults }"
				onclick="self.location.href = ../results/view?resultsId=${process.getResults().getId()}" />
			</a>
		</display:column>
	</display:table>
</div>