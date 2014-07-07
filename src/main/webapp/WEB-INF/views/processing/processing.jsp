<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>

<div class="page-header text-center">
	<h1>
		<spring:message code="processing.header" />
		<small>Analize your data</small>
	</h1>
</div>

<style>
div.clear {
	clear: both;
}

div.product-chooser {
	
}

div.product-chooser.disabled div.product-chooser-item {
	zoom: 1;
	filter: alpha(opacity = 60);
	opacity: 0.6;
	cursor: default;
}

div.product-chooser div.product-chooser-item {
	padding: 11px;
	border-radius: 6px;
	cursor: pointer;
	position: relative;
	border: 1px solid #efefef;
	margin-bottom: 10px;
	margin-left: 10px;
	margin-right: 10x;
}

div.product-chooser div.product-chooser-item.selected {
	border: 4px solid #428bca;
	background: #efefef;
	padding: 8px;
	filter: alpha(opacity = 100);
	opacity: 1;
}

div.product-chooser div.product-chooser-item img {
	padding: 0;
}

div.product-chooser div.product-chooser-item span.title {
	display: block;
	margin: 10px 0 5px 0;
	font-weight: bold;
	font-size: 12px;
}

div.product-chooser div.product-chooser-item span.description {
	font-size: 12px;
}

div.product-chooser div.product-chooser-item input.non-visible {
	position: absolute;
	left: 0;
	top: 0;
	visibility: hidden;
}
</style>

<script>
	$(function() {
		$('div.product-chooser').not('.disabled').find(
				'div.product-chooser-item').on(
				'click',
				function() {
					$(this).parent().parent().find('div.product-chooser-item')
							.removeClass('selected');
					$(this).addClass('selected');
					$(this).find('input[type="radio"]').prop("checked", true);

				});
	});
</script>

