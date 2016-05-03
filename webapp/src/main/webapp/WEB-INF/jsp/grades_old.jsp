<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | ${student.fullName} | <spring:message code="grades"/>
    </title>
    <jsp:include page="base/head.jsp" />
</head>
<>

<div id="wrapper">

    <jsp:include page="base/nav.jsp" />
    <jsp:include page="template/gradeForm.jsp" />

    <div id="page-wrapper">
        <div class="container-fluid">
            <!-- Page Heading -->
            <div class="row">
                <div class="col-xs-12">
                    <h1 class="page-header">
                        <spring:message code="grades"/>
                    </h1>
                    <jsp:include page="base/alerts.jsp" />
                </div>
            </div>

            <div class="col-md-1">
                <%--<a href="/students/${student.docket}/grades/add" id="addGrade" type="button" class="btn btn-default">Agregar Nota</a>--%><%-- implemented in /students/{docket}/courses --%>
            </div>

            <!-- content -->
            <c:forEach items="${semesters}" var="semester" varStatus="loop">
            <h2>Cuatrimeste ${loop.index+1}</h2>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><spring:message code="id"/></th>
                    <th><spring:message code="course"/></th>
                    <th><spring:message code="grade"/></th>
                    <th><spring:message code="modified"/></th>
                    <th><spring:message code="actions"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${semester}" var="grade">
                    <tr>
                        <td>${ grade.courseId }</td>
                        <td>${ grade.courseName }</td>
                        <td>${ grade.grade }</td>
                        <td>${ grade.modified }</td>
                        <td>
                            <a class="btn btn-default btn-xs" href="<c:url value="/courses/${grade.courseId}/info" />" role="button">
                                <span class="fa fa-info-circle" aria-hidden="true"></span> <spring:message code="see"/> <spring:message code="course"/>
                            </a>
                            <button name="gradeButton" class="btn btn-info btn-xs" type="button"
                                        data-course_id="${ grade.courseId }" data-course_name="${ grade.courseName }"
                                        data-grade="${grade.grade}" data-modified="${grade.modified}" data-toggle="modal"
                                        data-target="#gradeFormConfirmationModal">
                                <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="edit"/>
                            </button>
                        <td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            </c:forEach>

            <!-- /content -->

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- Scripts -->
<jsp:include page="base/footer.jsp" />
<script type="text/javascript" charset="UTF-8"><%@include file="../js/template/gradeForm.js"%></script>

<script>
    $( document ).ready(function() {
        loadGradeForm("gradeButton")
    });


</script>

</body>
</html>