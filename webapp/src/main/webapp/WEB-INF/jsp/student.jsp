<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Alumno ${student.docket}</title>
    <jsp:include page="base/head.jsp" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
                    <div class="col-lg-3">
                        <div class="row">Legajo</div>
                        <div class="row">DNI</div>
                        <div class="row">Primer Nombre</div>
                        <div class="row">Segundo Nombre</div>
                        <div class="row">Genero</div>
                        <div class="row">Cumpleaños</div>
                        <div class="row">Email</div>
                    </div>
                    <div class="col-lg-3">
                        <div class="row">${student.docket}</div>
                        <div class="row">${student.dni}</div>
                        <div class="row">${student.firstName}</div>
                        <div class="row">${student.lastName}</div>
                        <div class="row">${student.genre}</div>
                        <div class="row">${student.birthday}</div>
                        <div class="row">${student.email}</div>
                    </div>
                </div>
                <!-- Content -->
            </div>
        </div>
    </div>
</body>
</html>
