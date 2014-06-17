<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<h1>
	<spring:message code="preprocessing.header" />
</h1>

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
