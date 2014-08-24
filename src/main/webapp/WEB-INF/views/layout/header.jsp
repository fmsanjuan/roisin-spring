<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	

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
				<li><a href="/file/list"><spring:message code="my.files" /></a></li>
				<li><a href="/converter/create"><spring:message code="format.converter" /></a></li>
			</security:authorize>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<security:authorize access="isAuthenticated()">
				<li class="dropdown"><a class="fNiv dropdown-toggle"
					data-toggle="dropdown"> <security:authentication
							property="principal.username" />
				</a>
					<ul class="dropdown-menu">
						<li class="arrow"></li>
						<li><a href="/signup/edit"><spring:message code="edit.profile" /></a></li>
						<li><a href="/j_spring_security_logout"><spring:message code="sign.out" /></a></li>
					</ul></li>
			</security:authorize>
			<li><a href="#"><spring:message code="help" /></a></li>
			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown"><spring:message code="language" /> <b class="caret"></b></a>
				<ul class="dropdown-menu">
					<li><a href="/?language=en">EN</a></li>
					<li><a href="/?language=es">ES</a></li>
				</ul></li>
		</ul>
	</div>
</nav>