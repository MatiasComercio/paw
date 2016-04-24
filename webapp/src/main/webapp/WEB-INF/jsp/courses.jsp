<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | ${student.fullName} | <spring:message code="courses"/>
    </title>
    <jsp:include page="base/head.jsp" />
</head>
<body>

<div id="wrapper">

    <jsp:include page="base/nav.jsp" />

    <jsp:include page="template/enrollForm.jsp" />
    <c:if test="${subsection_courses}">
        <jsp:include page="template/gradeForm.jsp" />
    </c:if>

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-xs-12">
                    <h1 class="page-header">
                        <c:choose>
                            <c:when test="${subsection_enroll}">
                                <spring:message code="inscriptions"/> <small>- <spring:message code="availableCourses"/></small>
                            </c:when>
                            <c:when test="${subsection_courses}">
                                <spring:message code="courses"/>
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
<script type="text/javascript" charset="UTF-8"><%@include file="../js/template/enrollForm.js"%></script>
<c:if test="${subsection_courses}">
    <script type="text/javascript" charset="UTF-8"><%@include file="../js/template/gradeForm.js"%></script>
</c:if>
<script>
    $( document ).ready(function() {
        loadSearch();
        <c:choose>
            <c:when test="${subsection_enroll}">
                loadEnrollForm("inscription");
            </c:when>
            <c:when test="${subsection_courses}">
                loadEnrollForm("unenroll");
                loadGradeForm("gradeButton")
            </c:when>
        </c:choose>
    });
</script>
</body>
</html>