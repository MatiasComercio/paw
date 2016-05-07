<%@ include file="base/tags.jsp" %>
<%--@elvariable id="course" type="ar.edu.itba.paw.models.Course"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | ${course.name}
    </title>
    <jsp:include page="base/head.jsp" />
    <link href="<c:url value="/static/css/course-detail.css" />" rel="stylesheet" type="text/css"/>
</head>
<body>
<div id="wrapper">
    <%-- +++xtodo: should include a "section setter" for each file--%>
    <c:choose>
        <c:when test="${section=='info'}">
            <c:set var="infoActive" value="active" />
        </c:when>
    </c:choose>
    <%-- Actions definition --%>
    <sec:authorize access="hasAuthority('ROLE_VIEW_COURSE')">
        <c:set var="viewCourse">
            <li class="${infoActive}">
                <a href="<c:url value="/courses/${course.id}/info"/>" class="pushy-link">
                    <i class="fa fa-info-circle" aria-hidden="true"></i> <spring:message code="information"/>
                </a>
            </li>
        </c:set>
    </sec:authorize>
    <sec:authorize access="hasAuthority('ROLE_EDIT_COURSE')">
        <c:set var="editCourse">
            <li>
                <a href="<c:url value="/courses/${course.id}/edit"/>" class="pushy-link">
                    <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="edit"/>
                </a>
            </li>
        </c:set>
    </sec:authorize>
    <sec:authorize access="hasAuthority('ROLE_VIEW_STUDENTS')">
        <c:set var="viewStudents">
            <li>
                <a href="<c:url value="/courses/${course.id}/students" />" type="button" class="btn btn-info" role="button">
                    <i class="fa fa-users" aria-hidden="true"></i> <spring:message code="students"/>
                </a>
            </li>
        </c:set>
    </sec:authorize>
    <sec:authorize access="hasAuthority('ROLE_ADD_CORRELATIVE')">
        <c:set var="addCorrelative">
            <li>
                <a href="<c:url value="/courses/${course.id}/add_correlative" />" type="button" class="btn btn-info" role="button">
                    <i class="fa fa-fw fa-list-alt"></i> <spring:message code="add_correlatives"/>
                </a>
            </li>
        </c:set>
    </sec:authorize>
    <sec:authorize access="hasAuthority('ROLE_DELETE_COURSE')">
        <jsp:include page="template/deleteCourseForm.jsp" />
        <c:set var="deleteCourse">
            <li>
                <button name="deleteCourseButton" class="menu-btn btn-danger" type="button"
                        data-course_id="${ course.id }" data-course_name="${ course.name }"
                        data-toggle="modal" data-target="#deleteCourseFormConfirmationModal">
                    <span class="fa fa-trash" aria-hidden="true"></span> <spring:message code="delete"/>
                </button>
            </li>
        </c:set>
    </sec:authorize>
    <c:set var="currentActionsHeader" scope="request">
        <c:out value="${course.name}" />
    </c:set>
    <c:set var="currentActions" scope="request">
        ${viewCourse}, ${editCourse}, ${viewStudents}, ${addCorrelative}, ${deleteCourse}
    </c:set>
    <%-- /actions definition --%>

    <jsp:include page="base/nav.jsp" />
    <jsp:include page="template/CorrelativeForm.jsp" />


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
            <!-- Content -->
            <div class="row">
                <div class="col-xs-12">
                    <jsp:include page="base/alerts.jsp" />
                </div>
            </div>
            <div class="row">
                <div class="col-xs-12">
                    <div class="well">
                        <div class="row">
                            <div class="row">
                                <div class="col-xs-3 right-effect">
                                    <strong><spring:message code="id"/></strong>
                                </div>
                                <div class="col-xs-9">
                                    ${course.id}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-3 right-effect">
                                    <strong><spring:message code="name"/></strong>
                                </div>
                                <div class="col-xs-9">
                                    ${course.name}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-3 right-effect">
                                    <strong><spring:message code="credits"/></strong>
                                </div>
                                <div class="col-xs-9">
                                    ${course.credits}
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-3 right-effect">
                                    <strong><spring:message code="semester"/></strong>
                                </div>
                                <div class="col-xs-9">
                                    ${course.semester}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
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
                                <th><spring:message code="id"/></th>
                                <th><spring:message code="course"/></th>
                                <th><spring:message code="credits"/></th>
                                <th><spring:message code="actions"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${correlatives}" var="correlative">
                                <tr>
                                    <td>${ correlative.id }</td>
                                    <td>${ correlative.name }</td>
                                    <td>${ correlative.credits }</td>
                                    <td>
                                        <a class="btn btn-default btn-sm" href="<c:url value="/courses/${correlative.id}/info" />" role="button">
                                            <span class="fa fa-info-circle" aria-hidden="true"></span> <spring:message code="see"/>
                                        </a>
                                    <td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>

            <!-- Content -->
        </div>
    </div>
</div>
<!-- Scripts -->
<jsp:include page="base/footer.jsp" />
<sec:authorize access="hasAuthority('ROLE_DELETE_COURSE')">
    <script type="text/javascript" charset="UTF-8"><%@include file="../js/template/deleteCourseForm.js"%></script>
</sec:authorize>
<sec:authorize access="hasAuthority('ROLE_ADD_CORRELATIVE')">
    <script type="text/javascript" charset="UTF-8"><%@include file="../js/template/addCorrelativeForm.js"%></script>
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
</script>

</body>
</html>
