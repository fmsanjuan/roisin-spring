<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ page session="false"%>

<div class="page-header text-center">
	<h1>
		Results <small>Validate the rules</small>
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
			<display:column property="premise" title="Premise" />
			<display:column property="conclusion" title="Conclusion" />
			<display:column property="rulePrecision" title="Precision" />
			<display:column property="support" title="Support" />
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
			<br /> <br /> <br /> <br /> <br /> <br /> <br /> <br />
			<form:form method="post" class="form-horizontal"
				action="../results/export" role="form" modelAttribute="results">
				<form:hidden path="id"/>
				<form:hidden path="version"/>
				<form:hidden path="auc" />
				<form:hidden path="process" />
<%-- 				<form:hidden path="rules" /> --%>
				<form:button class="btn btn-primary btn-lg" type="submit">Download Results</form:button>
			</form:form><br />
			<%-- 			<a href="../results/export?resultsId=${resultsId }"> <input --%>
			<!-- 				class="btn btn-primary btn-lg" type="button" -->
			<!-- 				value="Download Results" -->
			<%-- 				onclick="self.location.href = ../results/export?resultsId=${resultsId }" /></a> --%>
			<br />
			<form:form method="post" class="form-horizontal"
				action="../results/optimization" role="form" modelAttribute="results">
				<form:hidden path="id"/>
				<form:hidden path="version"/>
				<form:hidden path="auc" />
				<form:hidden path="process" />
<%-- 				<form:hidden path="rules" /> --%>
				<form:button class="btn btn-primary btn-lg" type="submit">AUC Optimization</form:button>
			</form:form>
			<%-- 			 <a href="../results/export?resultsId=${resultsId }"> <input --%>
			<!-- 				class="btn btn-primary btn-lg" type="button" -->
			<!-- 				value="Download Results" -->
			<%-- 				onclick="self.location.href = ../results/export?resultsId=${resultsId }" /> </a> --%>
			<br /> <br /> <a href="${chart }"><button
					class="btn btn-primary btn-lg">Download Chart</button></a>
		</div>
	</div>
</div>
