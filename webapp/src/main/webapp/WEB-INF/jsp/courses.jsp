<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <c:choose>
            <c:when test="${section=='students'}">
                <spring:message code="webAbbreviation"/> | ${student.fullName} | <spring:message code="courses"/>
            </c:when>
            <c:when test="${section=='courses'}">
                <spring:message code="webAbbreviation"/> | <spring:message code="courses"/>
            </c:when>
            <c:otherwise><spring:message code="webAbbreviation"/></c:otherwise>
        </c:choose>
    </title>
    <jsp:include page="base/head.jsp" />
</head>
<body>

<div id="wrapper">

    <jsp:include page="base/nav.jsp" />

    <c:choose>
        <c:when test="${section=='students'}">
            <<jsp:include page="template/enrollForm.jsp" />
            <c:if test="${subsection_courses}">
                <jsp:include page="template/gradeForm.jsp" />
            </c:if>
        </c:when>
        <c:when test="${section=='courses'}">
            <c:choose>
                <c:when test="${subsection_get_courses}">
                    <jsp:include page="template/deleteCourseForm.jsp" />
                </c:when>
                <c:when test="${subsection_add_correlative}">
                    <jsp:include page="template/CorrelativeForm.jsp" />
                </c:when>
            </c:choose>
        </c:when>
    </c:choose>

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-xs-12">
                    <h1 class="page-header">
                        <c:choose>
                            <c:when test="${section=='students'}">
                                <c:choose>
                                    <c:when test="${subsection_enroll}">
                                        <spring:message code="inscriptions"/> <small>- <spring:message code="availableCourses"/></small>
                                    </c:when>
                                    <c:when test="${subsection_courses}">
                                        <spring:message code="courses"/>
                                    </c:when>
                                </c:choose>
                            </c:when>
                            <c:when test="${section=='courses'}">
                                <spring:message code="courses"/>
                            </c:when>
                            <c:otherwise><spring:message code="webAbbreviation"/></c:otherwise>
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
            <c:choose>
                <c:when test="${section=='courses'}">
                    <c:if test="${subsection_get_courses}">
                        <div class="row">
                            <div class="col-xs-12 col-md-2 text-center">
                                <p class="lead"><spring:message code="actions"/>:</p>
                            </div>
                            <div class="col-xs-12 col-md-2 text-center">
                                <a class="btn btn-info" href="/courses/add_course" role="button">
                                    <i class="fa fa-plus-circle" aria-hidden="true"></i> <spring:message code="addCourse"/>
                                </a>
                            </div>
                        </div>
                    </c:if>
                </c:when>
            </c:choose>
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
    <c:when test="${section=='students'}">
        <script type="text/javascript" charset="UTF-8"><%@include file="../js/template/enrollForm.js"%></script>
        <c:if test="${subsection_courses}">
            <script type="text/javascript" charset="UTF-8"><%@include file="../js/template/gradeForm.js"%></script>
        </c:if>
    </c:when>
    <c:when test="${section=='courses'}">
        <c:choose>
            <c:when test="${subsection_get_courses}">
                <script type="text/javascript" charset="UTF-8"><%@include file="../js/template/deleteCourseForm.js"%></script>
            </c:when>
            <c:when test="${subsection_add_correlative}">
                <script type="text/javascript" charset="UTF-8"><%@include file="../js/template/addCorrelativeForm.js"%></script>
            </c:when>
        </c:choose>
    </c:when>
</c:choose>

<script>
    $( document ).ready(function() {
        loadSearch();

        <c:choose>
            <c:when test="${section=='students'}">
                <c:choose>
                    <c:when test="${subsection_enroll}">
                        loadEnrollForm("inscription");
                    </c:when>
                    <c:when test="${subsection_courses}">
                        loadEnrollForm("unenroll");
                        loadGradeForm("gradeButton")
                    </c:when>
                </c:choose>
            </c:when>
            <c:when test="${section=='courses'}">
                <c:choose>
                    <c:when test="${subsection_get_courses}">
                        loadDeleteCourseForm("deleteCourseButton");
                    </c:when>
                    <c:when test="${subsection_add_correlative}">
                        loadCorrelativeForm("correlativeButton");
                    </c:when>
                </c:choose>
            </c:when>
        </c:choose>
    });
</script>
</body>
</html>
