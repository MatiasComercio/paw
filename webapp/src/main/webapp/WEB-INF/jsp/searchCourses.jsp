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
            <div class="row">
                <div class="col-md-6">
                    <div class="row">
                        <div class="input-group">
                            <span class="input-group-addon" id="sizing-addon">Id</span>
                            <input id="id_text" type="text" class="form-control" placeholder="Buscar por id" aria-describedby="sizing-addon2">
                            <span class="input-group-addon" id="sizing-addon2">Nombre</span>
                            <input id="name_text" type="text" class="form-control" placeholder="Buscar por nombre" aria-describedby="sizing-addon2">
                        </div>
                    </div>
                </div>
                <div class="col-md-2">
                    <div id="search" type="button" class="btn btn-default">Buscar</div>
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
        $('#search').click(function(){
            var id_text = $("#id_text").val();
            var name_text = $("#name_text").val();

            document.location.href = "?keyword=" + name_text + "&" + "id=" + id_text;

        });
    });
</script>
</body>
</html>