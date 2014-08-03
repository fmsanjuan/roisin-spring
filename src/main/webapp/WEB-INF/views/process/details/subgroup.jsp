<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ page session="false"%>


<div class="page-header text-center">
	<h1>
		Subgroup Discovery Processes <small>${dataName }</small>
	</h1>
</div>

<div class="container">
	<display:table uid="filesTable" keepStatus="false" name="processes"
		pagesize="5" class="table table-hover" requestURI="${requestURI}"
		id="process">
		<display:column title="Label">${process.getLabel().getName() }</display:column>
		<display:column title="Mode">
		${process.getSubgroupSettings().getMode() }
		</display:column>
		<display:column title="Utility Function">
		${process.getSubgroupSettings().getUtilityFunction() }
		</display:column>
		<display:column title="Min Utility">
		${process.getSubgroupSettings().getMinUtility() }
		</display:column>
		<display:column title="K Best Rules">
		${process.getSubgroupSettings().getkBestRules() }
		</display:column>
		<display:column title="Rule Generation">
		${process.getSubgroupSettings().getRuleGeneration() }
		</display:column>
		<display:column title="Max Depth">
		${process.getSubgroupSettings().getMaxDepth() }
		</display:column>
		<display:column title="Min Coverage">
		${process.getSubgroupSettings().getMinCoverage() }
		</display:column>
	</display:table>

</div>