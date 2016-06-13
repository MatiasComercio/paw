<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <jsp:include page="template/gradeForm.jsp" />

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-xs-12">
                    <h1 class="page-header">
                        Calificaciones
                    </h1>
                </div>
            </div>

            <!-- content -->
            <div class="row">
                <div class="col-xs-12">
                    <jsp:include page="base/alerts.jsp" />
                </div>
            </div>

            <jsp:include page="template/searchCourses.jsp" />

            <!-- /content -->

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->
    <jsp:include page="base/footer.jsp" />
</div>
<!-- Scripts -->
<script type="text/javascript" charset="UTF-8" src="<c:url value="/static/js/template/searchCourses.js"/>"></script>
<script type="text/javascript" charset="UTF-8" src="<c:url value="/static/js/template/gradeForm.js"/>"></script>

<script>
    $( document ).ready(function() {
        loadSearchCourses();
        loadGradeForm()
    });
</script>
</body>
</html>