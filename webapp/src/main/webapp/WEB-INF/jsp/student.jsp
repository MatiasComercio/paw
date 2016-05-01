<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | ${student.fullName} | <spring:message code="profile"/>
    </title>
    <jsp:include page="base/head.jsp" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
                        <spring:message code="profile"/>
                    </h1>
                </div>
            </div>
            <!-- Content -->
            <div class="row">
                <div class="col-xs-12">
                    <jsp:include page="base/alerts.jsp" />
                </div>
                <div class="col-xs-12 col-md-8">
                    <div class="well">
                        <div class="row">
                            <div class="col-xs-12 col-md-6">
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong><spring:message code="docket"/></strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.docket}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong><spring:message code="dni"/></strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.dni}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong><spring:message code="firstName"/></strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.firstName}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong><spring:message code="lastName"/></strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.lastName}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong><spring:message code="genre"/></strong>
                                    </div>
                                    <div class="col-xs-8">
                                        <spring:message code="${student.genre}"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong><spring:message code="birthday"/></strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.birthday}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong><spring:message code="email"/></strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.email}
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12 col-md-6">
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong><spring:message code="country"/></strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.address.country}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong><spring:message code="city"/></strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.address.city}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong><spring:message code="neighbourhood"/></strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.address.neighborhood}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong><spring:message code="street"/></strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.address.street}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong><spring:message code="number"/></strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.address.number}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong><spring:message code="floor"/></strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.address.floor}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong><spring:message code="door"/></strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.address.door}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong><spring:message code="telephone"/></strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.address.telephone}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong><spring:message code="zipCode"/></strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.address.zipCode}
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12"></div>
                            <div class="col-xs-6 text-center">
                                <a class="btn btn-info" href="<c:url value="/students/${student.docket}/edit"/>">
                                    <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="edit"/>
                                </a>
                            </div>
                            <div class="col-xs-6 text-center">
                                <a class="btn btn-info" href="<c:url value="/user/changePassword"/>">
                                    <i class="fa fa-key" aria-hidden="true"></i> <spring:message code="changePassword"/>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12 col-md-4">
                    <div class="row">
                        <div class="col-xs-6 col-md-6 text-center">
                            <a href="courses" type="button" class="btn btn-info col_vertical_margin" role="button"><i class="fa fa-fw fa-university"></i> <spring:message code="courses"/></a>
                        </div>
                        <div class="col-xs-6 col-md-6 text-center">
                            <a href="grades" type="button" class="btn btn-info col_vertical_margin" role="button"><i class="fa fa-fw fa-graduation-cap"></i> <spring:message code="grades"/></a>
                        </div>
                        <div class="col-xs-6 col-md-6 text-center">
                            <a href="inscription" type="button" class="btn btn-info col_vertical_margin" role="button"><i class="fa fa-fw fa-list-alt"></i> <spring:message code="inscriptions"/></a>
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
