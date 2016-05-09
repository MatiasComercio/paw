<%--@elvariable id="admin" type="ar.edu.itba.paw.models.users.Admin"--%>
<%@ include file="base/tags.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | ${admin.fullName} | <spring:message code="profile"/>
    </title>
    <jsp:include page="base/head.jsp" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div id="wrapper">

    <jsp:include page="base/sections.jsp"/>
    <jsp:include page="base/nav.jsp" />

    <div id="page-wrapper">
        <div class="container-fluid">
            <!-- Page Heading -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                        ${admin.fullName} <small> - <spring:message code="profile"/></small>
                    </h1>
                </div>
            </div>

            <jsp:include page="base/alerts.jsp" />


            <!-- Content -->
            <div class="row">
                <div class="col-xs-12">
                    <div class="well">
                        <div class="row">
                            <div class="col-xs-12 col-md-6">
                                <div class="row">
                                    <div class="col-xs-6 ">
                                        <strong><spring:message code="dni"/></strong>
                                    </div>
                                    <div class="col-xs-6">
                                        ${admin.dni}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-6 ">
                                        <strong><spring:message code="firstName"/></strong>
                                    </div>
                                    <div class="col-xs-6">
                                        ${admin.firstName}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-6 ">
                                        <strong><spring:message code="lastName"/></strong>
                                    </div>
                                    <div class="col-xs-6">
                                        ${admin.lastName}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-6 ">
                                        <strong><spring:message code="genre"/></strong>
                                    </div>
                                    <div class="col-xs-6">
                                        <spring:message code="${admin.genre}"/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-6 ">
                                        <strong><spring:message code="birthday"/></strong>
                                    </div>
                                    <div class="col-xs-6">
                                        ${admin.birthday}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-6 ">
                                        <strong><spring:message code="email"/></strong>
                                    </div>
                                    <div class="col-xs-6">
                                        ${admin.email}
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12 col-md-6">
                                <div class="row">
                                    <div class="col-xs-6 ">
                                        <strong><spring:message code="country"/></strong>
                                    </div>
                                    <div class="col-xs-6">
                                        ${admin.address.country}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-6 ">
                                        <strong><spring:message code="city"/></strong>
                                    </div>
                                    <div class="col-xs-6">
                                        ${admin.address.city}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-6 ">
                                        <strong><spring:message code="neighbourhood"/></strong>
                                    </div>
                                    <div class="col-xs-6">
                                        ${admin.address.neighborhood}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-6 ">
                                        <strong><spring:message code="street"/></strong>
                                    </div>
                                    <div class="col-xs-6">
                                        ${admin.address.street}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-6 ">
                                        <strong><spring:message code="number"/></strong>
                                    </div>
                                    <div class="col-xs-6">
                                        ${admin.address.number}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-6 ">
                                        <strong><spring:message code="floor"/></strong>
                                    </div>
                                    <div class="col-xs-6">
                                        ${admin.address.floor}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-6 ">
                                        <strong><spring:message code="door"/></strong>
                                    </div>
                                    <div class="col-xs-6">
                                        ${admin.address.door}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-6 ">
                                        <strong><spring:message code="telephone"/></strong>
                                    </div>
                                    <div class="col-xs-6">
                                        ${admin.address.telephone}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-6 ">
                                        <strong><spring:message code="zipCode"/></strong>
                                    </div>
                                    <div class="col-xs-6">
                                        ${admin.address.zipCode}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Content -->
        </div>
    </div>
    <jsp:include page="base/footer.jsp" />
</div>
<!-- Scripts -->
<jsp:include page="base/scripts.jsp" />
</body>
</html>
