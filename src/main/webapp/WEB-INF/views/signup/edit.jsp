<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page session="false"%>

<div class="page-header text-center">
	<h1>
		Edit Profile <small>Any recent changes in your personal info?</small>
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
			<form:form action="edit" modelAttribute="form" role="form"
				method="post">
				<form:hidden path="id"/>
				<form:hidden path="version"/>
				<fieldset>
					<c:if test="${error == true}">
						<div class="row">
							<div class="alert alert-danger alert-dismissable">
								<button type="button" class="close" data-dismiss="alert"
									aria-hidden="true">&times;</button>
								<strong>Error! </strong><br /> ${errorMessage }
								<form:errors path="*" />
							</div>
						</div>
					</c:if>
					<c:if test="${successMessage != null}">
						<div class="row">
							<div class="alert alert-success alert-dismissable">
								<button type="button" class="close" data-dismiss="alert"
									aria-hidden="true">&times;</button>
								<strong>Success! </strong><br /> ${successMessage }
							</div>
						</div>
					</c:if>
					<hr class="colorgraph">
					<div class="form-group">
						<form:input path="name" type="text" name="name" id="name"
							class="form-control input-lg" placeholder="Name" />
					</div>
					<div class="form-group">
						<form:input path="surname" type="text" name="surname" id="surname"
							class="form-control input-lg" placeholder="Surname" />
					</div>
					<div class="form-group">
						<form:input path="email" type="email" name="email" id="email"
							class="form-control input-lg" placeholder="Email" />
					</div>
					<hr class="colorgraph">
					<div class="form-group">
						<form:input path="oldPassword" type="password" name="oldPassword" id="oldPassword"
							class="form-control input-lg" placeholder="Current Password" />
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<form:input path="newPassword" type="password" name="newPassword"
									id="newPassword" class="form-control input-lg"
									placeholder="New Password" />
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<form:input path="repeatNewPassword" type="password"
									name="repeatNewPassword" id="repeatNewPassword"
									class="form-control input-lg" placeholder="Repeat New Password" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<form:input path="city" type="text" name="city" id="city"
									class="form-control input-lg" placeholder="City" />
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<form:input path="nationality" type="text" name="nationality"
									id="nationality" class="form-control input-lg"
									placeholder="Nationality" />
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-12">
							<input type="submit" class="btn btn-lg btn-primary btn-block"
								value="Edit" />
						</div>
					</div>
				</fieldset>
			</form:form>
		</div>
	</div>
</div>



<br />
