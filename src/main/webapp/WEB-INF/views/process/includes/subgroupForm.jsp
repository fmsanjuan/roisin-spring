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
	<c:choose>
		<c:when test="${error !=null && error=='Subgroup Discovery' }">
			<a href="#aboutsubgroupModal" data-toggle="modal"
				data-target="#subgroupModal"><button type="button"
					class="btn btn-danger">Advanced Settings</button></a>
		</c:when>
		<c:otherwise>
			<a href="#aboutsubgroupModal" data-toggle="modal"
				data-target="#subgroupModal"><button type="button"
					class="btn btn-default">Advanced Settings</button></a>
		</c:otherwise>
	</c:choose>
	<form:button type="submit" name="subgroup" class="btn btn-default">Run Subgroup</form:button>

	<div class="modal fade" id="subgroupModal" tabindex="-1" role="dialog"
		aria-labelledby="subgroupModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h4 class="modal-title" id="subgroupModalLabel">Subgroup
						Discovery Advanced Settings</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="subgroupModeSelect"
							class="col-sm-4 col-sm-offset-1 control-label">Mode</label>
						<div class="col-sm-4">
							<form:select class="form-control" id="subgroupModeSelect"
								path="mode">
								<form:option value="k best rules" selected="selected" />
								<form:option value="above minimum utility" />
							</form:select>
							<form:errors path="mode" />
						</div>
					</div>
					<div class="form-group">
						<label for="subgroupUtilityFunctionSelect"
							class="col-sm-4 col-sm-offset-1 control-label">Utility
							Function</label>
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
							<form:errors path="utilityFunction" />
						</div>
					</div>
					<div class="form-group">
						<label for="subgroupMinUtility"
							class="col-sm-4 col-sm-offset-1 control-label">Min
							Utility</label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control"
								id="subgroupMinUtility" placeholder="Min Utility"
								path="minUtility" />
							<form:errors path="minUtility" />
						</div>
					</div>
					<div class="form-group">
						<label for="subgroupKBestRules"
							class="col-sm-4 col-sm-offset-1 control-label">k best
							rules</label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control"
								id="subgroupKBestRules" placeholder="k best rules"
								path="kBestRules" />
							<form:errors path="kBestRules" />
						</div>
					</div>
					<div class="form-group">
						<label for="subgroupUtilityFunctionSelect"
							class="col-sm-4 col-sm-offset-1 control-label">Utility
							Function</label>
						<div class="col-sm-4">
							<form:select class="form-control"
								id="subgroupUtilityFunctionSelect" path="ruleGeneration">
								<form:option value="positive" />
								<form:option value="negative" />
								<form:option value="prediction" />
								<form:option value="both" selected="selected" />
							</form:select>
							<form:errors path="ruleGeneration" />
						</div>
					</div>
					<div class="form-group">
						<label for="subgroupMaxDepthRules"
							class="col-sm-4 col-sm-offset-1 control-label">Max Depth</label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control"
								id="subgroupMaxDepthRules" placeholder="Max Depth"
								path="maxDepth" />
							<form:errors path="maxDepth" />
						</div>
					</div>
					<div class="form-group">
						<label for="subgroupMinCoverage"
							class="col-sm-4 col-sm-offset-1 control-label">Min
							Coverage</label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control"
								id="subgroupMinCoverage" placeholder="Min Coverage"
								path="minCoverage" />
							<form:errors path="minCoverage" />
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Go
						Back</button>
					<form:button type="submit" name="subgroup" class="btn btn-default">Run Subgroup</form:button>
				</div>
			</div>
		</div>

	</div>
</form:form>