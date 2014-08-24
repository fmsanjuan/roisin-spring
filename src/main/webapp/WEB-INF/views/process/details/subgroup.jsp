<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ page session="false"%>


<div class="page-header text-center">
	<h1>
		<spring:message code="process.h1.subgroup" /> <small>${dataName }</small>
	</h1>
</div>

<div class="container">
	<display:table uid="filesTable" keepStatus="false" name="processes"
		pagesize="5" class="table table-hover" requestURI="${requestURI}"
		id="process">
		<spring:message code="process.h1.subgroup" var="label" />
		<display:column title="${label }">${process.getLabel().getName() }</display:column>
		<spring:message code="process.subgroup.mode" var="mode" />
		<display:column title="${mode }">
		${process.getSubgroupSettings().getMode() }
		</display:column>
		<spring:message code="process.subgroup.utility.function" var="utilityFunction" />
		<display:column title="${utilityFunction }">
		${process.getSubgroupSettings().getUtilityFunction() }
		</display:column>
		<spring:message code="process.subgroup.min.utility" var="minUtility" />
		<display:column title="${minUtility }">
		${process.getSubgroupSettings().getMinUtility() }
		</display:column>
		<spring:message code="process.subgroup.k.best.rules" var="kBestRules" />
		<display:column title="${kBestRules }">
		${process.getSubgroupSettings().getkBestRules() }
		</display:column>
		<spring:message code="process.subgroup.rule.generation" var="ruleGeneration" />
		<display:column title="${ruleGeneration }">
		${process.getSubgroupSettings().getRuleGeneration() }
		</display:column>
		<spring:message code="process.subgroup.max.depth" var="maxDepth" />
		<display:column title="${maxDepth }">
		${process.getSubgroupSettings().getMaxDepth() }
		</display:column>
		<spring:message code="process.subgroup.min.coverage" var="minCoverage" />
		<display:column title="${minCoverage }">
		${process.getSubgroupSettings().getMinCoverage() }
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