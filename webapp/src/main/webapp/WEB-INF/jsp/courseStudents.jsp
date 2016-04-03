<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Alumnos de ${courseStudents.name}</title>
    <jsp:include page="base/head.jsp" />
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
                        Lista de Alumnos de ${courseStudents.name}
                    </h1>
                </div>
            </div>
            <!-- Content -->
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Legajo</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${courseStudents.students}" var="student">
                    <tr>
                        <td>${ student.docket }</td>
                        <td>${ student.firstName }</td>
                        <td>${ student.lastName }</td>
                        <td><a href="<c:url value="/app/students/${student.docket}/info/" />">Ver</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- Content -->
        </div>
    </div>
</div>
</body>
</html>
