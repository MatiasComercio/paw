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
                <div class="col-lg-12">
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
                <div class="col-xs-12">
                    <h3>Materias en las que puede inscribirse:</h3>
                </div>
            </div>
            <form:form id="inscription_form" modelAttribute="inscriptionForm" action="/app/students/${inscriptionForm.studentDocket}/inscription" method="post" enctype="application/x-www-form-urlencoded">
                <div>
                    <form:input class="form-control" path="studentDocket" value="${inscriptionForm.studentDocket}" readonly="true" type="hidden"/>
                </div>
                <div>
                    <form:input id="courseInput" class="form-control" path="courseId" type="hidden"/>
                </div>
            </form:form>

            <!-- search -->
            <div class="row">
                <div class="col-md-6">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="input-group">
                                <span class="input-group-addon" id="sizing-addon">Id</span>
                                <input id="id_text" type="text" class="form-control" placeholder="Buscar por id..." aria-describedby="sizing-addon2">
                                <span class="input-group-addon" id="sizing-addon2">Nombre</span>
                                <input id="name_text" type="text" class="form-control" placeholder="Buscar por nombre..." aria-describedby="sizing-addon2">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-1">
                    <div id="search" type="button" class="btn btn-default">Buscar</div>
                </div>
            </div>

            <!-- content -->
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Créditos</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${courses}" var="course">
                    <tr>
                        <td>${ course.id }</td>
                        <td>${ course.name }</td>
                        <td>${ course.credits }</td>
                        <td>
                            <button name="inscription" data-id="${ course.id }" class="btn btn-default" type="submit">
                                Inscribirse
                            </button>
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

        var addCourse = function(){window.location="/app/courses/add_course";}
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