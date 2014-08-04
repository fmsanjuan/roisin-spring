<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form method="post" class="form-horizontal"
	action="../process/tree" role="form"
	modelAttribute="treeSettings">

	<form:hidden path="process" />

	<div class="col-xs-8 col-sm-8 col-md-12 col-lg-12">
		<h3 class="text-center">Tree to Rules</h3>
		<br /> <span class="description">Lorem ipsum dolor sit amet,
			consectetuer adipiscing elit. Aenean commodo ligula eget dolor.
			Aenean massa. Cum sociis natoque penatibus et magnis dis parturient
			montes, nascetur ridiculus mus. Donec quam felis, ultricies nec,
			pellentesque eu, pretium quis, sem.</span> <input type="radio"
			name="product" value="mobile" class="non-visible">
	</div>
	<div class="clear"></div>
	<br />
	<c:choose>
		<c:when test="${error !=null && error=='Tree to Rules' }">
			<a href="#aboutTree2rulesModal" data-toggle="modal"
				data-target="#tree2rulesModal"><button type="button"
					class="btn btn-danger">Advanced Settings</button></a>
		</c:when>
		<c:otherwise>
			<a href="#aboutTree2rulesModal" data-toggle="modal"
				data-target="#tree2rulesModal"><button type="button"
					class="btn btn-default">Advanced Settings</button></a>
		</c:otherwise>
	</c:choose>
	<form:button type="submit" name="tree" class="btn btn-default">Run Tree to Rules</form:button>
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
								<form:option value="gain_ratio" selected="selected" />
								<form:option value="information_gain" />
								<form:option value="gini_index" />
								<form:option value="accuracy" />
							</form:select>
							<font color="red"><form:errors path="tree2RulesCriterion" /></font>
						</div>
					</div>
					<div class="form-group">
						<label for="subgroupMinSizeSplit" class="col-sm-6 control-label">Minimal
							Size for Split</label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control"
								id="subgroupMinSizeSplit" placeholder="Minimal Size for Split"
								path="minimalSizeForSplit" />
							<font color="red"><form:errors path="minimalSizeForSplit" /></font>
						</div>
					</div>
					<div class="form-group">
						<label for="subgroupMinLeafSize" class="col-sm-6 control-label">Minimal
							Leaf Size</label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control"
								id="subgroupMinLeafSize" placeholder="Minimal Leaf Size"
								path="minimalLeafSize" />
							<font color="red"><form:errors path="minimalLeafSize" /></font>
						</div>
					</div>
					<div class="form-group">
						<label for="subgroupMinimalGain" class="col-sm-6 control-label">Minimal
							Gain</label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control"
								id="subgroupMinimalGain" placeholder="Minimal Gain"
								path="minimalGain" />
							<font color="red"><form:errors path="minimalGain" /></font>
						</div>
					</div>
					<div class="form-group">
						<label for="subgroupMaxDepth" class="col-sm-6 control-label">Maximal
							Depth</label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control"
								id="subgroupMaxDepth" placeholder="Maximal Depth"
								path="maximalDepth" />
							<font color="red"><form:errors path="maximalDepth" /></font>
						</div>
					</div>
					<div class="form-group">
						<label for="subgroupConfidence" class="col-sm-6 control-label">Confidence</label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control"
								id="subgroupConfidence" placeholder="Confidence"
								path="confidence" />
							<font color="red"><form:errors path="confidence" /></font>
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
							<font color="red"><form:errors path="numberOfPrepruningAlternatives" /></font>
						</div>
					</div>
					<div class="form-group">
						<label for="subgroupNoPrepruning" class="col-sm-6 control-label">No
							prepruning</label>
						<div class="col-sm-4">
							<form:checkbox class="form-control" id="subgroupNoPrepruning"
								path="noPrepruning" />
							<font color="red"><form:errors path="noPrepruning" /></font>
						</div>
					</div>
					<div class="form-group">
						<label for="subgroupNoPruning" class="col-sm-6 control-label">No
							pruning</label>
						<div class="col-sm-4">
							<form:checkbox class="form-control" id="subgroupNoPruning"
								path="noPruning" />
							<font color="red"><form:errors path="noPruning" /></font>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Go
						Back</button>
					<form:button type="submit" name="tree" class="btn btn-default">Run Tree to Rules</form:button>
				</div>
			</div>
		</div>
	</div>
</form:form>