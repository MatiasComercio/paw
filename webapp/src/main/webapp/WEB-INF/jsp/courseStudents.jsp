<%--@elvariable id="course" type="ar.edu.itba.paw.models.Course"--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <c:choose>
            <c:when test="${section2 eq 'students'}">
                <c:set var="title">
                    <spring:message code="webAbbreviation"/> | ${course.name} | <spring:message code="students"/>
                </c:set>
                <c:set var="pageHead">
                    ${course.name} <small> - <spring:message code="enrolledStudents"/></small>
                </c:set>
            </c:when>
            <c:when test="${section2 eq 'studentsPassed'}">
                <c:set var="title">
                    <spring:message code="webAbbreviation"/> | ${course.name} | <spring:message code="studentsApprovedHistory"/>
                </c:set>
                <c:set var="pageHead">
                    ${course.name} <small> - <spring:message code="studentsApprovedHistory"/></small>
                </c:set>
            </c:when>
        </c:choose>
        ${title}
    </title>
    <jsp:include page="base/head.jsp" />
</head>
<body>
<div id="wrapper">
    <jsp:include page="base/sections.jsp" />
    <jsp:include page="template/courseActionsPanel.jsp" />
    <c:if test="${section2 eq 'students'}">
        <jsp:include page="template/gradeForm.jsp" />
    </c:if>
    <jsp:include page="base/nav.jsp" />

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                       ${pageHead}
                    </h1>
                </div>
            </div>

            <jsp:include page="base/alerts.jsp" />

            <!-- Content -->
            <jsp:include page="template/searchStudents.jsp" />
            <!-- Content -->
        </div>
    </div>
    <jsp:include page="base/footer.jsp" />
</div>
<!-- Scripts -->
<jsp:include page="base/scripts.jsp" />
</body>
</html>
