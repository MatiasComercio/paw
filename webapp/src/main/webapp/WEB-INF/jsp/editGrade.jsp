<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | <spring:message code="editGrade"/>
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
                        <spring:message code="student"/> ${docket} - ${courseName}
                    </h1>
                </div>
            </div>

            <!-- -->
            <div class="container">
                <jsp:include page="base/alerts.jsp" />
                <h2><spring:message code="editGrade"/></h2>
                <form:form modelAttribute="gradeForm" method="post" action="/students/${docket}/grades/edit">
                    <div class="form-group">
                        <form:label path="grade"><spring:message code="grade"/>:</form:label>
                        <form:input type="text" class="form-control" path="grade"/>
                        <form:errors path="grade" cssStyle="color: red;" element="div"/>
                    </div>
                    <spring:message code="saveChanges" var="saveChangesButton"/>
                    <input type="submit" class="btn btn-info" value="${saveChangesButton}"/>
                </form:form>
            </div>

            <!-- -->

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->
    <jsp:include page="base/footer.jsp" />
</div>
<!-- Scripts -->
</body>
</html>
