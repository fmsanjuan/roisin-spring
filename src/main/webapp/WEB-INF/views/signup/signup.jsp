<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>

<div class="page-header text-center">
	<h1>
		Sign Up <small>Start using Roisin</small>
	</h1>
</div>


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
								<strong>Error! </strong>
								${errorMessage }
								<form:errors path="name" />
								<form:errors path="surname" />
								<form:errors path="email" />
								<form:errors path="password" />
								<form:errors path="city" />
								<form:errors path="nationality" />
							</div>
						</div>
					</c:if>
					<hr class="colorgraph">
					<div class="form-group">
						<form:input path="name" type="text" name="name" id="name"
							class="form-control input-lg" placeholder="Name" />
						<form:errors class="error" path="name" />
					</div>
					<div class="form-group">
						<form:input path="surname" type="text" name="surname" id="surname"
							class="form-control input-lg" placeholder="Surname" />
						<form:errors class="error" path="surname" />
					</div>
					<div class="form-group">
						<form:input path="email" type="email" name="email" id="email"
							class="form-control input-lg" placeholder="Email" />
						<form:errors class="error" path="email" />
					</div>
					<div class="form-group">
						<form:input path="password" type="password" name="password"
							id="password" class="form-control input-lg"
							placeholder="Password" />
						<form:errors class="error" path="password" />
					</div>
					<div class="form-group">
						<form:input path="repeatPassword" type="password"
							name="repeatPassword" id="repeatPassword"
							class="form-control input-lg" placeholder="Repeat Password" />
						<form:errors class="error" path="repeatPassword" />
					</div>
					<div class="form-group">
						<form:input path="city" type="text" name="city" id="city"
							class="form-control input-lg" placeholder="City" />
						<form:errors class="error" path="city" />
					</div>
					<div class="form-group">
						<form:input path="nationality" type="text" name="nationality"
							id="nationality" class="form-control input-lg"
							placeholder="Nationality" />
						<form:errors class="error" path="nationality" />
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<input type="submit" class="btn btn-lg btn-success btn-block"
								value="Sign In" />
						</div>
					</div>
				</fieldset>
			</form:form>
		</div>
	</div>
</div>



<br />
