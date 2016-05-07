<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | ${course.name} | <spring:message code="students"/>
    </title>
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
                        ${course.name} <small> - <spring:message code="students"/></small>
                    </h1>
                </div>
            </div>
            <!-- Content -->
            <jsp:include page="template/searchStudents.jsp" />
            <!-- Content -->
        </div>
    </div>
    <jsp:include page="base/footer.jsp" />
</div>
<!-- Scripts -->
<script type="text/javascript" charset="UTF-8"><%@include file="../js/template/searchStudents.js"%></script>
<script>
    $( document ).ready(function() {
        loadStudentSearch();
    });
</script>

</body>
</html>
