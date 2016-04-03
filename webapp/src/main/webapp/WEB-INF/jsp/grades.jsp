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
                         ${student.docket} - ${student.firstName} ${student.lastName}
                             <small>(${student.email})</small> - Grades
                    </h1>
                </div>
            </div>

            <!-- content -->
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>Materia</th>
                    <th>Nota</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${student.grades}" var="grade">
                    <tr>
                        <td><a href="<c:url value="/app/courses/${grade.courseId}/info" />">
                            ${ grade.courseId } - ${grade.courseName}
                        </td>
                        <td>${ grade.grade }</td>
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