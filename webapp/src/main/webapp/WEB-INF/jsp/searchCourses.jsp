<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Materias</title>
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
                        Cursos
                    </h1>
                </div>
            </div>

            <!-- search -->
            <div class="input-group">
                <input id="search_text" type="text" class="form-control" aria-label="...">
                <div class="input-group-btn">
                    <!-- Buttons -->
                    <div id="id_search" type="button" class="btn btn-default">Buscar por ID</div>
                    <div id="name_search" type="button" class="btn btn-default">Buscar por Nombre</div>
                </div>
            </div>

            <!-- content -->
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Creditos</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${courses}" var="course">
                    <tr>
                        <td>${ course.id }</td>
                        <td>${ course.name }</td>
                        <td>${ course.credits }</td>
                        <td><a href="<c:url value="courses/${course.id}" />">Ver</a></td>
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
        $('#id_search').click(function(){
            var text = $("#search_text").text();
        });
        $('#name_search')
    });
</script>
</body>
</html>