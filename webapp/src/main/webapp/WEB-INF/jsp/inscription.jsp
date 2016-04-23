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

    <%-- Confirmation Modal --%>

    <!-- Modal -->
    <div class="modal fade" id="confirmationModal" tabindex="-1" role="dialog" aria-labelledby="confirmationModal">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Confirmar Inscripción</h4>
                </div>
                <div class="modal-body">
                    <%-- Inscription Form --%>
                    <form:form class="form-horizontal" id="inscription_form" modelAttribute="inscriptionForm" action="/students/${docket}/inscription" method="post" enctype="application/x-www-form-urlencoded">
                        <div class="form-group">
                            <form:label path="studentDocket" class="col-xs-4 control-label">Legajo del Alumno</form:label>
                            <div class="col-xs-8">
                                <form:input class="form-control" id="disabledInput" type="text" path="studentDocket" value="${docket}" readonly="true"/>
                            </div>
                            <div class="col-xs-12">
                                <form:errors path="studentDocket" cssClass="text-danger bg-danger" element="div"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <form:label path="courseId" class="col-xs-4 control-label">Id del Curso</form:label>
                            <div class="col-xs-8">
                                <form:input class="form-control" id="disabledInput" type="text" path="courseId" readonly="true"/>
                            </div>
                            <div class="col-xs-12">
                                <form:errors path="courseId" cssClass="text-danger bg-danger" element="div"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <form:label path="courseName" class="col-xs-4 control-label">Nombre del Curso</form:label>
                            <div class="col-xs-8">
                                <form:input class="form-control" id="disabledInput" type="text" path="courseName" readonly="true"/>
                            </div>
                            <div class="col-xs-12">
                                <form:errors path="courseName" cssClass="text-danger bg-danger" element="div"/>
                            </div>
                        </div>
                    </form:form>
                    <%-- /Inscription Form--%>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                    <button id="confirmAction" type="button" class="btn btn-info">Confirmar</button>
                </div>
            </div>
        </div>
    </div>

    <%--  /Confirmation Modal --%>

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
            <%--            &lt;%&ndash; Inscription Form &ndash;%&gt;
                        <form:form id="inscription_form" modelAttribute="inscriptionForm" action="/students/${docket}/inscription" method="post" enctype="application/x-www-form-urlencoded">
                            <div>
                                <form:input class="form-control" path="studentDocket" value="${docket}" readonly="true" type="hidden"/>
                            </div>
                            <div>
                                <form:input name="courseId" class="form-control" path="courseId" type="hidden"/>

                                <form:input name="courseName" class="form-control" path="courseName" type="hidden"/>
                            </div>
                        </form:form>
                        &lt;%&ndash; /Inscription Form&ndash;%&gt;--%>

            <!-- search -->
            <%--Course Filter Form--%>
            <form:form id="course_filter_form" modelAttribute="courseFilterForm" action="/students/${docket}/inscription/courseFilterForm" method="post" enctype="application/x-www-form-urlencoded">

                <div class="row well">
                    <div class="col-xs-12 col-md-2">
                        <p class="lead">Buscar por:</p>
                    </div>
                    <div class="col-xs-12 col-md-6">
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
                                        <form:errors path="name" cssClass="text-danger bg-danger" element="div"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-1 hidden-md hidden-lg"></div>
                    <div class="col-xs-5 col-md-2 text-center">
                        <button id="search" class="btn btn-default" type="submit">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                            Buscar
                        </button>
                    </div>
                    <div class="col-xs-5 col-md-2 text-center">
                        <button id="resetSearch" class="btn btn-default" type="submit">
                            <span class="glyphicon glyphicon-repeat" aria-hidden="true"></span>
                            Resetear
                        </button>
                    </div>
                </div>
            </form:form>
            <%--/Course Filter Form--%>

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
                                <button name="inscription" class="btn btn-info btn-xs" type="button"
                                        data-course_id="${ course.id }" data-course_name="${ course.name }"
                                        data-toggle="modal" data-target="#confirmationModal">
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
            var courseId = $(this).data("course_id");
            var courseName = $(this).data("course_name");
            var inscriptionForm = $("#inscription_form");
            inscriptionForm.find("input[name='courseId']").val(courseId);
            inscriptionForm.find("input[name='courseName']").val(courseName);
            $(".modal-body span").text(courseId + " - " + courseName + "?");
        });

        $("#confirmAction").on("click", function() {
            $('#confirmationModal').modal('hide');
            $("#inscription_form").submit();
        });

        $("#resetSearch").on("click", function() {
            var courseFilterForm = $("#course_filter_form");
            courseFilterForm.find("input[name='id']").val(null);
            courseFilterForm.find("input[name='name']").val(null);
            courseFilterForm.submit();
        });
    });
</script>
</body>
</html>