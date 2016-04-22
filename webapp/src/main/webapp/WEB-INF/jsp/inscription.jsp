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
            <form:form id="inscription_form" modelAttribute="inscriptionForm" action="/students/${inscriptionForm.studentDocket}/inscription" method="post" enctype="application/x-www-form-urlencoded">
                <div>
                    <form:input class="form-control" path="studentDocket" value="${inscriptionForm.studentDocket}" readonly="true" type="hidden"/>
                </div>
                <div>
                    <form:input id="courseInput" class="form-control" path="courseId" type="hidden"/>
                </div>
            </form:form>

            <!-- search -->
            <div class="row well">
                <div class="col-xs-12 col-md-2">
                    <p class="lead">Buscar por:</p>
                </div>
                <div class="col-xs-12 col-md-8">
                    <div class="row">
                        <div class="col-xs-12 col-md-5">
                            <div class="input-group">
                                <span class="input-group-addon">Id</span>
                                <input id="id_text" type="text" class="form-control" placeholder="ID...">
                            </div>
                        </div>
                        <div class="col-xs-12 col-md-7">
                            <div class="input-group">
                                <span class="input-group-addon">Nombre</span>
                                <input id="name_text" type="text" class="form-control" placeholder="Nombre...">
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

            <!-- content -->
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

        var urlWithFilters = function() {
            var id_text = $("#id_text").val();
            var name_text = $("#name_text").val();

            document.location.href = "?keyword=" + name_text + "&" + "id=" + id_text;
        }

        $('#search').click(urlWithFilters);

        var addCourse = function(){window.location="/courses/add_course";}
        $('#addCourse').on("click", addCourse);

        /* source: http://stackoverflow.com/questions/10905345/pressing-enter-on-a-input-type-text-how */
        $("input").bind("keypress", {}, keypressInBox);

        function keypressInBox(e) {
            var code = (e.keyCode ? e.keyCode : e.which);
            if (code == 13) { //Enter keycode
                e.preventDefault();
                urlWithFilters();
            }
        };


        $("[name='inscription']").on("click", function() {
            $("#courseInput").val($(this).attr("data-id"));
            $("#inscription_form").submit();
        });


    });
</script>
</body>
</html>