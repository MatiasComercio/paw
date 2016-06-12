<%@ include file="base/tags.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<title>
		<spring:message code="webAbbreviation"/> | <spring:message code="changePassword"/>
	</title>
	<jsp:include page="base/head.jsp" />
</head>
<body>

<div id="wrapper">

	<jsp:include page="base/sections.jsp" />
	<jsp:include page="base/nav.jsp" />

	<div id="page-wrapper">

		<div class="container-fluid">

			<!-- Page Heading -->
			<div class="row">
				<div class="col-xs-12">
					<h1 class="page-header">
						<spring:message code="changePassword"/>
					</h1>
				</div>
			</div>

			<jsp:include page="base/alerts.jsp" />

			<!-- content -->
			<div class="row">
				<c:url value="/user/changePassword" var="changePasswordProcessingUrl"/>
				<form:form modelAttribute="changePasswordForm" action="${changePasswordProcessingUrl}"
						   method="post" enctype="application/x-www-form-urlencoded">

                    <div class="col-xs-12 form-group">
                        <%--<form:label path="dni"><spring:message code="dni" /></form:label>--%>
                        <form:input type="hidden" path="dni" class="form-control" value="${dni}"/>
                    </div>
                    <%--
						Disable chrome autocomplete
						source: http://stackoverflow.com/questions/15738259/disabling-chrome-autofill#answer-30976223
					--%>
					<div class="col-xs-12 form-group">
						<form:label path="currentPassword"><spring:message code="currentPassword" /></form:label>
						<form:input type="password" path="currentPassword" class="form-control" autocomplete="new-password"/>
						<form:errors path="currentPassword" cssClass="text-danger bg-danger" element="div"/>
					</div>
					<div class="col-xs-12 form-group">
						<form:label path="newPassword"><spring:message code="newPassword" /></form:label>
						<form:input type="password" path="newPassword" class="form-control"/>
						<form:errors path="newPassword" cssClass="text-danger bg-danger" element="div"/>
					</div>
					<div class="col-xs-12 form-group">
						<form:label path="repeatNewPassword"><spring:message code="repeatNewPassword" /></form:label>
						<form:input type="password" path="repeatNewPassword" class="form-control"/>
						<form:errors path="repeatNewPassword" cssClass="text-danger bg-danger" element="div"/>
					</div>
					<div class="col-sm-2 hidden-xs"></div>
					<div class="col-xs-6 col-sm-4 text-center">
						<button type="submit" class="btn btn-info center-block fullWidthButton">
							<spring:message code="changePassword"/>
						</button>
					</div>
					<div class="col-xs-6 col-sm-4 text-center">
						<button id="cancelButton" class="btn btn-default center-block fullWidthButton">
							<spring:message code="cancel"/>
						</button>
					</div>
				</form:form>
			</div>

			<!-- /content -->

		</div>
		<!-- /.container-fluid -->

	</div>
	<!-- /#page-wrapper -->
	<jsp:include page="base/footer.jsp" />
</div>
<!-- Scripts -->
<script charset="UTF-8">
	<%@include file='/WEB-INF/js/cancelButton.js'%>
</script>
<script>
	$(document).ready(function() {
		loadCancelButton("cancelButton");
	});
</script>
</body>
</html>

