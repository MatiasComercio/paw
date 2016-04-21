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
                        Alumnos <small>Lista de alumnos</small>
                    </h1>
                </div>
            </div>

            <!-- -->
            <div class="container">
                <jsp:include page="base/alerts.jsp" />
                <h2>Agregar curso</h2>
            <c:if test="${task == 'add' }">
                <form:form modelAttribute="courseForm" method="post" action="/app/courses/add_course">
                    <div class="form-group">
                        <form:label path="id">ID:</form:label>
                        <form:input path="id" type="text" class="form-control" />
                        <form:errors path="id" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label path="name">Nombre:</form:label>
                        <form:input type="text" class="form-control" path="name"/>
                        <form:errors path="name" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label path="credits">Créditos:</form:label>
                        <form:input type="text" class="form-control" path="credits"/>
                        <form:errors path="credits" cssStyle="color: red;" element="div"/>
                    </div>
                    <input type="submit" class="btn btn-info" value="Agregar materia"/>
                </form:form>
            </c:if>
            <c:if test="${task == 'edit' }">
                <form:form modelAttribute="courseForm" method="post" action="/app/courses/${courseId}/edit">
                    <div class="form-group">
                        <form:label path="id">ID:</form:label>
                        <form:input path="id" type="text" class="form-control" />
                        <form:errors path="id" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="name" path="name">Nombre:</form:label>
                        <form:input type="text" class="form-control" id="name" path="name"/>
                    </div>
                    <div class="form-group">
                        <form:label for="credits" path="credits">Créditos:</form:label>
                        <form:input type="text" class="form-control" id="credits" path="credits"/>
                    </div>
                    <input type="submit" class="btn btn-info" value="Guardar cambios"/>
                </form:form>
            </c:if>
            </div>

            <!-- -->

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- Scripts -->
<jsp:include page="base/footer.jsp" />
</body>
</html>