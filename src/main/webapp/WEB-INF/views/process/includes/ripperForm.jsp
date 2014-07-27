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
	<c:choose>
		<c:when test="${error !=null && error=='Ripper' }">
			<a href="#aboutRipperModal" data-toggle="modal"
				data-target="#ripperModal"><button type="button"
					class="btn btn-danger">Advanced Settings</button>
				</button></a>
		</c:when>
		<c:otherwise>
			<a href="#aboutRipperModal" data-toggle="modal"
				data-target="#ripperModal"><button type="button"
					class="btn btn-default">Advanced Settings</button>
				</button></a>
		</c:otherwise>
	</c:choose>
	<form:button type="submit" name="ripper" class="btn btn-default">Run Ripper</form:button>
	<div class="modal fade" id="ripperModal" tabindex="-1" role="dialog"
		aria-labelledby="ripperModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true"></button>
					<h4 class="modal-title" id="ripperModalLabel">Ripper Advanced
						Settings</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="ripperCriterionSelect"
							class="col-sm-4 col-sm-offset-1 control-label">Criterion</label>
						<div class="col-sm-4">
							<form:select class="form-control" id="ripperCriterionSelect"
								path="ripperCriterion">
								<form:option value="information_gain" selected="selected" />
								<form:option value="accuracy" />
							</form:select>
							<form:errors path="ripperCriterion" />
						</div>
					</div>
					<div class="form-group">
						<label for="ripperSampleRatio"
							class="col-sm-4 col-sm-offset-1 control-label">Sample
							Ratio</label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control"
								id="ripperSampleRatio" placeholder="Sample Ratio"
								path="sampleRatio" />
							<form:errors path="sampleRatio" />
						</div>
					</div>
					<div class="form-group">
						<label for="ripperPureness"
							class="col-sm-4 col-sm-offset-1 control-label">Pureness</label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control" id="ripperPureness"
								placeholder="Pureness" path="pureness" />
							<form:errors path="pureness" />
						</div>
					</div>
					<div class="form-group">
						<label for="ripperMinimalPruneBenefit"
							class="col-sm-4 col-sm-offset-1 control-label">Minimal
							Prune Benefit</label>
						<div class="col-sm-4">
							<form:input type="text" class="form-control"
								id="ripperMinimalPruneBenefit"
								placeholder="Minimal Prune Benefit" path="minimalPruneBenefit" />
							<form:errors path="minimalPruneBenefit" />
						</div>
					</div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Go
						Back</button>
					<form:button type="submit" name="ripper" class="btn btn-default">Run Ripper</form:button>
				</div>
			</div>
		</div>
	</div>
</form:form>