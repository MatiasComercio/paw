<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head><jsp:include page="base/head.jsp" /></head>
<body>

<div id="wrapper">

    <jsp:include page="base/nav.jsp" />

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                        Alumnos
                    </h1>
                </div>
            </div>

            <!-- Result Message -->
            <div class="row">
                <div class="col-xs-12">
                    <h1>${message}</h1>
                </div>
            </div>

            <!-- search -->
            <div class="row">
                <div class="col-xs-9">
                    <div class="row">
                        <div class="input-group">
                            <span class="input-group-addon" id="sizing-addon">Legajo</span>
                            <input id="docket_text" type="text" class="form-control" placeholder="Buscar por legajo..." aria-describedby="sizing-addon2">
                            <span class="input-group-addon" id="sizing-addon2">Nombre</span>
                            <input id="first_name_text" type="text" class="form-control" placeholder="Buscar por nombre..." aria-describedby="sizing-addon2">
                            <span class="input-group-addon" id="sizing-addon3">Apellido</span>
                            <input id="last_name_text" type="text" class="form-control" placeholder="Buscar por apellido..." aria-describedby="sizing-addon2">
                        </div>
                    </div>
                </div>
                <div class="col-md-1">
                    <div id="search" type="button" class="btn btn-default">Buscar</div>
                </div>
                <div class="col-md-1">
                    <div id="addStudent" type="button" class="btn btn-default">Agregar</div>
                </div>
            </div>

            <!-- content -->
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Legajo</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Email</th>
                    <th>Acciones</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${students}" var="student">
                    <tr>
                        <td>${ student.docket }</td>
                        <td>${ student.firstName }</td>
                        <td>${ student.lastName }</td>
                        <td>${ student.email }</td>
                        <td><a href="<c:url value="/app/students/${student.docket}/info" />">Ver</a>
                            <form action="students/${student.docket}/delete" method="post">
                                <button type="submit" value="students/${student.docket}/delete">Eliminar</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

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
            var docket_text = $("#docket_text").val();
            var first_name_text = $("#first_name_text").val();
            var last_name_text = $("#last_name_text").val();

            document.location.href = "?docket=" + docket_text + "&" + "firstName=" + first_name_text + "&" + "lastName=" + last_name_text;
        }

        $('#search').click(urlWithFilters);

        var addStudent = function(){window.location="/app/students/add_student";}
        $('#addStudent').on("click", addStudent);

        /* source: http://stackoverflow.com/questions/10905345/pressing-enter-on-a-input-type-text-how */
        $("input").bind("keypress", {}, keypressInBox);

        function keypressInBox(e) {
            var code = (e.keyCode ? e.keyCode : e.which);
            if (code == 13) { //Enter keycode
                e.preventDefault();
                urlWithFilters();
            }
        };
    });
</script>
</body>
</html>