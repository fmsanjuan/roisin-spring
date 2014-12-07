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
		<spring:message code="welcome.password.recovery" />
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
		<c:if test="${message != null}">
			<div class="row">
				<div
					class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3 alert alert-danger alert-dismissable">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">&times;</button>
					<spring:message code="${message}" />
				</div>
			</div>
		</c:if>
		<c:choose>
			<c:when test="${success == true}">
				<div
					class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3 alert alert-info">
					<spring:message code="welcome.recover.request.email.sent" />
				</div>
			</c:when>
			<c:otherwise>
				<div class="row" style="margin-top: 20px">
					<div
						class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
						<form:form action="/security/request/recovery" modelAttribute="form" role="form" method="post">
							<fieldset>
								<h2>Email</h2>
								<hr class="colorgraph">
								<div class="form-group">
									<spring:message code="welcome.email" var="email" />
									<form:input path="email" type="email" name="email"
										id="email" class="form-control input-lg"
										placeholder="${email }" />
								</div>
								<div class="row">
									<div class="col-xs-12 col-sm-12 col-md-12">
										<spring:message code="welcome.request.password.recovery" var="recoverPassword" />
										<input type="submit" class="btn btn-lg btn-success btn-block"
											value="${recoverPassword }" />
									</div>
								</div>
							</fieldset>
						</form:form>
					</div>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	<br />
	<br />
</security:authorize>

<security:authorize access="hasRole('USER')">
	<div class="container">
		<div class="col-md-4 col-md-offset-4">
		</div>
	</div>
</security:authorize>

<br />
