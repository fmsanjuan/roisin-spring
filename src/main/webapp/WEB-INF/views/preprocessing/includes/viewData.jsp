<form:form method="post" action="processData"
	modelAttribute="form" role="form">
	<form:hidden path="filePath" />
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
				<form:input path="filterValue" type="text" class="form-control" />
			</div>
		</div>
		<div class="col-md-2">
			<br /> <br /> <br />
			<form:button type="submit" class="btn btn-success btn-lg">Process Data</form:button>
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
							<th>${attribute.getName() } <form:checkbox
									path="deletedAttributes" value="${attribute.getName() }" /></th>
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