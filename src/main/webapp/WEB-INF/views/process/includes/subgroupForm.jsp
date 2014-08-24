<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form method="post" class="form-horizontal"
	action="../process/subgroup" role="form"
	modelAttribute="subgroupSettings">

	<form:hidden path="process" />

	<div class="col-xs-8 col-sm-8 col-md-12 col-lg-12">
		<h3 class="text-center">Subgroup Discovery</h3>
		<br /> <span class="description">Lorem ipsum dolor sit amet,
			consectetuer adipiscing elit. Aenean commodo ligula eget dolor.
			Aenean massa. Cum sociis natoque penatibus et magnis dis parturient
			montes, nascetur ridiculus mus. Donec quam felis, ultricies nec,
			pellentesque eu, pretium quis, sem.</span> <input type="radio"
			name="product" value="desktop" class="non-visible">
	</div>
	<div class="clear"></div>
	<br />
	<div class="row text-center">
		<c:choose>
			<c:when test="${error !=null && error=='Subgroup Discovery' }">
				<a href="#aboutsubgroupModal" data-toggle="modal"
					data-target="#subgroupModal"><button type="button"
						class="btn btn-danger">
						<spring:message code="process.advanced.settings" />
					</button></a>
			</c:when>
			<c:otherwise>
				<a href="#aboutsubgroupModal" data-toggle="modal"
					data-target="#subgroupModal"><button type="button"
						class="btn btn-default">
						<spring:message code="process.advanced.settings" />
					</button></a>
			</c:otherwise>
		</c:choose>
	</div>
	<br />
	<div class="row text-center">
		<form:button type="submit" name="subgroup" class="btn btn-default">
			<spring:message code="process.subgroup.run" />
		</form:button>
	</div>
	<div class="modal fade" id="subgroupModal" tabindex="-1" role="dialog"
		aria-labelledby="subgroupModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h4 class="modal-title" id="subgroupModalLabel">
						<spring:message code="process.subgroup.advanced.settings" />
					</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="subgroupModeSelect"
							class="col-sm-4 col-sm-offset-1 control-label"><spring:message
								code="process.subgroup.mode" /></label>
						<div class="col-sm-4">
							<form:select class="form-control" id="subgroupModeSelect"
								path="mode">
								<form:option value="k best rules" selected="selected" />
								<form:option value="above minimum utility" />
							</form:select>
							<font color="red"><form:errors path="mode" /></font>
						</div>
					</div>
					<div class="form-group">
						<label for="subgroupUtilityFunctionSelect"
							class="col-sm-4 col-sm-offset-1 control-label"><spring:message
								code="process.subgroup.utility.function" /></label>
						<div class="col-sm-4">
							<form:select class="form-control"
								id="subgroupUtilityFunctionSelect" path="utilityFunction">
								<form:option value="WRAcc" selected="selected" />
								<form:option value="Coverage" />
								<form:option value="Precision" />
								<form:option value="Accuracy" />
								<form:option value="Bias" />
								<form:option value="Lift" />
								<form:option value="Binominal" />
								<form:option value="Squared" />
								<form:option value="Odds" />
								<form:option value="Odds Ratio" />
							</form:select>
							<font color="red"><form:errors path="utilityFunction" /></font>
						</div>
					</div>
					<div class="form-group">
						<spring:message code="process.subgroup.min.utility"
							var="minUtility" />
						<label for="subgroupMinUtility"
							class="col-sm-4 col-sm-offset-1 control-label">${minUtility }</label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control"
								id="subgroupMinUtility" placeholder="${minUtility }Min Utility"
								path="minUtility" />
							<font color="red"><form:errors path="minUtility" /></font>
						</div>
					</div>
					<div class="form-group">
						<spring:message code="process.subgroup.k.best.rules"
							var="kBestRules" />
						<label for="subgroupKBestRules"
							class="col-sm-4 col-sm-offset-1 control-label">${kBestRules }</label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control"
								id="subgroupKBestRules" placeholder="${kBestRules }"
								path="kBestRules" />
							<font color="red"><form:errors path="kBestRules" /></font>
						</div>
					</div>
					<div class="form-group">
						<spring:message code="process.subgroup.rule.generation"
							var="ruleGeneration" />
						<label for="subgroupUtilityFunctionSelect"
							class="col-sm-4 col-sm-offset-1 control-label">${ruleGeneration }</label>
						<div class="col-sm-4">
							<form:select class="form-control"
								id="subgroupUtilityFunctionSelect" path="ruleGeneration">
								<form:option value="positive" />
								<form:option value="negative" />
								<form:option value="prediction" />
								<form:option value="both" selected="selected" />
							</form:select>
							<font color="red"><form:errors path="ruleGeneration" /></font>
						</div>
					</div>
					<div class="form-group">
						<spring:message code="process.subgroup.max.depth" var="maxDepth" />
						<label for="subgroupMaxDepthRules"
							class="col-sm-4 col-sm-offset-1 control-label">${maxDepth }</label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control"
								id="subgroupMaxDepthRules" placeholder="${maxDepth }"
								path="maxDepth" />
							<font color="red"><form:errors path="maxDepth" /></font>
						</div>
					</div>
					<div class="form-group">
						<spring:message code="process.subgroup.min.coverage"
							var="minCoverage" />
						<label for="subgroupMinCoverage"
							class="col-sm-4 col-sm-offset-1 control-label">${minCoverage }</label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control"
								id="subgroupMinCoverage" placeholder="${minCoverage }"
								path="minCoverage" />
							<font color="red"><form:errors path="minCoverage" /></font>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<spring:message code="process.go.back" />
					</button>
					<form:button type="submit" name="subgroup" class="btn btn-default">
						<spring:message code="process.subgroup.run" />
					</form:button>
				</div>
			</div>
		</div>

	</div>
</form:form>