<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>

</head>

<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="base/head.jsp" />
</head>
<body>

<div id="wrapper">

    <jsp:include page="base/nav.jsp" />

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-xs-12">
                    <h1 class="page-header">
                        Inscribirse en Materia - <small>Legajo del alumno: ${docket}</small>
                    </h1>
                </div>
            </div>

            <!-- content -->
            <div class="row">
                <div class="col-xs-12">
                    <jsp:include page="base/alerts.jsp" />
                </div>
            </div>
            <%-- Inscription Form --%>
            <form:form id="inscription_form" modelAttribute="inscriptionForm" action="/students/${docket}/inscription" method="post" enctype="application/x-www-form-urlencoded">
                <div>
                    <form:input class="form-control" path="studentDocket" value="${docket}" readonly="true" type="hidden"/>
                </div>
                <div>
                    <form:input name="courseId" class="form-control" path="courseId" type="hidden"/>

                    <form:input name="courseName" class="form-control" path="courseName" type="hidden"/>
                </div>
            </form:form>
            <%-- /Inscription Form--%>

            <!-- search -->
            <form:form id="course_filter_form" modelAttribute="courseFilterForm" action="/students/${docket}/inscription/courseFilterForm" method="post" enctype="application/x-www-form-urlencoded">

                <div class="row well">
                    <div class="col-xs-12 col-md-2">
                        <p class="lead">Buscar por:</p>
                    </div>
                    <div class="col-xs-12 col-md-8">
                        <div class="row">
                            <div class="col-xs-12 col-md-5">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="input-group">
                                            <span class="input-group-addon">Id</span>
                                            <form:input path="id"  type="text" class="form-control" placeholder="Id..."/>
                                        </div>
                                    </div>
                                    <div class="col-xs-12">
                                        <form:errors path="id" cssClass="text-danger bg-danger" element="div"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12 col-md-7">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="input-group">
                                            <span class="input-group-addon">Nombre</span>
                                            <form:input path="name" type="text" class="form-control" placeholder="Nombre..."/>
                                        </div>
                                    </div>
                                    <div class="col-xs-12">
                                        <form:errors path="name" cssStyle="color: red;"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-3 hidden-md hidden-lg"></div>
                    <div class="col-xs-6 col-md-1 text-center">
                        <button id="search" class="btn btn-default" type="submit">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                            Buscar
                        </button>
                    </div>
                </div>
            </form:form>
            <!-- content -->
            <div class="table-responsive">
                <table class="table table-hover <%--table-bordered--%> <%--table-condensed--%>">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Créditos</th>
                        <th>Acciones</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${empty courses}">
                        <tr class="bg-warning">
                            <td colspan="4" class="text-danger text-center"> No se encontraron materias disponibles. </td>
                        </tr>
                    </c:if>
                    <c:forEach items="${courses}" var="course">
                        <tr>
                            <td>${ course.id }</td>
                            <td>${ course.name }</td>
                            <td>${ course.credits }</td>
                            <td>
                                <button name="inscription" data-course_id="${ course.id }" data-course_name="${ course.name }" class="btn btn-info btn-xs" type="submit">
                                    <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span> Inscribirse
                                </button>
                                <a class="btn btn-default btn-xs" href="<c:url value="/courses/${course.id}/info" />" role="button">
                                    <span class="glyphicon glyphicon-info-sign" aria-hidden="true"></span> Ver
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- /content -->

            <!-- /content -->

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- Scripts -->
<jsp:include page="base/footer.jsp" />
<script>
    $( document ).ready(function() {

        $("[name='inscription']").on("click", function() {
            $("#inscription_form input[name='courseId']").val($(this).data("course_id"));
            $("#inscription_form input[name='courseName']").val($(this).data("course_name"));
            $("#inscription_form").submit();
        });


    });
</script>
</body>
</html>