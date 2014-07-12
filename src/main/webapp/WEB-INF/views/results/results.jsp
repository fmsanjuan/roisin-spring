<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>

<div class="page-header text-center">
	<h1>
		Results <small>Validate the rules</small>
	</h1>
</div>

<div class="container">
	<div class="row">
		<div class="table-responsive">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>#</th>
						<th>Premise</th>
						<th>Conclusion</th>
						<th>Precision</th>
						<th>Support</th>
						<th>TPR</th>
						<th>FPR</th>
						<th>TP</th>
						<th>FP</th>
						<th>FN</th>
						<th>TN</th>
						<th>AUC</th>
					</tr>
				</thead>
				<tbody id="myTable">
					<c:forEach items="${results.getRoisinRules() }" var="rule"
						varStatus="loop">
						<tr>
							<td>${loop.index+1 }</td>
							<td>${rule.getPremise() }</td>
							<td>${rule.getConclusion() }</td>
							<td>${rule.getPrecision() }</td>
							<td>${rule.getSupport() }</td>
							<td>${rule.getTruePositiveRate() }</td>
							<td>${rule.getFalsePositiveRate() }</td>
							<td>${rule.getTruePositives() }</td>
							<td>${rule.getFalsePositives() }</td>
							<td>${rule.getFalseNegatives() }</td>
							<td>${rule.getTrueNegatives() }</td>
							<td>${rule.getAuc() }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<img alt="AUC Chart" src="${chart }">
		</div>
	</div>
	<div class="col-md-6">
		<form:form method="post" class="form-horizontal" action="../results/export"
			role="form" modelAttribute="form">
			<%@include file="/WEB-INF/views/includes/commonHidden.jsp"%>
			<%@include file="/WEB-INF/views/includes/ripperHidden.jsp"%>
			<%@include file="/WEB-INF/views/includes/subgroupHidden.jsp"%>
			<%@include file="/WEB-INF/views/includes/tree2rulesHidden.jsp"%>
			<form:button type="submit">Get Excel</form:button>
		</form:form>
	</div>
</div>