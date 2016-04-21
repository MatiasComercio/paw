<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
                        Inscripción de Alumno <small></small>
                    </h1>
                </div>
            </div>

            <!-- content -->
            <div class="row">
                <div class="col-xs-12">
                    <jsp:include page="base/alerts.jsp" />
                </div>
            </div>
            <form:form modelAttribute="inscriptionForm" action="/app/students/${inscriptionForm.studentDocket}/inscription" method="post" enctype="application/x-www-form-urlencoded">
                <div>
                    <form:label path="studentDocket">Legajo del Alumno</form:label>
                    <form:input class="form-control" path="studentDocket" value="${inscriptionForm.studentDocket}" readonly="true"/>
                </div>
                <div>
                    <form:label path="courseId">ID del Curso</form:label>
                    <form:input class="form-control" path="courseId" type="text"/>
                    <form:errors path="courseId" cssStyle="color: red;" element="div"/>
                </div>
                <div>
                    <input class="form-control" type="submit" value="Inscribirse"/>
                </div>
            </form:form>

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