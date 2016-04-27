<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | ${course.name}
    </title>
    <jsp:include page="base/head.jsp" />
    <link href="<c:url value="/static/css/course-detail.css" />" rel="stylesheet" type="text/css"/>
</head>

<body>
<div id="wrapper">
    <jsp:include page="base/nav.jsp" />

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                        ${course.name} <small> - <spring:message code="information"/></small>
                    </h1>
                </div>
            </div>
            <!-- Content -->
            <div class="row">
                <div class="col-xs-12 col-md-6">
                    <div class="well">
                        <div class="row">

                            <div class="row">
                                <div class="col-xs-3 right-effect">
                                    <strong><spring:message code="id"/></strong>
                                </div>
                                <div class="col-xs-9">
                                    ${course.id}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-3 right-effect">
                                    <strong><spring:message code="name"/></strong>
                                </div>
                                <div class="col-xs-9">
                                    ${course.name}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-3 right-effect">
                                    <strong><spring:message code="credits"/></strong>
                                </div>
                                <div class="col-xs-9">
                                    ${course.credits}
                                </div>
                            </div>
                            <div class="col-xs-12 text-center">
                                <a class="btn btn-info" href="<c:url value="/courses/${course.id}/edit"/>">
                                    <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="edit"/>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12 col-md-6">
                    <div class="row">
                        <div class="col-xs-12 col-md-6">
                            <a href="<c:url value="/courses/${course.id}/students" />" type="button" class="btn btn-info" role="button">
                                <i class="fa fa-users" aria-hidden="true"></i> <spring:message code="students"/></a>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Content -->
        </div>
    </div>
</div>
<!-- Scripts -->
<jsp:include page="base/footer.jsp" />
</body>
</html>
