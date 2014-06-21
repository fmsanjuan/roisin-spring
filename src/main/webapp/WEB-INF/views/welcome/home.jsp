<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page session="false" %>

<div class="page-header text-center">
  <h1><spring:message code="welcome.hello" /> <small><spring:message code="welcome.hello.description" /></small></h1>
</div>

<p class="text-center">  <spring:message code="welcome.time" /> ${serverTime}. </p>
