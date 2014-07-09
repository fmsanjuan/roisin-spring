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
				<form:form method="post" class="form-horizontal"
					action="/roisin-spring/processing/ripper" role="form"
					modelAttribute="form">
					<form:hidden path="deletedRows" />
					<form:hidden path="attributeSelection" />
					<form:hidden path="filePath" />
					<form:hidden path="label" />
					<form:hidden path="filterCondition" />
					<form:hidden path="filterAttribute" />
					<form:hidden path="filterOperator" />
					<form:hidden path="filterValue" />
					<form:hidden path="exampleSetSize" />
					<!-- Tree2Rules Hidden -->
					<form:hidden path="tree2RulesCriterion" />
					<form:hidden path="minimalSizeForSplit" />
					<form:hidden path="minimalLeafSize" />
					<form:hidden path="minimalGain" />
					<form:hidden path="maximalDepth" />
					<form:hidden path="confidence" />
					<form:hidden path="numberOfPrepruningAlternatives" />
					<form:hidden path="noPrepruning" />
					<form:hidden path="noPruning" />
					<!-- Subgroup Hidden -->
					<form:hidden path="mode" />
					<form:hidden path="utilityFunction" />
					<form:hidden path="minUtility" />
					<form:hidden path="kBestRules" />
					<form:hidden path="maxDepth" />
					<form:hidden path="minCoverage" />
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
					<br />
					<a href="#aboutRipperModal" data-toggle="modal"
						data-target="#ripperModal"><button type="button"
							class="btn btn-default">Advanced Settings</button>
						</button></a>
					<form:button type="submit" class="btn btn-default">Run Ripper</form:button>
					<div class="modal fade" id="ripperModal" tabindex="-1"
						role="dialog" aria-labelledby="ripperModalLabel"
						aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal"
										aria-hidden="true"></button>
									<h4 class="modal-title" id="ripperModalLabel">Ripper
										Advanced Settings</h4>
								</div>
								<div class="modal-body">
									<div class="form-group">
										<label for="ripperCriterionSelect"
											class="col-sm-4 col-sm-offset-1 control-label">Criterion</label>
										<div class="col-sm-4">
											<form:select class="form-control" id="ripperCriterionSelect"
												path="ripperCriterion">
												<option>information gain</option>
												<option>accuracy</option>
											</form:select>
										</div>
									</div>
									<div class="form-group">
										<label for="ripperSampleRatio"
											class="col-sm-4 col-sm-offset-1 control-label">Sample
											Ratio</label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												id="ripperSampleRatio" placeholder="Sample Ratio"
												path="sampleRatio" />
										</div>
									</div>
									<div class="form-group">
										<label for="ripperPureness"
											class="col-sm-4 col-sm-offset-1 control-label">Pureness</label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												id="ripperPureness" placeholder="Pureness" path="pureness" />
										</div>
									</div>
									<div class="form-group">
										<label for="ripperMinimalPruneBenefit"
											class="col-sm-4 col-sm-offset-1 control-label">Minimal
											Prune Benefit</label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												id="ripperMinimalPruneBenefit"
												placeholder="Minimal Prune Benefit"
												path="minimalPruneBenefit" />
										</div>
									</div>

								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Go Back</button>
									<form:button type="submit" class="btn btn-default">Run Ripper</form:button>
								</div>
							</div>
						</div>
					</div>
				</form:form>
			</div>
		</div>

		<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
			<div class="product-chooser-item">
				<form:form method="post" class="form-horizontal"
					action="/roisin-spring/processing/subgroup" role="form"
					modelAttribute="form">
					<form:hidden path="deletedRows" />
					<form:hidden path="attributeSelection" />
					<form:hidden path="filePath" />
					<form:hidden path="label" />
					<form:hidden path="filterCondition" />
					<form:hidden path="filterAttribute" />
					<form:hidden path="filterOperator" />
					<form:hidden path="filterValue" />
					<form:hidden path="exampleSetSize" />
					<!-- Ripper Hidden -->
					<form:hidden path="ripperCriterion" />
					<form:hidden path="sampleRatio" />
					<form:hidden path="pureness" />
					<form:hidden path="minimalPruneBenefit" />
					<!-- Tree2Rules Hidden -->
					<form:hidden path="tree2RulesCriterion" />
					<form:hidden path="minimalSizeForSplit" />
					<form:hidden path="minimalLeafSize" />
					<form:hidden path="minimalGain" />
					<form:hidden path="maximalDepth" />
					<form:hidden path="confidence" />
					<form:hidden path="numberOfPrepruningAlternatives" />
					<form:hidden path="noPrepruning" />
					<form:hidden path="noPruning" />

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
					<br />
					<a href="#aboutsubgroupModal" data-toggle="modal"
						data-target="#subgroupModal"><button type="button"
							class="btn btn-default">Advanced Settings</button></a>
					<form:button type="submit" class="btn btn-default">Run Subgroup</form:button>

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
									<div class="form-group">
										<label for="subgroupModeSelect"
											class="col-sm-4 col-sm-offset-1 control-label">Mode</label>
										<div class="col-sm-4">
											<form:select class="form-control" id="subgroupModeSelect"
												path="mode">
												<option>k best rules</option>
												<option>above minimum utility</option>
											</form:select>
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupUtilityFunctionSelect"
											class="col-sm-4 col-sm-offset-1 control-label">Utility
											Function</label>
										<div class="col-sm-4">
											<form:select class="form-control"
												id="subgroupUtilityFunctionSelect" path="utilityFunction">
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
											</form:select>
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
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupMaxDepthRules"
											class="col-sm-4 col-sm-offset-1 control-label">Max
											Depth</label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												id="subgroupMaxDepthRules" placeholder="Max Depth"
												path="maxDepth" />
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
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Go Back</button>
									<form:button type="submit" class="btn btn-default">Run Subgroup</form:button>
								</div>
							</div>
						</div>

					</div>
				</form:form>
			</div>
		</div>

		<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
			<div class="product-chooser-item">
				<form:form method="post" class="form-horizontal"
					action="/roisin-spring/processing/tree" role="form"
					modelAttribute="form">
					<form:hidden path="deletedRows" />
					<form:hidden path="attributeSelection" />
					<form:hidden path="filePath" />
					<form:hidden path="label" />
					<form:hidden path="filterCondition" />
					<form:hidden path="filterAttribute" />
					<form:hidden path="filterOperator" />
					<form:hidden path="filterValue" />
					<form:hidden path="exampleSetSize" />
					<!-- Ripper Hidden -->
					<form:hidden path="ripperCriterion" />
					<form:hidden path="sampleRatio" />
					<form:hidden path="pureness" />
					<form:hidden path="minimalPruneBenefit" />
					<!-- Subgroup Hidden -->
					<form:hidden path="mode" />
					<form:hidden path="utilityFunction" />
					<form:hidden path="minUtility" />
					<form:hidden path="kBestRules" />
					<form:hidden path="maxDepth" />
					<form:hidden path="minCoverage" />

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
					<br />
					<a href="#aboutTree2rulesModal" data-toggle="modal"
						data-target="#tree2rulesModal"><button type="button"
							class="btn btn-default">Advanced Settings</button></a>
					<form:button type="submit" class="btn btn-default">Run Tree to Rules</form:button>
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
									<div class="form-group">
										<label for="subgroupModeSelect" class="col-sm-6 control-label">Criterion</label>
										<div class="col-sm-4">
											<form:select class="form-control" id="subgroupModeSelect"
												path="tree2RulesCriterion">
												<option>gain ratio</option>
												<option>information gain</option>
												<option>gini index</option>
												<option>accuracy</option>
											</form:select>
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupMinSizeSplit"
											class="col-sm-6 control-label">Minimal Size for Split</label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												id="subgroupMinSizeSplit"
												placeholder="Minimal Size for Split"
												path="minimalSizeForSplit" />
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupMinLeafSize"
											class="col-sm-6 control-label">Minimal Leaf Size</label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												id="subgroupMinLeafSize" placeholder="Minimal Leaf Size"
												path="minimalLeafSize" />
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupMinimalGain"
											class="col-sm-6 control-label">Minimal Gain</label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												id="subgroupMinimalGain" placeholder="Minimal Gain"
												path="minimalGain" />
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupMaxDepth" class="col-sm-6 control-label">Maximal
											Depth</label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												id="subgroupMaxDepth" placeholder="Maximal Depth"
												path="maximalDepth" />
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupConfidence" class="col-sm-6 control-label">Confidence</label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												id="subgroupConfidence" placeholder="Confidence"
												path="confidence" />
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupNumberOfPrepruning"
											class="col-sm-6 control-label">Number of prepruning
											Alternatives</label>
										<div class="col-sm-4">
											<form:input type="text" class="form-control"
												id="subgroupNumberOfPrepruning"
												placeholder="Number of prepruning"
												path="numberOfPrepruningAlternatives" />
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupNoPrepruning"
											class="col-sm-6 control-label">No prepruning</label>
										<div class="col-sm-4">
											<form:checkbox class="form-control" id="subgroupNoPrepruning"
												path="noPrepruning" />
										</div>
									</div>
									<div class="form-group">
										<label for="subgroupNoPruning" class="col-sm-6 control-label">No
											pruning</label>
										<div class="col-sm-4">
											<form:checkbox class="form-control" id="subgroupNoPruning"
												path="noPruning" />
										</div>
									</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-default"
										data-dismiss="modal">Go Back</button>
									<form:button type="submit" class="btn btn-default">Run Tree to Rules</form:button>
								</div>
							</div>
						</div>
					</div>
				</form:form>
			</div>
		</div>

	</div>
