<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="base/head.jsp" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Alumno ${student.docket}</title>
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
                    <dl>
                        <dt>Legajo</dt>
                        <dd>${student.docket}</dd>
                        <dt>DNI</dt>
                        <dd>${student.dni}</dd>
                        <dt>Primer Nombre</dt>
                        <dd>${student.firstName}</dd>
                        <dt>Segundo Nombre</dt>
                        <dd>${student.lastName}</dd>
                        <dt>Genero</dt>
                        <dd>${student.genre}</dd>
                        <dt>Cumpleaños</dt>
                        <dd>${student.birthday}</dd>
                        <dt>Email</dt>
                        <dd>${student.email}</dd>
                    </dl>
                </div>
                <!-- Content -->
            </div>
        </div>
    </div>
</body>
</html>
