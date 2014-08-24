<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ page session="false"%>


<div class="page-header text-center">
	<h1>
		<spring:message code="process.h1.tree" /> <small>${dataName }</small>
	</h1>
</div>

<div class="container">
	<display:table uid="filesTable" keepStatus="false" name="processes"
		pagesize="5" class="table table-hover" requestURI="${requestURI}"
		id="process">
		<spring:message code="process.label" var="label" />
		<display:column title="${label }">${process.getLabel().getName() }</display:column>
		<spring:message code="process.tree.criterion" var="criterion" />
		<display:column title="${criterion }">
		${process.getTreeToRulesSettings().getTree2RulesCriterion() }
		</display:column>
		<spring:message code="process.tree.minimal.size.split" var="mSizeSplit" />
		<display:column title="${mSizeSplit }">
		${process.getTreeToRulesSettings().getMinimalSizeForSplit() }
		</display:column>
		<spring:message code="process.tree.minimal.leaf.size" var="mLeafSize" />
		<display:column title="${mLeafSize }">
		${process.getTreeToRulesSettings().getMinimalLeafSize() }
		</display:column>
		<spring:message code="process.tree.minimal.gain" var="minimalGain" />
		<display:column title="${minimalGain }">
		${process.getTreeToRulesSettings().getMinimalGain() }
		</display:column>
		<spring:message code="process.tree.maximal.depth" var="maximalDepth" />
		<display:column title="${maximalDepth }">
		${process.getTreeToRulesSettings().getMaximalDepth() }
		</display:column>
		<spring:message code="process.tree.confidence" var="confidence" />
		<display:column title="${confidence }">
		${process.getTreeToRulesSettings().getConfidence() }
		</display:column>
		<spring:message code="process.tree.prepruning.alternatives" var="prepruningAlternatives" />
		<display:column title="${prepruningAlternatives }">
		${process.getTreeToRulesSettings().getNumberOfPrepruningAlternatives() }
		</display:column>
		<spring:message code="process.tree.no.prepruning" var="noPrepruning" />
		<display:column title="${noPrepruning }">
		${process.getTreeToRulesSettings().getNoPrepruning() }
		</display:column>
		<spring:message code="process.tree.no.pruning" var="noPruning" />
		<display:column title="${noPruning }">
		${process.getTreeToRulesSettings().getNoPruning() }
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