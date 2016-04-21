<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        Materias
                    </h1>
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
                            <div class="row">
                                <div class="col-xs-2">
                                    <a href="<c:url value="/app/courses/${course.id}/info" />">Ver</a>
                                </div>
                                <div class="col-xs-4">
                                    <button name="unenroll" type="button" class="btn btn-danger"
                                            data-course_id="${ course.id }" data-course_name="${ course.name }">
                                        Darse de Baja
                                    </button>
                                </div>
                            </div>
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
<script type="text/javascript"><%@include file="../js/courses.js"%></script>
<script>
    $(document).ready(loadCoursesJs());
</script>
</body>
</html>



