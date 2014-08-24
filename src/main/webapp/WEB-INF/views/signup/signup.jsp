<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>

<div class="page-header text-center">
	<spring:message code="sign.h1.sign" var="signUp" />
	<h1>
		${signUp } <small><spring:message code="sign.small.start" /></small>
	</h1>
</div>

<style>
.colorgraph {
	height: 5px;
	border-top: 0;
	background: #993232;
	border-radius: 5px;
}
</style>

<div class="container">
	<div class="row" style="margin-top: 20px">
		<div
			class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
			<form:form action="save" modelAttribute="form" role="form"
				method="post">
				<fieldset>
					<c:if test="${error == true}">
						<div class="row">
							<div class="alert alert-danger alert-dismissable">
								<button type="button" class="close" data-dismiss="alert"
									aria-hidden="true">&times;</button>
								<strong>Error! </strong><br /> <c:if test="${errorMessage !=null }"><spring:message code="${errorMessage }" /></c:if>
								<form:errors path="*" />
							</div>
						</div>
					</c:if>
					<hr class="colorgraph">
					<div class="form-group">
						<spring:message code="sign.name" var="name" />
						<form:input path="name" type="text" name="name" id="name"
							class="form-control input-lg" placeholder="${name }" />
					</div>
					<div class="form-group">
						<spring:message code="sign.surname" var="surname" />
						<form:input path="surname" type="text" name="surname" id="surname"
							class="form-control input-lg" placeholder="${surname }" />
					</div>
					<div class="form-group">
						<form:input path="email" type="email" name="email" id="email"
							class="form-control input-lg" placeholder="Email" />
					</div>
					<hr class="colorgraph">
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<spring:message code="sign.password" var="password" />
								<form:input path="password" type="password" name="password"
									id="password" class="form-control input-lg"
									placeholder="${password }" />
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<spring:message code="sign.repeat.password" var="repeatPassword" />
								<form:input path="repeatPassword" type="password"
									name="repeatPassword" id="repeatPassword"
									class="form-control input-lg" placeholder="${repeatPassword }" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<spring:message code="sign.city" var="city" />
							<div class="form-group">
								<form:input path="city" type="text" name="city" id="city"
									class="form-control input-lg" placeholder="${city }" />
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<spring:message code="sign.nationality" var="nationality" />
								<form:input path="nationality" type="text" name="nationality"
									id="nationality" class="form-control input-lg"
									placeholder="${nationality }" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<input type="submit" class="btn btn-lg btn-success btn-block"
								value="${signUp }" />
						</div>
					</div>
				</fieldset>
			</form:form>
		</div>
	</div>
</div>
<br />
