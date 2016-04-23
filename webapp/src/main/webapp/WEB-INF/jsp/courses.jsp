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

    <jsp:include page="template/enrollForm.jsp" />

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-xs-12">
                    <h1 class="page-header">
                        <c:choose>
                            <c:when test="${action_enroll}">
                                Inscripciones <small>- Materias Disponibles</small>
                            </c:when>
                            <c:when test="${action_unenroll}">
                                Materias
                            </c:when>
                        </c:choose>
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

</div>
<!-- Scripts -->
<jsp:include page="base/footer.jsp" />
<script type="text/javascript" charset="UTF-8"><%@include file="../js/template/searchCourses.js"%></script>
<c:choose>
    <c:when test="${action_enroll}">
        <script type="text/javascript" charset="UTF-8"><%@include file="../js/template/enrollForm.js"%></script>
    </c:when>
    <c:when test="${action_unenroll}">
        <script type="text/javascript" charset="UTF-8"><%@include file="../js/template/enrollForm.js"%></script>
    </c:when>
</c:choose>

<script>
    $( document ).ready(function() {
        loadSearch();
        <c:choose>
            <c:when test="${action_enroll}">
                loadEnrollForm("inscription");
            </c:when>
            <c:when test="${action_unenroll}">
                loadEnrollForm("unenroll");
            </c:when>
        </c:choose>
    });
</script>
</body>
</html>