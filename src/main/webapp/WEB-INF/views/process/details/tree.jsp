<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ page session="false"%>


<div class="page-header text-center">
	<h1>
		Tree To Rules Processes <small>${dataName }</small>
	</h1>
</div>

<div class="container">
	<display:table uid="filesTable" keepStatus="false" name="processes"
		pagesize="5" class="table table-hover" requestURI="${requestURI}"
		id="process">
		<display:column title="Label">${process.getLabel().getName() }</display:column>
		<display:column title="Criterion">
		${process.getTreeToRulesSettings().getTree2RulesCriterion() }
		</display:column>
		<display:column title="Minimal Size For Split">
		${process.getTreeToRulesSettings().getMinimalSizeForSplit() }
		</display:column>
		<display:column title="Minimal Leaf Size">
		${process.getTreeToRulesSettings().getMinimalLeafSize() }
		</display:column>
		<display:column title="Minimal Gain">
		${process.getTreeToRulesSettings().getMinimalGain() }
		</display:column>
		<display:column title="Maximal Depth">
		${process.getTreeToRulesSettings().getMaximalDepth() }
		</display:column>
		<display:column title="Confidence">
		${process.getTreeToRulesSettings().getConfidence() }
		</display:column>
		<display:column title="Prepruning Alternatives">
		${process.getTreeToRulesSettings().getNumberOfPrepruningAlternatives() }
		</display:column>
		<display:column title="No Prepruning">
		${process.getTreeToRulesSettings().getNoPrepruning() }
		</display:column>
		<display:column title="No Pruning">
		${process.getTreeToRulesSettings().getNoPruning() }
		</display:column>
		<display:column>
			<a
				href="../results/view?resultsId=${process.getResults().getId()}">
				<input class="btn btn-default" type="button" value="Show Results"
				onclick="self.location.href = ../results/view?resultsId=${process.getResults().getId()}" />
			</a>
		</display:column>
	</display:table>
</div>