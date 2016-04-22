<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Materias</title>
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
                    <h4 class="modal-title" id="myModalLabel">Darse de Baja</h4>
                </div>
                <div class="modal-body">
                    Seguro?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">No</button>
                    <button id="confirmAction" type="button" class="btn btn-primary">Sí</button>
                </div>
            </div>
        </div>
    </div>

    <%-- End of Confirmation Modal --%>

    <%-- Form --%>
    <form:form id="inscription_form" modelAttribute="inscriptionForm" action="/app/students/${inscriptionForm.studentDocket}/courses/unenroll" method="post" enctype="application/x-www-form-urlencoded">
        <div>
            <form:input class="form-control" path="studentDocket" value="${inscriptionForm.studentDocket}" readonly="true" type="hidden"/>
        </div>
        <div>
            <form:input id="courseInput" class="form-control" path="courseId" type="hidden"/>
        </div>
    </form:form>
    <%-- End of Form --%>


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
            <div class="row">
                <div class="col-xs-12">
                    <jsp:include page="base/alerts.jsp" />
                </div>
            </div>
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
                                            data-course_id="${ course.id }" data-course_name="${ course.name }"
                                            data-toggle="modal" data-target="#confirmationModal">
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



