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

            <!-- content -->

            <form:form method="post" action="/app/courses/add_course">

                <table>
                    <tr>
                        <td><form:label path="id">ID</form:label></td>
                        <td><form:input path="id" /></td>
                    </tr>
                    <tr>
                        <td><form:label path="name">Nombre</form:label></td>
                        <td><form:input path="name" /></td>
                    </tr>
                    <tr>
                        <td><form:label path="credits">Créditos</form:label></td>
                        <td><form:input path="credits" /></td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <input type="submit" value="Agregar materia"/>
                        </td>
                    </tr>
                </table>

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