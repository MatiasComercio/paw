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

    <jsp:include page="base/nav.jsp" />

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                        <c:choose>
                            <c:when test="${task == 'add' }">
                                <spring:message code="courses"/><small> - <spring:message code="add"/></small>
                            </c:when>
                            <c:when test="${task == 'edit' }">
                                ${courseName}<small> - <spring:message code="edit"/></small>
                            </c:when>
                        </c:choose>
                    </h1>
                </div>
            </div>

            <!-- -->
            <div class="container">
                <jsp:include page="base/alerts.jsp" />
                <c:if test="${task == 'add' }">
                    <form:form modelAttribute="courseForm" method="post" action="/courses/add_course">
                        <div class="form-group">
                            <form:label path="id"><spring:message code="id"/>:</form:label>
                            <form:input path="id" type="text" class="form-control" />
                            <form:errors path="id" cssStyle="color: red;" element="div"/>
                        </div>
                        <div class="form-group">
                            <form:label path="name"><spring:message code="name"/>:</form:label>
                            <form:input type="text" class="form-control" path="name"/>
                            <form:errors path="name" cssStyle="color: red;" element="div"/>
                        </div>
                        <div class="form-group">
                            <form:label path="credits"><spring:message code="credits"/>:</form:label>
                            <form:input type="text" class="form-control" path="credits"/>
                            <form:errors path="credits" cssStyle="color: red;" element="div"/>
                        </div>
                        <spring:message code="addCourse" var="buttonValue"/>
                        <input type="submit" class="btn btn-info" value="${buttonValue}"/>
                    </form:form>
                </c:if>
                <c:if test="${task == 'edit' }">
                    <form:form modelAttribute="courseForm" method="post" action="/courses/${courseId}/edit">
                        <div class="form-group">
                            <form:label path="id"><spring:message code="id"/>:</form:label>
                            <form:input path="id" type="text" class="form-control" />
                            <form:errors path="id" cssStyle="color: red;" element="div"/>
                        </div>
                        <div class="form-group">
                            <form:label for="name" path="name"><spring:message code="name"/>:</form:label>
                            <form:input type="text" class="form-control" id="name" path="name"/>
                        </div>
                        <div class="form-group">
                            <form:label for="credits" path="credits"><spring:message code="credits"/>:</form:label>
                            <form:input type="text" class="form-control" id="credits" path="credits"/>
                        </div>
                        <spring:message code="saveChanges" var="saveChangesButton"/>
                        <input type="submit" class="btn btn-info" value="${saveChangesButton}"/>
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