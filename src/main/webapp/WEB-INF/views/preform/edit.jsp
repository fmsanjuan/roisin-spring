<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ page session="false"%>


<div class="page-header text-center">
	<h1>
		Preprocess <small>Filter your data</small>
	</h1>
</div>

<div class="container">
	<form:form method="post" action="process" modelAttribute="form"
		role="form">
		<form:input path="dataParam" value="${dataParam}" hidden="true" />
		<div class="row">
			<div class="col-md-2 col-md-offset-8">
				<form:button type="submit" name="export" value="Export"
					class="btn btn-primary btn-lg">Export Data</form:button>
			</div>
			<div class="col-md-2">
				<form:button type="submit" name="process" value="Process"
					class="btn btn-success btn-lg">Process Data</form:button>
			</div>
		</div>
		<br />
		<div class="row">
			<display:table uid="examplesTable" keepStatus="false" name="examples"
				pagesize="10" class="table table-hover" requestURI="${requestURI}"
				id="row">
				<display:column title="#">
					<c:out value="${row_rowNum}" />
				</display:column>
				<c:forEach items="${attributes }" var="attribute">
					<display:column title="${attribute.getName()}">
				${row.getValueAsString(attribute) } 
					</display:column>
				</c:forEach>
				<display:column title="Delete">
					<a href="deleterow?${dataParam }&rowId=${row_rowNum-1 }"> <input
						class="btn btn-default" type="button" value="Delete"
						onclick="self.location.href = deleterow?${dataParam }&rowId=${row_rowNum-1 }" />
					</a>
				</display:column>
			</display:table>
		</div>
		<div class="row">
			<div class="col-md-3">
				<h3>Attribute selection</h3>
				<br />
				<form:select multiple="true" path="attributeSelection"
					class="form-control">
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
					<h3>Label</h3>
					<br />
					<form:select path="label" class="form-control">
						<c:forEach items="${attributes }" var="attribute">
							<form:option value="${attribute.getName() }" />
						</c:forEach>
					</form:select>
				</div>
			</div>
		</div>

	</form:form>
</div>