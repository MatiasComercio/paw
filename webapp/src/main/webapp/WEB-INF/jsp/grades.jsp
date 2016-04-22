<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head><jsp:include page="base/head.jsp" /></head>
<body>

<div id="wrapper">

    <jsp:include page="base/nav.jsp" />

    <div id="page-wrapper">
            <!-- Page Heading -->
            <div class="row">
                <div class="col-xs-12">
                    <h1 class="page-header">
                         ${student.docket} - ${student.firstName} ${student.lastName}
                             <small>(${student.email})</small> - Notas
                    </h1>
                </div>
            </div>

            <div class="container-fluid">
                <div class="col-md-1">
                    <a href="/app/students/${student.docket}/grades/add" id="addGrade" type="button" class="btn btn-default">Agregar Nota</a>
                </div>
            </div>

            <!-- content -->
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Materia</th>
                    <th>Nota</th>
                    <th>Modificado</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${student.grades}" var="grade">
                    <tr>
                        <td>${ grade.courseId }</td>
                        <td>${ grade.courseName }</td>
                        <td>${ grade.grade }</td>
                        <td>${ grade.modified }</td>
                        <td><a href="<c:url value="/app/courses/${grade.courseId}/info" />">Ver Materia</a></td>
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
</body>
</html>