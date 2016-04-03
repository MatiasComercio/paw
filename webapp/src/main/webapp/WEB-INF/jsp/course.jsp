<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title> ${course.name}</title>
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
                            Informacion de la Materia
                        </h1>
                    </div>
                </div>
                <!-- Content -->
                <div class="row">
                    <div class="col-lg-3">
                        <div class="row">ID</div>
                        <div class="row">Nombre</div>
                        <div class="row">Creditos</div>
                    </div>
                    <div class="col-lg-3">
                        <div class="row">${course.id}</div>
                        <div class="row">${course.name}</div>
                        <div class="row">${course.credits}</div>
                    </div>
                </div>
                <div class="row">
                    <a href="<c:url value="/app/courses/${course.id}/students" />">Ver Alumnos Inscriptos</a>
                </div>
                <!-- Content -->
            </div>
        </div>
    </div>
</body>
</html>
