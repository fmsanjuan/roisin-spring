<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>


<h1>
	<spring:message code="preprocessing.header" />
</h1>

<div class="container">
	<c:choose>
		<c:when test="${uploaded == false}">
			<form:form method="post" enctype="multipart/form-data"
				modelAttribute="uploadedFile" action="upload">
				<table>
					<tr>
						<td>Upload File:</td>
						<td><input type="file" name="file" /></td>
						<td style="color: red; font-style: italic;"><form:errors
								path="file" /></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Upload" /></td>
						<td></td>
					</tr>
				</table>
			</form:form>
		</c:when>
		<c:when test="${uploaded == true }">
			<h3><span class="label label-success">Success</span></h3>
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
		</c:when>
	</c:choose>
</div>