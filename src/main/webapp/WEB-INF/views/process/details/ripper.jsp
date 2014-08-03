<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ page session="false"%>


<div class="page-header text-center">
	<h1>
		Ripper Processes <small>${dataName }</small>
	</h1>
</div>

<div class="container">
	<display:table uid="filesTable" keepStatus="false" name="processes"
		pagesize="5" class="table table-hover" requestURI="${requestURI}"
		id="process">
		<display:column title="Label">${process.getLabel().getName() }</display:column>
		<display:column title="Ripper Criterion">
		${process.getRipperSettings().getRipperCriterion() }
		</display:column>
		<display:column title="Sample Ratio">
		${process.getRipperSettings().getSampleRatio() }
		</display:column>
		<display:column title="Pureness">
		${process.getRipperSettings().getPureness() }
		</display:column>
		<display:column title="Minimal Prune Benefit">
		${process.getRipperSettings().getMinimalPruneBenefit() }
		</display:column>
	</display:table>

</div>