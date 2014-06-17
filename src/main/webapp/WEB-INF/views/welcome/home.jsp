<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false" %>
<h1>
	<spring:message code="welcome.hello" /> <spring:message code="welcome.hello.description" />
</h1>

<p>  <spring:message code="welcome.time" /> ${serverTime}. </p>

<a href="preprocessing/create">A procesamiento</a>
