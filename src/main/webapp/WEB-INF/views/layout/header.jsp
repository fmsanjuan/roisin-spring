<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<nav class="navbar navbar-default navbar-static-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/">Roisin</a>
		</div>
		<ul class="nav navbar-nav">
			<security:authorize access="hasRole('USER')">
				<li><a href="/file/list">My Files</a></li>
				<li><a href="/converter/create">Format Converter</a></li>
			</security:authorize>
		</ul>
		<ul class="nav navbar-nav navbar-right">

			<security:authorize access="hasRole('ADMIN')">
				<li><a href="#"><security:authentication
							property="principal.username" /></a></li>
			</security:authorize>
			<security:authorize access="hasRole('USER')">
				<li><a href="#"><security:authentication
							property="principal.username" /></a></li>
			</security:authorize>
			<security:authorize access="isAuthenticated()">
				<li><a href="/j_spring_security_logout">Logout</a></li>
			</security:authorize>
			<li><a href="#">Help</a></li>
		</ul>
	</div>
</nav>