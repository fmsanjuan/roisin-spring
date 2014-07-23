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

		<div class="row" style="margin-top: 20px">
			<div
				class="col-xs-12 col-sm-8 col-md-6 col-sm-offset-2 col-md-offset-3">
				<form:form action="j_spring_security_check"
					modelAttribute="credentials" role="form">
					<fieldset>
						<h2>SIGN IN</h2>
						<hr class="colorgraph">
						<div class="form-group">
							<input path="username" type="text" name="username" id="username"
								class="form-control input-lg" placeholder="Username">
							<form:errors class="error" path="username" />
						</div>
						<div class="form-group">
							<input path="password" type="password" name="password"
								id="password" class="form-control input-lg"
								placeholder="Password">
							<form:errors class="error" path="username" />
						</div>
						<br />
						<div class="row">

							<div class="col-xs-12 col-sm-12 col-md-12">
								<input type="submit" class="btn btn-lg btn-success btn-block"
									value="Sign In" />
							</div>
						</div>
						<br />
					</fieldset>
				</form:form>
			</div>
		</div>



	</div>

	<br />
	<br />
	<br />

</security:authorize>

<security:authorize access="hasRole('USER')">
Hey there!
<br />
	<a href="data/list">My data</a>
</security:authorize>

<br />
<p class="text-center">
	<spring:message code="welcome.time" />
	${serverTime}.
</p>
