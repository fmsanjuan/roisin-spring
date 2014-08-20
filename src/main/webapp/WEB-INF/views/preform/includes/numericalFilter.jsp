
<div class="form-group">
	<form:form method="post" action="filternumerical"
		modelAttribute="filterConditionForm" role="form">
		<form:hidden path="dataId" value="${dataId }" />

		<div class="row">
			<div class="col-md-offset-1">
				<h4>Numerical Filter</h4>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4 col-md-offset-1">
				<form:select path="filterAttribute" class="form-control"
					id="nominalFilterSelect">
					<c:forEach items="${attributes }" var="attribute">
						<c:if test="${attribute.isNumerical() }">
							<form:option value="${attribute.getName() }" />
						</c:if>
					</c:forEach>
				</form:select>
			</div>
			<div class="col-md-4">
				<form:select path="filterOperator" class="form-control">
					<form:option value="eq">equals</form:option>
					<form:option value="neq">non equals</form:option>
					<form:option value="goe">greater or equals</form:option>
					<form:option value="gt">greater</form:option>
					<form:option value="soe">smaller or equals</form:option>
					<form:option value="st">smaller</form:option>
				</form:select>
			</div>
			<div class="col-md-2">
				<form:input path="filterValue" type="text" class="form-control"
					placeholder="value" />
			</div>
		</div>
		<br />
		<div class="row">
			<div class="col-md-offset-8">
				<form:button type="submit" name="filter" value="Filter"
					class="btn btn-default btn-md">Delete Numerical</form:button>
			</div>
		</div>
	</form:form>
</div>