<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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

			<!-- content -->
			<div class="row">
				<div class="col-xs-12">
					<jsp:include page="base/alerts.jsp" />
				</div>
			</div>
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
						<form:errors path="currentPassword" cssStyle="color: red;" element="div"/>
					</div>
					<div class="col-xs-12 form-group">
						<form:label path="newPassword"><spring:message code="newPassword" /></form:label>
						<form:input type="password" path="newPassword" class="form-control"/>
						<form:errors path="newPassword" cssStyle="color: red;" element="div"/>
					</div>
					<div class="col-xs-12 form-group">
						<form:label path="repeatNewPassword"><spring:message code="repeatNewPassword" /></form:label>
						<form:input type="password" path="repeatNewPassword" class="form-control"/>
						<form:errors path="repeatNewPassword" cssStyle="color: red;" element="div"/>
					</div>
					<div class="col-sm-4 hidden-xs"></div>
					<div class="col-xs-6 col-sm-2 text-center">
						<button id="cancelChangePassword" class="btn btn-default center-block">
							<spring:message code="cancel"/>
						</button>
					</div>
					<div class="col-xs-6 col-sm-2 text-center">
						<button type="submit" class="btn btn-info center-block">
							<spring:message code="changePassword"/>
						</button>
					</div>
				</form:form>
			</div>

			<!-- /content -->

		</div>
		<!-- /.container-fluid -->

	</div>
	<!-- /#page-wrapper -->

</div>
<!-- Scripts -->
<jsp:include page="base/footer.jsp" />
<script>
	$(document).ready(function() {
		$("#cancelChangePassword").on("click", function() {
			parent.history.back();
			return false;
		});
	});
</script>
</body>
</html>

