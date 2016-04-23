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
            <form:form id="inscription_form" modelAttribute="inscriptionForm" action="/students/${inscriptionForm.studentDocket}/inscription" method="post" enctype="application/x-www-form-urlencoded">
                <div>
                    <form:input class="form-control" path="studentDocket" value="${inscriptionForm.studentDocket}" readonly="true" type="hidden"/>
                </div>
                <div>
                    <form:input id="courseInput" class="form-control" path="courseId" type="hidden"/>
                </div>
            </form:form>
            <%-- /Inscription Form--%>

            <%--            &lt;%&ndash; CourseFilterForm &ndash;%&gt;
                        <form:form id="course_filter_form" modelAttribute="courseFilterForm" action="/students/${inscriptionForm.studentDocket}/inscription/courseFilterForm" method="post" enctype="application/x-www-form-urlencoded">
                            <div>
                                <form:input class="form-control" path="id" type="hidden"/>
                            </div>
                            <div>
                                <form:input class="form-control" path="name" type="hidden"/>
                            </div>
                        </form:form>
                        &lt;%&ndash; CourseFilterForm &ndash;%&gt;--%>

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
                                            <form:errors path="id" cssStyle="color: red;"/>
                                        </div>
                                    </div>
                                    <div class="col-xs-12">
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12 col-md-7">
                                <div class="input-group">
                                    <span class="input-group-addon">Nombre</span>
                                    <form:input path="name" type="text" class="form-control" placeholder="Nombre..."/>
                                    <form:errors path="name" cssStyle="color: red;"/>
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
                                <button name="inscription" data-id="${ course.id }" class="btn btn-info btn-xs" type="submit">
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
            $("#courseInput").val($(this).data("id"));
            $("#inscription_form").submit();
        });


    });
</script>
</body>
</html>