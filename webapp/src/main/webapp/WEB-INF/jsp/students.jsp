<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | <spring:message code="students"/>
    </title>
    <jsp:include page="base/head.jsp" />
</head>
<body>

<div id="wrapper">

    <jsp:include page="base/nav.jsp" />

    <c:if test="${subsection_students}">
        <jsp:include page="template/deleteStudentForm.jsp" />
    </c:if>

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-xs-12">
                    <h1 class="page-header">
                        <c:choose>
                            <c:when test="${subsection_students}">
                                <spring:message code="students"/>
                            </c:when>
                            <c:when test="${subsection_courses}">
                                ${course.name} <small>- <spring:message code="students"/></small>
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
            <div class="row">
                <div class="col-xs-12 col-md-2 text-center">
                    <p class="lead"><spring:message code="actions"/>:</p>
                </div>
                <div class="col-xs-12 col-md-2 text-center">
                    <a class="btn btn-info" href="/students/add_student" role="button">
                        <i class="fa fa-plus-circle" aria-hidden="true"></i> <spring:message code="addStudent"/>
                    </a>
                </div>
            </div>
            <jsp:include page="template/searchStudents.jsp" />

            <!-- /content -->

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- Scripts -->
<jsp:include page="base/footer.jsp" />
<script type="text/javascript" charset="UTF-8"><%@include file="../js/template/searchStudents.js"%></script>
<c:if test="${subsection_students}">
    <script type="text/javascript" charset="UTF-8"><%@include file="../js/template/deleteStudentForm.js"%></script>
</c:if>
<script>
    $( document ).ready(function() {
        loadStudentSearch();
        <c:choose>
        <c:when test="${subsection_students}">
        loadDeleteStudentForm("deleteStudentButton");
        </c:when>
        </c:choose>
    });
</script>
</body>
</html>