<div class="container">
	<div class="row form-group product-chooser">
		<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
			<div class="product-chooser-item selected">
				<div class="col-xs-8 col-sm-8 col-md-12 col-lg-12">
					<h3 class="text-center">Ripper</h3>
					<br /> <span class="description">Lorem ipsum dolor sit
						amet, consectetuer adipiscing elit. Aenean commodo ligula eget
						dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis
						parturient montes, nascetur ridiculus mus. Donec quam felis,
						ultricies nec, pellentesque eu, pretium quis, sem.</span> <input
						type="radio" name="product" value="mobile_desktop"
						checked="checked" class="non-visible">
				</div>
				<div class="clear"></div>
				<br /> <a href="#aboutRipperModal" data-toggle="modal"
					data-target="#ripperModal"><button type="button"
						class="btn btn-default">Advanced Settings</button>
					</button></a>
				<div class="modal fade" id="ripperModal" tabindex="-1" role="dialog"
					aria-labelledby="ripperModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true"></button>
								<h4 class="modal-title" id="ripperModalLabel">Ripper
									Advanced Settings</h4>
							</div>
							<div class="modal-body">
								<form class="form-horizontal" role="form">
									<div class="form-group">
										<label for="ripperCriterionSelect"
											class="col-sm-4 col-sm-offset-1 control-label">Criterion</label>
										<div class="col-sm-4">
											<select class="form-control" id="ripperCriterionSelect">
												<option>information gain</option>
												<option>accuracy</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label for="ripperSampleRatio"
											class="col-sm-4 col-sm-offset-1 control-label">Sample
											Ratio</label>
										<div class="col-sm-4">
											<input type="text" class="form-control"
												id="ripperSampleRatio" placeholder="Sample Ratio">
										</div>
									</div>
									<div class="form-group">
										<label for="ripperPureness"
											class="col-sm-4 col-sm-offset-1 control-label">Pureness</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="ripperPureness"
												placeholder="Pureness">
										</div>
									</div>
									<div class="form-group">
										<label for="ripperMinimalPruneBenefit"
											class="col-sm-4 col-sm-offset-1 control-label">Minimal
											Prune Benefit</label>
										<div class="col-sm-4">
											<input type="text" class="form-control"
												id="ripperMinimalPruneBenefit"
												placeholder="Minimal Prune Benefit">
										</div>
									</div>

								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Go Back</button>
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Run Ripper</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
			<div class="product-chooser-item">
				<div class="col-xs-8 col-sm-8 col-md-12 col-lg-12">
					<h3 class="text-center">Subgroup Discovery</h3>
					<br /> <span class="description">Lorem ipsum dolor sit
						amet, consectetuer adipiscing elit. Aenean commodo ligula eget
						dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis
						parturient montes, nascetur ridiculus mus. Donec quam felis,
						ultricies nec, pellentesque eu, pretium quis, sem.</span> <input
						type="radio" name="product" value="desktop" class="non-visible">
				</div>
				<div class="clear"></div>
				<br /> <a href="#aboutsubgroupModal" data-toggle="modal"
					data-target="#subgroupModal"><button type="button"
						class="btn btn-default">Advanced Settings</button></a>
				<div class="modal fade" id="subgroupModal" tabindex="-1"
					role="dialog" aria-labelledby="subgroupModalLabel"
					aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true"></button>
								<h4 class="modal-title" id="subgroupModalLabel">Subgroup
									Discovery Advanced Settings</h4>
							</div>
							<div class="modal-body">
								<form class="form-horizontal" role="form">
									<div class="form-group">
										<label for="subgroupModeSelect"
											class="col-sm-4 col-sm-offset-1 control-label">Mode</label>
										<div class="col-sm-4">
											<select class="form-control" id="subgroupModeSelect">
												<option>k best rules</option>
												<option>above minimum utility</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupUtilityFunctionSelect"
											class="col-sm-4 col-sm-offset-1 control-label">Utility
											Function</label>
										<div class="col-sm-4">
											<select class="form-control"
												id="subgroupUtilityFunctionSelect">
												<option>WRAcc</option>
												<option>Coverage</option>
												<option>Precision</option>
												<option>Accuracy</option>
												<option>Bias</option>
												<option>Lift</option>
												<option>Binominal</option>
												<option>Squared</option>
												<option>Odds</option>
												<option>Odds Ratio</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupMinUtility"
											class="col-sm-4 col-sm-offset-1 control-label">Min
											Utility</label>
										<div class="col-sm-4">
											<input type="text" class="form-control"
												id="subgroupMinUtility" placeholder="Min Utility">
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupKBestRules"
											class="col-sm-4 col-sm-offset-1 control-label">k best
											rules</label>
										<div class="col-sm-4">
											<input type="text" class="form-control"
												id="subgroupKBestRules" placeholder="k best rules">
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupMaxDepthRules"
											class="col-sm-4 col-sm-offset-1 control-label">Max
											Depth</label>
										<div class="col-sm-4">
											<input type="text" class="form-control"
												id="subgroupMaxDepthRules" placeholder="Max Depth">
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupMinCoverage"
											class="col-sm-4 col-sm-offset-1 control-label">Min
											Coverage</label>
										<div class="col-sm-4">
											<input type="text" class="form-control"
												id="subgroupMinCoverage" placeholder="Min Coverage">
										</div>
									</div>
								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Go Back</button>
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Run Ripper</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
			<div class="product-chooser-item">
				<div class="col-xs-8 col-sm-8 col-md-12 col-lg-12">
					<h3 class="text-center">Tree to Rules</h3>
					<br /> <span class="description">Lorem ipsum dolor sit
						amet, consectetuer adipiscing elit. Aenean commodo ligula eget
						dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis
						parturient montes, nascetur ridiculus mus. Donec quam felis,
						ultricies nec, pellentesque eu, pretium quis, sem.</span> <input
						type="radio" name="product" value="mobile" class="non-visible">
				</div>
				<div class="clear"></div>
				<br /> <a href="#aboutTree2rulesModal" data-toggle="modal"
					data-target="#tree2rulesModal"><button type="button"
						class="btn btn-default">Advanced Settings</button></a>
				<div class="modal fade" id="tree2rulesModal" tabindex="-1"
					role="dialog" aria-labelledby="tree2rulesModalLabel"
					aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true"></button>
								<h4 class="modal-title" id="tree2rulesModalLabel">Tree to
									Rules Advanced Settings</h4>
							</div>
							<div class="modal-body">
								<form class="form-horizontal" role="form">
									<div class="form-group">
										<label for="subgroupModeSelect"
											class="col-sm-6 control-label">Criterion</label>
										<div class="col-sm-4">
											<select class="form-control" id="subgroupModeSelect">
												<option>gain ratio</option>
												<option>information gain</option>
												<option>gini index</option>
												<option>accuracy</option>
											</select>
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupMinSizeSplit"
											class="col-sm-6 control-label">Minimal
											Size for Split</label>
										<div class="col-sm-4">
											<input type="text" class="form-control"
												id="subgroupMinSizeSplit"
												placeholder="Minimal Size for Split">
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupMinLeafSize"
											class="col-sm-6 control-label">Minimal
											Leaf Size</label>
										<div class="col-sm-4">
											<input type="text" class="form-control"
												id="subgroupMinLeafSize" placeholder="Minimal Leaf Size">
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupMinimalGain"
											class="col-sm-6 control-label">Minimal
											Gain</label>
										<div class="col-sm-4">
											<input type="text" class="form-control"
												id="subgroupMinimalGain" placeholder="Minimal Gain">
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupMaxDepth" class="col-sm-6 control-label">Maximal
											Depth</label>
										<div class="col-sm-4">
											<input type="text" class="form-control" id="subgroupMaxDepth"
												placeholder="Maximal Depth">
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupConfidence" class="col-sm-6 control-label">Confidence</label>
										<div class="col-sm-4">
											<input type="text" class="form-control"
												id="subgroupConfidence" placeholder="Confidence">
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupNumberOfPrepruning"
											class="col-sm-6 control-label">Number of prepruning
											Alternatives</label>
										<div class="col-sm-4">
											<input type="text" class="form-control"
												id="subgroupNumberOfPrepruning"
												placeholder="Number of prepruning">
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupNoPrepruning"
											class="col-sm-6 control-label">No prepruning</label>
										<div class="col-sm-4">
											<input type="checkbox" class="form-control"
												id="subgroupNoPrepruning">
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupNoPruning" class="col-sm-6 control-label">No
											pruning</label>
										<div class="col-sm-4">
											<input type="checkbox" class="form-control"
												id="subgroupNoPruning">
										</div>
									</div>
								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Go Back</button>
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Run Ripper</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
</div>











