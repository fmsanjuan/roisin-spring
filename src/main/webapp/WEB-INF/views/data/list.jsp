<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ page session="false"%>


<div class="page-header text-center">
	<h1>
		Preprocessed Data from  <small>${file.getName() }</small>
	</h1>
</div>

<div class="container">
	<div class="row">
		<display:table uid="dataTable" keepStatus="false" name="forms"
			pagesize="5" class="table table-hover" requestURI="${requestURI}"
			id="row">
			<display:column property="name" title="Name" />
			<display:column property="description" title="Description" />
			<display:column>
				<a href="../data/details?dataId=${row.id}"> <input
					class="btn btn-default" type="button" value="Details"
					onclick="self.location.href = ../data/details?dataId=${row.id}" />
				</a>
			</display:column>
		</display:table>
	</div>
</div>
