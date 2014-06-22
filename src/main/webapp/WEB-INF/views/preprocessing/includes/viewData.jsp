<form:form method="post" action="updatePreprocessing"
	modelAttribute="form" role="form">
	<form:hidden path="filterCondition" />
	<form:hidden path="label" />

	<div class="row">
		<div class="table-responsive">
			<table class="table table-hover">
				<thead>
					<tr>
						<th>#</th>
						<c:forEach items="${attributes }" var="attribute">
							<th>${attribute.getName() } <form:checkbox path="deletedAttributes" value="${attribute.getName() }" /></th>
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
		<div class="col-md-1">
			<form:button type="submit" class="btn btn-danger">Save</form:button>
		</div>
	</div>
</form:form>