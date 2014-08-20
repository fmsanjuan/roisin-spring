<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>

<div class="page-header text-center">
	<h1>
		<spring:message code="welcome.hello" />
		<small><spring:message code="welcome.hello.description" /></small>
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
		<c:if test="${successMessage != null}">
			<div
				class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3 alert alert-success alert-dismissable">
				<button type="button" class="close" data-dismiss="alert"
					aria-hidden="true">&times;</button>
				${successMessage }
			</div>
		</c:if>
		<div class="row" style="margin-top: 20px">
			<div
				class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
				<form:form action="/j_spring_security_check"
					modelAttribute="credentials" role="form">
					<fieldset>
						<h2>Sign In</h2>
						<hr class="colorgraph">
						<div class="form-group">
							<form:input path="username" type="text" name="username"
								id="username" class="form-control input-lg" placeholder="Email" />
							<form:errors class="error" path="username" />
						</div>
						<div class="form-group">
							<form:input path="password" type="password" name="password"
								id="password" class="form-control input-lg"
								placeholder="Password" />
							<form:errors class="error" path="username" />
						</div>
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
								<input type="submit" class="btn btn-lg btn-success btn-block"
									value="Sign In" />
							</div>
						</div>
						<br />
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12">
								<a href="/signup/"> <input
									class="btn btn-lg btn-primary btn-block" type="button"
									value="Sign Up" onclick="self.location.href = /signup/" /></a>
							</div>
						</div>
						<br />
						<c:if test="${showError == true}">
							<div class="alert alert-danger alert-dismissable">
								Incorrect username or password.</div>
						</c:if>
						<c:if test="${showError == false}">
							<br />
							<br />
							<br />
							<br />
							<br />
							<br />
						</c:if>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
	<br />
	<br />
</security:authorize>

<security:authorize access="hasRole('USER')">
	<div class="container">
		<div class="col-md-4 col-md-offset-4">
			<br />
			<h2 class="text-center">Association rules</h2>
			<br />
			<p class="text-center">
				If you want to start processing your data to get association rules.
				<b>First things first!</b> Upload or use a file you're already
				uploaded
			</p>
			<a href="/file/list"> <input
				class="btn btn-lg btn-primary btn-block" type="button"
				value="My Files" onclick="self.location.href = /file/list" /></a> <br />
			<h2 class="text-center">Data conversion</h2>
			<br />
			<p class="text-center">If you just want to convert your files.</p>

			<a href="/converter/create"> <input
				class="btn btn-lg btn-primary btn-block" type="button"
				value="Data Converter"
				onclick="self.location.href = /converter/create" /></a>
		</div>
	</div>
</security:authorize>

<br />