<div class="container">
	<div class="row">
		<h3 class="text-center">Choose an algorithm</h3>
	</div>
	<br /> <br /> <br />
	<div class="row">
		<div class="col-md-2 col-md-offset-1">
			<form:form method="post" modelAttribute="form"
				action="/roisin-spring/processing/ripper" role="form">
				<form:hidden path="deletedRows" />
				<form:hidden path="attributeSelection" />
				<form:hidden path="filePath" />
				<form:hidden path="label" />
				<form:hidden path="filterCondition" />
				<form:hidden path="filterAttribute" />
				<form:hidden path="filterOperator" />
				<form:hidden path="filterValue" />
				<form:hidden path="exampleSetSize" />
				<form:button type="submit" class="btn btn-primary btn-lg">Run Ripper</form:button>
			</form:form>
		</div>
		<div class="col-md-2 col-md-offset-2">
			<form:form method="post" modelAttribute="form"
				action="/roisin-spring/processing/subgroup" role="form">
				<form:hidden path="deletedRows" />
				<form:hidden path="attributeSelection" />
				<form:hidden path="filePath" />
				<form:hidden path="label" />
				<form:hidden path="filterCondition" />
				<form:hidden path="filterAttribute" />
				<form:hidden path="filterOperator" />
				<form:hidden path="filterValue" />
				<form:hidden path="exampleSetSize" />
				<form:button type="submit" class="btn btn-primary btn-lg"> Run Subgroup Discovery</form:button>
			</form:form>
		</div>
		<div class="col-md-2 col-md-offset-2">
			<form:form method="post" modelAttribute="form"
				action="/roisin-spring/processing/tree" role="form">
				<form:hidden path="deletedRows" />
				<form:hidden path="attributeSelection" />
				<form:hidden path="filePath" />
				<form:hidden path="label" />
				<form:hidden path="filterCondition" />
				<form:hidden path="filterAttribute" />
				<form:hidden path="filterOperator" />
				<form:hidden path="filterValue" />
				<form:hidden path="exampleSetSize" />
				<form:button type="submit" class="btn btn-primary btn-lg">Run Tree to Rules</form:button>
			</form:form>
		</div>
	</div>
</div>
