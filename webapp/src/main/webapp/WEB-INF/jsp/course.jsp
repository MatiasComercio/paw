<%@ include file="base/tags.jsp" %>
<%--@elvariable id="course" type="ar.edu.itba.paw.models.Course"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | ${course.name} | <spring:message code="information" />
    </title>
    <jsp:include page="base/head.jsp" />
</head>
<body>
<div id="wrapper">
    <jsp:include page="base/sections.jsp" />
    <jsp:include page="template/courseActionsPanel.jsp" />
    <jsp:include page="base/nav.jsp" />

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                        ${course.name} <small> - <spring:message code="information"/></small>
                    </h1>
                </div>
            </div>
            <!-- /Page Heading -->

            <!-- Alerts -->
            <jsp:include page="base/alerts.jsp" />
            <!-- /Alerts -->

            <!-- Content -->
            <div class="row">
                <form>
                    <div class="form-group col-xs-2">
                        <label class="control-label">
                            <span></span><spring:message code="id"/>
                        </label>
                        <p class="form-control-static active-overflow">${course.courseId}</p>
                        <%--<label for="courseId"><spring:message code="id"/></label>--%>
                        <%--<input type="text" class="form-control" id="courseId" value="${course.courseId}" readonly>--%>
                    </div>
                    <div class="form-group col-xs-6">
                        <label class="control-label">
                            <span></span><spring:message code="name"/>
                        </label>
                        <p class="form-control-static active-overflow">${course.name}</p>
                        <%--<label for="name"><spring:message code="name"/></label>--%>
                        <%--<input type="text" class="form-control" id="name" value="${course.name}" readonly>--%>
                    </div>
                    <div class="form-group col-xs-2">
                        <label class="control-label">
                            <span></span><spring:message code="credits"/>
                        </label>
                        <p class="form-control-static active-overflow">${course.credits}</p>
                        <%--<label for="credits"><spring:message code="credits"/></label>--%>
                        <%--<input type="text" class="form-control" id="credits" value="${course.credits}" readonly>--%>
                    </div>
                    <div class="form-group col-xs-2">
                        <label class="control-label">
                            <span></span><spring:message code="semester"/>
                        </label>
                        <p class="form-control-static active-overflow">${course.semester}</p>
                        <%--<label for="semester"><spring:message code="semester"/></label>--%>
                        <%--<input type="text" class="form-control" id="semester" value="${course.semester}" readonly>--%>
                    </div>
                </form>
            </div>

            <%-- Correlatives --%>
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"> <spring:message code="correlatives"/> </h3>
                </div>
            </div>
            <%--@elvariable id="correlatives" type="java.util.List"--%>
            <c:choose>
                <c:when test="${empty correlatives}">
                    <div class="alert alert-info text-center">
                        <strong><spring:message code="noCorrelativesFound"/></strong>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="table-responsive">
                        <table class="table table-hover <%--table-bordered--%> <%--table-condensed--%>">
                            <thead>
                            <tr>
                                <th class="col-xs-1"><spring:message code="id"/></th>
                                <th class="col-xs-5"><spring:message code="course"/></th>
                                <th class="col-xs-2"><spring:message code="credits"/></th>
                                <th class="col-xs-4"><spring:message code="actions"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${correlatives}" var="correlative">
                                <tr>
                                    <td>${ correlative.id }</td>
                                    <td>${ correlative.name }</td>
                                    <td>${ correlative.credits }</td>
                                    <td>
                                        <a class="btn btn-default" href="<c:url value="/courses/${correlative.id}/info" />" role="button">
                                            <span class="fa fa-info-circle" aria-hidden="true"></span> <spring:message code="information"/>
                                        </a>
                                        <sec:authorize access="hasAuthority('ROLE_DELETE_CORRELATIVE')">
                                        <button name="deleteCorrelativeButton" class="btn btn-danger" type="button"
                                                data-course_id="${ course.id }" data-course_name="${ course.name }"
                                                data-correlative_id="${correlative.id}" data-correlative_name="${correlative.name}"
                                                data-toggle="modal" data-target="#correlativeFormConfirmationModal">
                                            <span class="fa fa-trash" aria-hidden="true"></span> <spring:message code="delete_correlative"/>
                                        </button>
                                        </sec:authorize>
                                    <td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>

            <!-- Content -->

            <!-- Final Exams -->
            <div class="row">
                <div class="col-lg-12">
                    <h3 class="page-header"> <spring:message code="finalDates"/> </h3>
                </div>
            </div>

            <%--@elvariable id="finalInscriptions" type="java.util.List"--%>
            <c:choose>
                <c:when test="${empty finalInscriptions}">
                    <div class="alert alert-info text-center">
                        <strong><spring:message code="noFinalInsctiptions"/></strong>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="table-responsive">
                        <table class="table table-hover <%--table-bordered--%> <%--table-condensed--%>">
                            <thead>
                            <tr>
                                <th class="col-xs-1"><spring:message code="id"/></th>
                                <th class="col-xs-5"><spring:message code="final_vacancy"/></th>
                                <th class="col-xs-2"><spring:message code="final_date"/></th>
                                <th class="col-xs-2"><spring:message code="state"/></th>
                                <th class="col-xs-4"><spring:message code="actions"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${finalInscriptions}" var="finalInscription">
                                <tr>
                                    <td>${ finalInscription.id }</td>
                                    <td>${ finalInscription.vacancy }/${finalInscription.maxVacancy}</td>
                                    <td>${ finalInscription.finalExamDate }</td>
                                    <td><c:choose>
                                        <c:when test="${finalInscription.state == 'OPEN'}">
                                            <spring:message code="open"/>
                                        </c:when>
                                        <c:otherwise>
                                            <spring:message code="closed"/>
                                        </c:otherwise>
                                    </c:choose>
                                    </td>
                                    <td>
                                        <a class="btn btn-default" href="<c:url value="/courses/final_inscription/${finalInscription.id}/info" />" role="button">
                                            <span class="fa fa-info-circle" aria-hidden="true"></span> <spring:message code="information"/>
                                        </a>
                                    <td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
            <!-- /Final Exams -->
        </div>
    </div>
    <jsp:include page="base/footer.jsp" />
</div>
<!-- Scripts -->
<%--<sec:authorize access="hasAuthority('ROLE_DELETE_COURSE')">
    <script type="text/javascript" charset="UTF-8">
        <%@include file="/static/js/template/deleteCourseForm.js"%>
    </script>
</sec:authorize>
<sec:authorize access="hasAuthority('ROLE_ADD_CORRELATIVE')">
    <script type="text/javascript" charset="UTF-8"><%@include file="/static/js/template/CorrelativeForm.js"%></script>
</sec:authorize>
<script>
    $( document ).ready(function() {
        <sec:authorize access="hasAuthority('ROLE_DELETE_COURSE')">
        loadDeleteCourseForm("deleteCourseButton");
        </sec:authorize>
        <sec:authorize access="hasAuthority('ROLE_ADD_CORRELATIVE')">
        loadCorrelativeForm("deleteCorrelativeButton");
        </sec:authorize>
    });
</script>--%>
<jsp:include page="base/scripts.jsp" />

</body>
</html>
