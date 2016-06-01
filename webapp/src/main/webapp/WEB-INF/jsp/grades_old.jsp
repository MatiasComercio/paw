<%@ taglib prefix="s" uri="http://www.springframework.org/security/tags" %>
<%@include file="base/tags.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | ${student.fullName} | <spring:message code="grades"/>
    </title>
    <jsp:include page="base/head.jsp" />
</head>
<body>
<div id="wrapper">

    <jsp:include page="base/sections.jsp" />
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
                </div>
            </div>

            <jsp:include page="base/alerts.jsp" />

            <div class="row">
                <div class="col-xs-12 col-md-10">
                    <div class="well">
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="row">
                                    <div class="col-xs-9">
                                        <strong><spring:message code="passedCredits" /></strong>
                                    </div>
                                    <div class="col-xs-3 right-effect">
                                        ${passed_credits}/${total_credits}
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12">
                                <div class="row">
                                    <div class="col-xs-9">
                                        <strong><spring:message code="carrearPercentage" /></strong>
                                    </div>
                                    <div class="col-xs-3 right-effect">
                                        ${percentage}%
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12">
                                <div class="progress">
                                    <div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar" aria-valuenow="${percentage}" aria-valuemin="0" aria-valuemax="100" style="width: ${percentage}%">
                                        <span class="sr-only">${percentage}% Completado</span>
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
                <h2><spring:message code="semester" /> ${loop.index+1}</h2>
                <div class="table-responsive">
                    <table class="table table-hover <%--table-bordered--%> <%--table-condensed--%>">
                        <thead>
                        <tr>
                            <th class="col-xs-1"><span><spring:message code="id"/></span>
                            <th class="col-xs-4"><span><spring:message code="course"/></span></th>
                            <th class="col-xs-1"><spring:message code="grade"/></th>
                            <th class="col-xs-1"><spring:message code="finalGrade"/></th>
                            <th class="col-xs-2"><spring:message code="modified"/></th>
                            <th class="col-xs-3"><spring:message code="actions"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${semester}" var="grade">
                            <tr>
                                <td><span> ${ grade.courseId }</span></td>
                                <td><span> ${ grade.courseName }</span></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${grade.taking}">
                                            <span class="text-info"><spring:message code="coursing" /></span>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${grade.grade >= 4}">
                                                    <c:set var="gradeColorClass" value="text-success" />
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="gradeColorClass" value="text-danger" />
                                                </c:otherwise>
                                            </c:choose>
                                            <span class="${gradeColorClass}">${ grade.grade }</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:forEach items="${grade.finalGrades}" var="finalGrade">
                                        <div class="row">
                                        <c:choose>
                                            <c:when test="${finalGrade.grade >= 4}">
                                                <c:set var="gradeColorClass" value="text-success" />
                                            </c:when>
                                            <c:otherwise>
                                                <c:set var="gradeColorClass" value="text-danger" />
                                            </c:otherwise>
                                        </c:choose>
                                        <span class="${gradeColorClass}">${ finalGrade.grade }</span>
                                        </div>
                                    </c:forEach>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${grade.modified eq null}">
                                            -
                                        </c:when>
                                        <c:otherwise>
                                            ${ grade.modified }
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a class="btn btn-default" href="<c:url value="/courses/${grade.courseId}/info" />" role="button">
                                        <span class="fa fa-info-circle" aria-hidden="true"></span> <spring:message code="courseInformation"/>
                                    </a>
                                    <c:if test="${grade.modified != null }">
                                    <s:authorize access="hasAuthority('ROLE_EDIT_GRADE')">
                                    <c:if test="${grade.canEdit}">
                                        <button name="gradeButton" class="btn btn-info" type="button"
                                                data-id="${grade.id}"
                                                data-course_id="${ grade.courseId }" data-course_name="${ grade.courseName }"
                                                data-grade="${grade.grade}" data-modified="${grade.modified}" data-toggle="modal"
                                                data-target="#gradeFormConfirmationModal">
                                            <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="edit"/>
                                        </button>
                                    </c:if>
                                    </s:authorize>
                                    </c:if>
                                <td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:forEach>

            <!-- /content -->

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