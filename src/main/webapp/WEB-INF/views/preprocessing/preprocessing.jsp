<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>


<div class="page-header text-center">
	<h1>
		<spring:message code="preprocessing.header" />
		<small>Select your data</small>
	</h1>
</div>

<div class="container">
	<c:choose>
		<c:when test="${uploaded == false}">
			<form:form method="post" enctype="multipart/form-data"
				modelAttribute="uploadedFile" action="upload">

				<div class="row">
					<h3>Data upload</h3>
				</div>
				<div class="row">
					<input type="file" name="file" class="filestyle">
					<form:errors path="file" />
				</div>
				<br />
				<div class="row">
					<button class="btn btn-default" type="submit" value="Upload">Upload</button>
				</div>
			</form:form>
		</c:when>
		<c:when test="${uploaded == true }">
			<h3>
				<span class="label label-success">Success</span>
			</h3>
			<br />

			<ul class="nav nav-tabs" id="myTab">
				<li class="active"><a href="#view" data-toggle="tab">View
						Data</a></li>
				<li><a href="#deleteRows" data-toggle="tab">Delete Rows</a></li>
				<li><a href="#deleteAttributes" data-toggle="tab">Delete
						Attributes</a></li>
				<li><a href="#settings" data-toggle="tab">Settings</a></li>
			</ul>

			<div class="tab-content">
				<div class="tab-pane active" id="view">
					<br />
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
				</div>
				<div class="tab-pane" id="deleteRows">
					<br />
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
								<tbody id="myTable2">
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
							<ul class="pagination pagination-lg pager" id="myPager2"></ul>
						</div>
					</div>
				</div>
				<div class="tab-pane" id="deleteAttributes">
					<br />
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
								<tbody id="myTable3">
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
							<ul class="pagination pagination-lg pager" id="myPager3"></ul>
						</div>
					</div>
				</div>
				<div class="tab-pane" id="settings">settings</div>
			</div>

			<script>
				$(function() {
					$('#myTab a:first').tab('show')
				})
			</script>
		</c:when>
	</c:choose>
</div>