<div class="form-group">
	<form:form method="post" action="filternominal"
		modelAttribute="filterConditionForm" role="form" class="form-inline">
		<form:hidden path="dataId" value="${dataId }" />
		<div class="row">
			<div class="col-md-offset-1">
				<h4><spring:message code="preform.nominal.filter" /></h4>
			</div>
		</div>
		<br />
		<div class="row">
			<div class="col-md-3 col-md-offset-1">
				<form:select path="filterAttribute" class="form-control"
					id="nominalFilterSelect">
					<c:forEach items="${attributes }" var="attribute">
						<c:if test="${attribute.isNominal() }">
							<form:option value="${attribute.getName() }" />
						</c:if>
					</c:forEach>
				</form:select>
			</div>
			<div class="col-md-3">
				<form:select path="filterOperator" class="form-control">
					<form:option value="eq"><spring:message code="preform.equals" /></form:option>
					<form:option value="neq"><spring:message code="preform.non.equals" /></form:option>
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
					class="btn btn-default btn-md"><spring:message code="preform.delete.nominal" /></form:button>
			</div>
		</div>
	</form:form>
</div>