<form:form method="post" action="processData" modelAttribute="form"
	role="form">
	<form:hidden path="filePath" />
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
	<c:if test="${error == true}">
		<div class="row">
			<div class="alert alert-danger alert-dismissable">
				<button type="button" class="close" data-dismiss="alert"
					aria-hidden="true">&times;</button>
				<strong>Error! </strong>
				<form:errors path="label" />
				<form:errors path="deletedRows" />
				<form:errors path="attributeSelection" />
			</div>
		</div>
	</c:if>
	<div class="row">
		<div class="col-md-3">
			<h3>Label selection</h3>
			<br />
			<form:select path="label" class="form-control">
				<c:forEach items="${attributes }" var="attribute">
					<form:option value="${attribute.getName() }" />
				</c:forEach>
			</form:select>
		</div>
		<div class="col-md-7">
			<h3>Filter condition</h3>
			<br />
			<div class="col-md-3">
				<form:select path="filterAttribute" class="form-control">
					<c:forEach items="${attributes }" var="attribute">
						<form:option value="${attribute.getName() }" />
					</c:forEach>
				</form:select>
			</div>
			<div class="col-md-3">
				<form:select path="filterOperator" class="form-control">
					<form:option value="eq">equals</form:option>
					<form:option value="neq">non equals</form:option>
					<form:option value="goe">greater or equals</form:option>
					<form:option value="gt">greater</form:option>
					<form:option value="soe">smaller or equals</form:option>
					<form:option value="st">smaller</form:option>
				</form:select>
			</div>
			<div class="col-md-6">
				<form:input path="filterValue" type="text" class="form-control"
					placeholder="value" />
			</div>
		</div>
		<div class="col-md-2">
			<div class="row">
				<form:button type="submit" name="export" value="Export"
					class="btn btn-primary btn-lg">Export Data</form:button>
			</div>
			<div class="row">
				<form:button type="submit" name="process" value="Process"
					class="btn btn-success btn-lg">Process Data</form:button>
			</div>
		</div>
	</div>
	<br />
	<div class="row">
		<div class="table-responsive">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>#</th>
						<c:forEach items="${attributes }" var="attribute">
							<th>${attribute.getName() }<form:checkbox
									path="attributeSelection" value="${attribute.getName() }" /></th>
						</c:forEach>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody id="myTable">
					<c:forEach items="${examples }" var="example" varStatus="loop">
						<tr>
							<td>${loop.index+1 }</td>
							<c:forEach items="${attributes }" var="attribute">
								<td>${example.getValueAsString(attribute) }</td>
							</c:forEach>
							<td><form:checkbox path="deletedRows" value="${loop.index }" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<div class="row">
		<div class="col-md-1">
			Total <span class="badge">${examples.size()}</span>
		</div>
		<div class="col-md-10 text-center">
			<ul class="pagination pagination" id="myPager"></ul>
		</div>
	</div>

</form:form>