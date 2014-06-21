<div class="row">
	<div class="table-responsive">
		<table class="table table-hover">
			<thead>
				<tr>
					<th>#</th>
					<c:forEach items="${attributes}" var="attribute">
						<th>${attribute.getName() }</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody id="myTable">
				<c:forEach items="${examples}" var="example" varStatus="loop">
					<tr>
						<td>${loop.index+1 }</td>
						<c:forEach items="${attributes}" var="attribute">
							<td>${example.getValueAsString(attribute) }</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<div class="row">
	<div class="col-md-1">
		Total <span class="badge">${exampleSize}</span>
	</div>
	<div class="col-md-10 text-center">
		<ul class="pagination pagination-lg pager" id="myPager"></ul>
	</div>
</div>