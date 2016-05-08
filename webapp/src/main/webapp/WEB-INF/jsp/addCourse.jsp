<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> |
        <c:choose>
            <c:when test="${task == 'add' }">
                <spring:message code="addCourse"/>
            </c:when>
            <c:when test="${task == 'edit' }">
                <spring:message code="editCourse"/>
            </c:when>
        </c:choose>
    </title>
    <jsp:include page="base/head.jsp" />
</head>
<body>

<div id="wrapper">

    <jsp:include page="base/sections.jsp" />
    <jsp:include page="template/courseActionsPanel.jsp" />
    <jsp:include page="base/nav.jsp" />

    <%-- Task decision --%>
    <%--@elvariable id="task" type="java.lang.String"--%>
    <%--@elvariable id="course" type="ar.edu.itba.paw.models.Course"--%>
    <c:choose>
        <c:when test="${task eq 'add'}" >
            <c:set var="title">
                <spring:message code="courses"/><small> - <spring:message code="add"/></small>
            </c:set>
            <c:set var="formAction">
                <c:url value="/courses/add_course" />
            </c:set>
            <spring:message var="formButton" code="addCourse"/>
        </c:when>
        <c:when test="${task eq 'edit'}" >
            <c:set var="title">
                ${course.name}<small> - <spring:message code="edit"/></small>
            </c:set>
            <c:set var="formAction">
                <c:url value="/courses/${course.id}/edit" />
            </c:set>
            <spring:message var="formButton" code="saveChanges"/>
        </c:when>
    </c:choose>

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                        ${title}
                    </h1>
                </div>
            </div>
            <!-- /Page Heading -->

            <!-- Alerts -->
            <jsp:include page="base/alerts.jsp" />
            <!-- /Alerts -->

            <!-- Content -->
            <div class="row">
                <form:form modelAttribute="courseForm" method="post" action="${formAction}">
                    <div class="form-group col-xs-12">
                        <form:label path="id"><spring:message code="id"/>:</form:label>
                        <form:input path="id" type="text" class="form-control" />
                        <form:errors path="id" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group col-xs-12">
                        <form:label path="name"><spring:message code="name"/>:</form:label>
                        <form:input type="text" class="form-control" path="name"/>
                        <form:errors path="name" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group col-xs-12">
                        <form:label path="credits"><spring:message code="credits"/>:</form:label>
                        <form:input type="text" class="form-control" path="credits"/>
                        <form:errors path="credits" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group col-xs-12">
                        <form:label path="semester"><spring:message code="semester"/>:</form:label>
                        <form:input type="text" class="form-control" path="semester"/>
                        <form:errors path="semester" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="col-sm-4 hidden-xs"></div>
                    <div class="col-xs-6 col-sm-2 text-center">
                        <a id="cancelButton" class="btn btn-default center-block" role="button">
                            <spring:message code="cancel"/>
                        </a>
                    </div>
                    <div class="col-xs-6 col-sm-2 text-center">
                        <button type="submit" class="btn btn-info center-block">
                                ${formButton}
                        </button>
                    </div>
                </form:form>
            </div>
            <!-- /Content -->

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->
    <jsp:include page="base/footer.jsp" />
</div>
<!-- Scripts -->
<jsp:include page="base/scripts.jsp" />
</body>
</html>