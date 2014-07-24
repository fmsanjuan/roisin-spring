<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ page session="false"%>


<div class="page-header text-center">
	<h1>
		My Data
		<small>Select your data</small>
	</h1>
</div>

<display:table uid="dataTable" keepStatus="false" name="data"
	pagesize="5" class="table table-hover" requestURI="${requestURI}"
	id="row">
	<display:column property="name" title="Name" sortable="true" />
	<display:column property="description" title="Description"
		sortable="true" />
</display:table>

