<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Alumno ${student.docket}</title>
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
                        Informacion del Alumno
                    </h1>
                </div>
            </div>
            <!-- Content -->
            <div class="row">
                <div class="col-xs-9">
                    <div class="well">
                        <div class="row">

                            <div class="row">
                                <div class="col-xs-3 right-effect">
                                    <strong>Legajo</strong>
                                </div>
                                <div class="col-xs-9">
                                    ${student.docket}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-3 right-effect">
                                    <strong>DNI</strong>
                                </div>
                                <div class="col-xs-9">
                                    ${student.dni}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-3 right-effect">
                                    <strong>Nombre</strong>
                                </div>
                                <div class="col-xs-9">
                                    ${student.firstName}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-3 right-effect">
                                    <strong>Apellido</strong>
                                </div>
                                <div class="col-xs-9">
                                    ${student.lastName}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-3 right-effect">
                                    <strong>Género</strong>
                                </div>
                                <div class="col-xs-9">
                                    ${student.genre}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-3 right-effect">
                                    <strong>Cumpleaños</strong>
                                </div>
                                <div class="col-xs-9">
                                    ${student.birthday}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-3 right-effect">
                                    <strong>Email</strong>
                                </div>
                                <div class="col-xs-9">
                                    ${student.email}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Content -->
        </div>
    </div>
</div>
</body>
</html>
