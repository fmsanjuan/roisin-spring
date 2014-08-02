<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ page session="false"%>


<div class="page-header text-center">
	<h1>
		Data Details <small> Preprocessing results</small>
	</h1>
</div>
<div class="container">
	<div class="row">
		<display:table uid="examplesTable" keepStatus="false" name="examples"
			pagesize="10" class="table table-hover"
			requestURI="${requestURI}" id="row">
			<display:column title="#">
				<c:out value="${row_rowNum}" />
			</display:column>
			<c:forEach items="${attributes }" var="attribute">
				<display:column title="${attribute.getName()}">
				${row.getValueAsString(attribute) } 
					</display:column>
			</c:forEach>
		</display:table>
	</div>
</div>