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
                        Información del Alumno
                        <a class="btn btn-info" href="<c:url value="/app/students/${student.docket}/edit"/>">Editar</a>
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
                                        <strong>Legajo</strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.docket}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong>DNI</strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.dni}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong>Nombre</strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.firstName}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong>Apellido</strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.lastName}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong>Género</strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.genre}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong>Cumpleaños</strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.birthday}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong>Email</strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.email}
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12 col-md-6">
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong>País</strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.address.country}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong>Ciudad</strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.address.city}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong>Localidad</strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.address.neighborhood}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong>Calle</strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.address.street}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong>Altura</strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.address.number}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong>Piso Nº</strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.address.floor}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong>Dpto. Nº</strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.address.door}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong>Teléfono</strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.address.telephone}
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-4 right-effect">
                                        <strong>Código Postal</strong>
                                    </div>
                                    <div class="col-xs-8">
                                        ${student.address.zipCode}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12 col-md-4">
                    <div class="row">
                        <div class="col-xs-12 col-md-6">
                            <a href="courses" type="button" class="btn btn-link">Ver Materias actuales</a>
                        </div>
                        <div class="col-xs-12 col-md-6">
                            <a href="grades" type="button" class="btn btn-link">Ver Historial de Notas</a>
                        </div>
                        <div class="col-xs-12 col-md-6">
                            <a href="inscription" type="button" class="btn btn-link">Inscribirse en Materia</a>
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
