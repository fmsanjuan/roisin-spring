<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form method="post" class="form-horizontal"
	action="../process/ripper" role="form" modelAttribute="ripperSettings">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="process" />

	<div class="col-xs-8 col-sm-8 col-md-12 col-lg-12">
		<h3 class="text-center">Ripper</h3>
		<br /> <span class="description">Lorem ipsum dolor sit amet,
			consectetuer adipiscing elit. Aenean commodo ligula eget dolor.
			Aenean massa. Cum sociis natoque penatibus et magnis dis parturient
			montes, nascetur ridiculus mus. Donec quam felis, ultricies nec,
			pellentesque eu, pretium quis, sem.</span> <input type="radio"
			name="product" value="mobile_desktop" checked="checked"
			class="non-visible">
	</div>
	<div class="clear"></div>
	<br />
	<div class="row text-center">
		<c:choose>
			<c:when test="${error !=null && error=='Ripper' }">
				<a href="#aboutRipperModal" data-toggle="modal"
					data-target="#ripperModal"><button type="button"
						class="btn btn-danger">
						<spring:message code="process.advanced.settings" />
					</button> </a>
			</c:when>
			<c:otherwise>
				<a href="#aboutRipperModal" data-toggle="modal"
					data-target="#ripperModal"><button type="button"
						class="btn btn-default">
						<spring:message code="process.advanced.settings" />
					</button> </a>
			</c:otherwise>
		</c:choose>
	</div>
	<br />
	<div class="row text-center">
		<form:button type="submit" name="ripper" class="btn btn-default">
			<spring:message code="process.ripper.run" />
		</form:button>
	</div>
	<div class="modal fade" id="ripperModal" tabindex="-1" role="dialog"
		aria-labelledby="ripperModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h4 class="modal-title" id="ripperModalLabel">
						<spring:message code="process.ripper.advanced.settings" />
					</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<spring:message code="process.ripper.criterion" var="criterion" />
						<label for="ripperCriterionSelect"
							class="col-sm-4 col-sm-offset-1 control-label">${criterion }</label>
						<div class="col-sm-4">
							<form:select class="form-control" id="ripperCriterionSelect"
								path="ripperCriterion">
								<form:option value="information_gain" selected="selected" />
								<form:option value="accuracy" />
							</form:select>
							<font color="red"><form:errors path="ripperCriterion" /></font>
						</div>
					</div>
					<div class="form-group">
						<spring:message code="process.ripper.sample.ratio"
							var="sampleRatio" />
						<label for="ripperSampleRatio"
							class="col-sm-4 col-sm-offset-1 control-label">${sampleRatio }</label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control"
								id="ripperSampleRatio" placeholder="${sampleRatio }"
								path="sampleRatio" />
							<font color="red"><form:errors path="sampleRatio" /></font>
						</div>
					</div>
					<div class="form-group">
						<spring:message code="process.ripper.pureness" var="pureness" />
						<label for="ripperPureness"
							class="col-sm-4 col-sm-offset-1 control-label">${pureness }</label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control" id="ripperPureness"
								placeholder="${pureness }" path="pureness" />
							<font color="red"><form:errors path="pureness" /></font>
						</div>
					</div>
					<div class="form-group">
						<spring:message code="process.ripper.minimal.prune.benefit"
							var="mPruneBenefit" />
						<label for="ripperMinimalPruneBenefit"
							class="col-sm-4 col-sm-offset-1 control-label">${mPruneBenefit }</label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control"
								id="ripperMinimalPruneBenefit" placeholder="${mPruneBenefit }"
								path="minimalPruneBenefit" />
							<font color="red"><form:errors path="minimalPruneBenefit" /></font>
						</div>
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						<spring:message code="process.go.back" />
					</button>
					<form:button type="submit" name="ripper" class="btn btn-default">
						<spring:message code="process.ripper.run" />
					</form:button>
				</div>
			</div>
		</div>
	</div>
</form:form>