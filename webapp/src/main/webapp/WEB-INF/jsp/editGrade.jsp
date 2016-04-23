<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
                        Alumno ${docket}, materia ${courseId}
                    </h1>
                </div>
            </div>

            <!-- -->
            <div class="container">
                <jsp:include page="base/alerts.jsp" />
                <h2>Editar nota</h2>
                <form:form modelAttribute="gradeForm" method="post" action="/students/${docket}/grades/edit/${courseId}/${modified}/${grade}">
                    <div class="form-group">
                        <form:label path="grade">Nota:</form:label>
                        <form:input type="text" class="form-control" path="grade"/>
                        <form:errors path="grade" cssStyle="color: red;" element="div"/>
                    </div>
                    <input type="submit" class="btn btn-info" value="Guardar Cambios"/>
                </form:form>
            </div>

            <!-- -->

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->

</div>

</body>
</html>
