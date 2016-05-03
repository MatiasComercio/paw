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
    <jsp:include page="template/CorrelativeForm.jsp" />
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
                <div class="col-xs-12">
                    <jsp:include page="base/alerts.jsp" />
                </div>
            </div>
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
                <div class="col-xs-12 col-md-4">
                    <div class="row">
                        <div class="col-xs-6 col-md-6">
                            <a href="<c:url value="/courses/${course.id}/students" />" type="button" class="btn btn-info col_vertical_margin" role="button">
                                <i class="fa fa-users" aria-hidden="true"></i> <spring:message code="students"/></a>
                        </div>
                        <div class="col-xs-6 col-md-6 text-center">
                            <a href="<c:url value="/courses/${course.id}/add_correlative" />" type="button" class="btn btn-info col_vertical_margin" role="button">
                                <i class="fa fa-fw fa-list-alt"></i> <spring:message code="add_correlatives"/></a>
                        </div>
                    </div>
                </div>
            </div>

            <table class="table table-striped">
                <thead>
                <tr>
                    <th><spring:message code="id"/></th>
                    <th><spring:message code="course"/></th>
                    <th><spring:message code="credits"/></th>
                    <th><spring:message code="actions"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${correlatives}" var="correlative">
                    <tr>
                        <td>${ correlative.id }</td>
                        <td>${ correlative.name }</td>
                        <td>${ correlative.credits }</td>
                        <td>
                            <a class="btn btn-default btn-xs" href="<c:url value="/courses/${correlative.id}/info" />" role="button">
                                <span class="fa fa-info-circle" aria-hidden="true"></span> <spring:message code="see"/> <spring:message code="course"/>
                            </a>
                            <button name="deleteCorrelativeButton" class="btn btn-danger btn-xs" type="button"
                                    data-course_id="${ course.id }" data-course_name="${ course.name }"
                                    data-correlative_id="${correlative.id}" data-correlative_name="${correlative.name}"
                                    data-toggle="modal" data-target="#correlativeFormConfirmationModal">
                                <span class="fa fa-trash" aria-hidden="true"></span> <spring:message code="delete"/>
                            </button>
                        <td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

            <!-- Content -->
        </div>
    </div>
</div>
<!-- Scripts -->
<jsp:include page="base/footer.jsp" />
<script type="text/javascript" charset="UTF-8"><%@include file="../js/template/addCorrelativeForm.js"%></script>
<script>
    $( document ).ready(function() {
        loadCorrelativeForm("deleteCorrelativeButton");
    });
</script>

</body>
</html>
