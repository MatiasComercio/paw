<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | ${courseStudents.name} | <spring:message code="students"/>
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
                        ${courseStudents.name} <small> - <spring:message code="students"/></small>
                    </h1>
                </div>
            </div>
            <!-- Content -->
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><spring:message code="docket"/></th>
                    <th><spring:message code="firstName"/></th>
                    <th><spring:message code="lastName"/></th>
                    <th><spring:message code="actions"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${courseStudents.students}" var="student">
                    <tr>
                        <td>${ student.docket }</td>
                        <td>${ student.firstName }</td>
                        <td>${ student.lastName }</td>
                        <td>
                            <a href="<c:url value="/students/${student.docket}/info" />">
                                <spring:message code="see"/>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <!-- Content -->
        </div>
    </div>
</div>
<!-- Scripts -->
<jsp:include page="base/footer.jsp" />
</body>
</html>
