<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>

<div class="page-header text-center">
	<h1>
		<spring:message code="welcome.activation" />
	</h1>
</div>


<security:authorize access="isAnonymous()">
	<style>
.colorgraph {
	height: 5px;
	border-top: 0;
	background: #993232;
	border-radius: 5px;
}
</style>
	<div class="container">
		<c:choose>
			<c:when test="${activationSuccess == true}">
				<div
					class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3 alert alert-success">
					<spring:message code="${message}"></spring:message>
				</div>
			</c:when>
			<c:when test="${activationSuccess == false || notActivated == true}">
				<div
					class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3 alert alert-danger">
					<spring:message code="${message}"></spring:message>
				</div>
			</c:when>
		</c:choose>
	</div>
	<br />
	<br />
</security:authorize>

<security:authorize access="hasRole('USER')">
	<div class="container">
		<div class="col-md-4 col-md-offset-4">
			<div
				class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3 alert alert-info">
				<spring:message code="welcome.activation.signed.in" />
			</div>
		</div>
	</div>
</security:authorize>

<br />