</div>











<!-- <div class="container"> -->
<!-- 	<div class="row"> -->
<!-- 		<h3 class="text-center">Choose an algorithm</h3> -->
<!-- 	</div> -->
<!-- 	<br /> <br /> <br /> -->
<!-- 	<div class="row"> -->
<!-- 		<div class="col-md-2 col-md-offset-1"> -->
<%-- 			<form:form method="post" modelAttribute="form" --%>
<%-- 				action="/roisin-spring/processing/ripper" role="form"> --%>
<%-- 				<form:hidden path="deletedRows" /> --%>
<%-- 				<form:hidden path="attributeSelection" /> --%>
<%-- 				<form:hidden path="filePath" /> --%>
<%-- 				<form:hidden path="label" /> --%>
<%-- 				<form:hidden path="filterCondition" /> --%>
<%-- 				<form:hidden path="filterAttribute" /> --%>
<%-- 				<form:hidden path="filterOperator" /> --%>
<%-- 				<form:hidden path="filterValue" /> --%>
<%-- 				<form:hidden path="exampleSetSize" /> --%>
<%-- 				<form:button type="submit" class="btn btn-primary btn-lg">Run Ripper</form:button> --%>
<%-- 			</form:form> --%>
<!-- 		</div> -->
<!-- 		<div class="col-md-2 col-md-offset-2"> -->
<%-- 			<form:form method="post" modelAttribute="form" --%>
<%-- 				action="/roisin-spring/processing/subgroup" role="form"> --%>
<%-- 				<form:hidden path="deletedRows" /> --%>
<%-- 				<form:hidden path="attributeSelection" /> --%>
<%-- 				<form:hidden path="filePath" /> --%>
<%-- 				<form:hidden path="label" /> --%>
<%-- 				<form:hidden path="filterCondition" /> --%>
<%-- 				<form:hidden path="filterAttribute" /> --%>
<%-- 				<form:hidden path="filterOperator" /> --%>
<%-- 				<form:hidden path="filterValue" /> --%>
<%-- 				<form:hidden path="exampleSetSize" /> --%>
<%-- 				<form:button type="submit" class="btn btn-primary btn-lg"> Run Subgroup Discovery</form:button> --%>
<%-- 			</form:form> --%>
<!-- 		</div> -->
<!-- 		<div class="col-md-2 col-md-offset-2"> -->
<%-- 			<form:form method="post" modelAttribute="form" --%>
<%-- 				action="/roisin-spring/processing/tree" role="form"> --%>
<%-- 				<form:hidden path="deletedRows" /> --%>
<%-- 				<form:hidden path="attributeSelection" /> --%>
<%-- 				<form:hidden path="filePath" /> --%>
<%-- 				<form:hidden path="label" /> --%>
<%-- 				<form:hidden path="filterCondition" /> --%>
<%-- 				<form:hidden path="filterAttribute" /> --%>
<%-- 				<form:hidden path="filterOperator" /> --%>
<%-- 				<form:hidden path="filterValue" /> --%>
<%-- 				<form:hidden path="exampleSetSize" /> --%>
<%-- 				<form:button type="submit" class="btn btn-primary btn-lg">Run Tree to Rules</form:button> --%>
<%-- 			</form:form> --%>
<!-- 		</div> -->
<!-- 	</div> -->
<!-- </div> -->
