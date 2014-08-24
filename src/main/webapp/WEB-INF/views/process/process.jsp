<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>

<div class="page-header text-center">
	<h1>
		<spring:message code="process.h1.processing" />
		<small><spring:message code="process.small.analize" /></small>
	</h1>
</div>

<style>
div.clear {
	clear: both;
}

div.product-chooser {
	
}

div.product-chooser.disabled div.product-chooser-item {
	zoom: 1;
	filter: alpha(opacity = 60);
	opacity: 0.6;
	cursor: default;
}

div.product-chooser div.product-chooser-item {
	padding: 11px;
	border-radius: 6px;
	cursor: pointer;
	position: relative;
	border: 1px solid #efefef;
	margin-bottom: 10px;
	margin-left: 10px;
	margin-right: 10x;
}

div.product-chooser div.product-chooser-item.selected {
	border: 4px solid #428bca;
	background: #efefef;
	padding: 8px;
	filter: alpha(opacity = 100);
	opacity: 1;
}

div.product-chooser div.product-chooser-item img {
	padding: 0;
}

div.product-chooser div.product-chooser-item span.title {
	display: block;
	margin: 10px 0 5px 0;
	font-weight: bold;
	font-size: 12px;
}

div.product-chooser div.product-chooser-item span.description {
	font-size: 12px;
}

div.product-chooser div.product-chooser-item input.non-visible {
	position: absolute;
	left: 0;
	top: 0;
	visibility: hidden;
}
</style>

<script>
	$(function() {
		$('div.product-chooser').not('.disabled').find(
				'div.product-chooser-item').on(
				'click',
				function() {
					$(this).parent().parent().find('div.product-chooser-item')
							.removeClass('selected');
					$(this).addClass('selected');
					$(this).find('input[type="radio"]').prop("checked", true);

				});
	});
</script>

<div class="container">
	<c:if test="${error != null}">
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="alert alert-danger alert-dismissable">
					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">&times;</button>
					<strong>Error! </strong> <spring:message code="process.error.algorithm" /> ${error }.
					<spring:hasBindErrors htmlEscape="true" name="result">
						<c:forEach items="${result.allErrors}" var="error">
							<spring:message code="${error.code}"
								arguments="${error.arguments}" text="${error.defaultMessage}" />
							<br />
						</c:forEach>
					</spring:hasBindErrors>
				</div>
			</div>
		</div>
	</c:if>
	<div class="row form-group product-chooser">
		<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
			<div class="product-chooser-item selected">
				<%@include file="includes/ripperForm.jsp"%>
			</div>
		</div>
		<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
			<div class="product-chooser-item">
				<%@include file="includes/subgroupForm.jsp"%>
			</div>
		</div>
		<div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
			<div class="product-chooser-item">
				<%@include file="includes/tree2rulesForm.jsp"%>
			</div>
		</div>
	</div>
</div>
