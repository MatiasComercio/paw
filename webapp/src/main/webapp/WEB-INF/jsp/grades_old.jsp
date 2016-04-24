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
                <div class="col-xs-12">
                    <h1 class="page-header">
                        Notas
                    </h1>
                    <jsp:include page="base/alerts.jsp" />
                </div>
            </div>

            <div class="col-md-1">
                <%--<a href="/students/${student.docket}/grades/add" id="addGrade" type="button" class="btn btn-default">Agregar Nota</a>--%><%-- implemented in /students/{docket}/courses --%>
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
                        <td><a href="<c:url value="/courses/${grade.courseId}/info" />">Ver Materia</a></td>
                        <td><a href="<c:url value="/students/${student.docket}/grades/edit/${grade.courseId}/${grade.modified}/${grade.grade}" />">Editar Nota</a></td>
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