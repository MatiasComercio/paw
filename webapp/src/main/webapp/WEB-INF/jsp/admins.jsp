<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | <spring:message code="admins"/>
    </title>
    <jsp:include page="base/head.jsp" />
</head>
<body>

<div id="wrapper">

    <jsp:include page="base/nav.jsp" />
    <jsp:include page="template/enableInscriptionsForm.jsp" />

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-xs-12">
                    <h1 class="page-header">
                        <spring:message code="admins"/>
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
                <div class="col-xs-12 col-md-2 text-center">
                    <p class="lead"><spring:message code="actions"/>:</p>
                </div>
                <div class="col-xs-12 col-md-3 text-center">
                    <a class="btn btn-info" href="<c:url value="/admins/add_admin"/>" role="button">
                        <i class="fa fa-plus-circle" aria-hidden="true"></i> <spring:message code="addAdmin"/>
                    </a>
                </div>
                <div class="col-xs-12 col-md-3 text-center">
                    <c:choose>
                        <c:when test="${isInscriptionEnabled}">
                            <button name="disableInscriptionsButton" class="btn btn-info" type="button"
                                    data-toggle="modal" data-target="#enableInscriptionsConfirmationModal">
                                <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="disable_inscriptions"/>
                            </button>
                        </c:when>
                        <c:when test="${isInscriptionEnabled eq false}">
                            <button name="enableInscriptionsButton" class="btn btn-info" type="button"
                                    data-toggle="modal" data-target="#enableInscriptionsConfirmationModal">
                                <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="enable_inscriptions"/>
                            </button>
                        </c:when>
                    </c:choose>
                </div>
            </div>
            <jsp:include page="template/searchAdmins.jsp" />

            <!-- /content -->

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- Scripts -->
<jsp:include page="base/footer.jsp" />
<script type="text/javascript" charset="UTF-8"><%@include file="../js/template/searchAdmins.js"%></script>
<script type="text/javascript" charset="UTF-8"><%@include file="../js/template/enableInscriptionsForm.js"%></script>
<script>
    $( document ).ready(function() {
        loadAdminSearch();
        loadEnableInscriptionsForm();
    });
</script>
</body>
</html>
