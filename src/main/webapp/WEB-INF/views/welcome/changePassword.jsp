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
		<c:choose>
			<c:when test="${success == true}">
				<div
					class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3 alert alert-success">
					<spring:message code="welcome.password.changed"></spring:message>
				</div>
			</c:when>
			<c:otherwise>
				<c:if test="${message != null}">
					<div class="row">
						<div class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3 alert alert-danger alert-dismissable">
							<button type="button" class="close" data-dismiss="alert"
								aria-hidden="true">&times;</button>
							<spring:message code="${message}" />
						</div>
					</div>
				</c:if>
				<div class="row" style="margin-top: 20px">
					<div
						class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
						<form:form action="/security/change" modelAttribute="form" role="form" method="post">
							<form:hidden path="key"/>
							<form:hidden path="userAccountId"/>
							<fieldset>
								<h2><spring:message code="welcome.new.password" /></h2>
								<hr class="colorgraph">
								<div class="form-group">
									<spring:message code="welcome.new.password" var="newPassword" />
									<form:input path="newPassword" type="password" name="newPassword"
										id="newPassword" class="form-control input-lg"
										placeholder="${newPassword }" />
								</div>
								<div class="form-group">
									<spring:message code="welcome.repeat.new.password" var="repeatNewPassword" />
									<form:input path="repeatNewPassword" type="password" name="repeatNewPassword"
										id="repeatNewPassword" class="form-control input-lg"
										placeholder="${repeatNewPassword }" />
								</div>
								<div class="row">
									<div class="col-xs-12 col-sm-12 col-md-12">
										<spring:message code="welcome.change.password" var="changePassword" />
										<input type="submit" class="btn btn-lg btn-success btn-block"
											value="${changePassword }" />
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
			Forgot your password while logged in?
		</div>
	</div>
</security:authorize>

<br />
