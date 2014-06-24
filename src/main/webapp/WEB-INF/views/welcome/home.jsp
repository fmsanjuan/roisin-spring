<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false"%>

<style>
.jumbotron {
	margin-bottom: 0px;
	background-image: url(resources/images/rosas-background.jpg);
	background-position: 0% 25%;
	background-size: cover;
	background-repeat: no-repeat;
	color: white;
	text-shadow: black 0.3em 0.3em 0.3em;
}
</style>

<div class="page-header text-center">
	<h1>
		<spring:message code="welcome.hello" />
		<small><spring:message code="welcome.hello.description" /></small>
	</h1>
</div>


<div class="jumbotron">
	<div class="container">
		<br /> <br /> <br /> <br /> <br /> <br /> <br /> <br />
		<br />
		<br /><br/><br/><br/><br/><br/>
		<br />
		<br />
	</div>
</div>
<br/>
<p class="text-center">
	<spring:message code="welcome.time" />
	${serverTime}.
</p>