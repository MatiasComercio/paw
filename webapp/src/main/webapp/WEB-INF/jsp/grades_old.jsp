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

            <%--<div class="col-md-1">--%>
                <%--<a href="/students/${student.docket}/grades/add" id="addGrade" type="button" class="btn btn-default">Agregar Nota</a>--%><%-- implemented in /students/{docket}/courses --%>
            <%--</div>--%>
            <!-- Transcript details -->
            <div class="row">
                <div class="col-xs-12 col-md-6">
                    <div class="well">
                        <div class="row">

                            <div class="row">
                                <div class="col-xs-6 right-effect">
                                    <strong>Créditos aprobados</strong>
                                </div>
                                <div class="col-xs-6">
                                    ${passed_credits}/${total_credits}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-6 right-effect">
                                    <strong>Porcentaje de carrera completado</strong>
                                </div>
                                <div class="col-xs-6">
                                    ${percentage}%
                                </div>
                            </div>
                            <div class="row">
                               <div class="col-xs-8">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" aria-valuenow="${percentage}" aria-valuemin="0" aria-valuemax="100" style="width: ${percentage}%">
                                            <span class="sr-only">${percentage}% Completado</span>
                                        </div>
                                    </div>
                               </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /Transcript details -->
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
                        <td><c:if test="${grade.grade == null}">-</c:if>${ grade.grade }</td>
                        <td><c:if test="${grade.modified == null}">-</c:if>${ grade.modified }</td>
                        <td>
                            <a class="btn btn-default btn-xs" href="<c:url value="/courses/${grade.courseId}/info" />" role="button">
                                <span class="fa fa-info-circle" aria-hidden="true"></span> <spring:message code="see"/> <spring:message code="course"/>
                            </a>
                            <c:if test="${grade.modified != null }">
                            <button name="gradeButton" class="btn btn-info btn-xs" type="button"
                                        data-course_id="${ grade.courseId }" data-course_name="${ grade.courseName }"
                                        data-grade="${grade.grade}" data-modified="${grade.modified}" data-toggle="modal"
                                        data-target="#gradeFormConfirmationModal">
                                <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="edit"/>
                            </button>
                            </c:if>
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