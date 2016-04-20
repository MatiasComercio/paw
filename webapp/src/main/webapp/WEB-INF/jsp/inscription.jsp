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

            <form:form modelAttribute="inscriptionForm" action="/app/students/${inscriptionForm.studentDocket}/inscription" method="post" enctype="application/x-www-form-urlencoded">
                <div>
                    <form:label path="studentDocket">Legajo del Alumno</form:label>
                    <form:input path="studentDocket" disabled="true" value="${inscriptionForm.studentDocket}"/>
                </div>
                <div>
                    <form:label path="courseId">ID del Curso</form:label>
                    <form:input path="courseId" type="text"/>
                    <form:errors path="courseId" cssStyle="color: red;" element="div"/>
                </div>
                <div>
                    <input type="submit" value="Login"/>
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