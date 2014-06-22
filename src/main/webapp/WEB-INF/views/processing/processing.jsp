<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>

<div class="page-header text-center">
	<h1>
		<spring:message code="processing.header" />
		<small>Analize your data</small>
	</h1>
</div>

<div class="container">
	<div class="row">
		<h3 class="text-center">Choose an algorithm</h3>
	</div>
	<br /> <br /> <br />
	<div class="row">
		<div class="col-md-2 col-md-offset-1">
			<form:form method="post" modelAttribute="form" action="ripper">
				<form:button type="submit" class="btn btn-primary btn-lg">Ripper</form:button>
			</form:form>
		</div>
		<div class="col-md-2 col-md-offset-2">
			<form:form method="post" modelAttribute="form" action="subgroup">
				<form:button type="submit" class="btn btn-primary btn-lg">Subgroup Discovery</form:button>
			</form:form>
		</div>
		<div class="col-md-2 col-md-offset-2">
			<form:form method="post" modelAttribute="form" action="tree">
				<form:button type="submit" class="btn btn-primary btn-lg">Tree to Rules</form:button>
			</form:form>
		</div>
	</div>
</div>
