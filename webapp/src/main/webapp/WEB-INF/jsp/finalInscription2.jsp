<%@ include file="base/tags.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | <spring:message code="students"/>
    </title>
    <jsp:include page="base/head.jsp" />
</head>
<body>

<div id="wrapper">

    <jsp:include page="base/sections.jsp" />
    <jsp:include page="base/nav.jsp" />
    <jsp:include page="template/finalInscriptionForm.jsp" />
    <jsp:include page="template/finalInscriptionDropForm.jsp" />


    <div id="page-wrapper">

        <!-- container-fluid -->
        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-xs-12">
                    <h1 class="page-header">
                        <spring:message code="final_inscription"/>
                    </h1>
                </div>
            </div>

            <div class="row">
                <div class="col-xs-12">
                    <jsp:include page="base/alerts.jsp" />
                </div>
            </div>

            <!-- content -->
            <div class="table-responsive">
                <table class="table table-hover <%--table-bordered--%> <%--table-condensed--%>">
                    <thead>
                    <tr>
                        <th><spring:message code="id"/></th>
                        <th><spring:message code="course"/></th>
                        <th><spring:message code="final_date"/></th>
                        <th><spring:message code="final_vacancy"/></th>
                        <th><spring:message code="actions"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${empty finalInscriptions}">
                        <tr class="bg-warning">
                            <td colspan="5" class="text-danger text-center"><spring:message code="noFinalInscriptionsFound"/></td>
                        </tr>
                    </c:if>
                    <c:forEach items="${finalInscriptions}" var="inscription">
                        <tr>
                            <td>${ inscription.course.courseId }</td>
                            <td>${ inscription.course.name}</td>
                            <td>${ inscription.finalExamDate}</td>
                            <td>${ inscription.vacancy } / ${ inscription.maxVacancy }</td>
                            <td>
                                <c:choose>
                                    <c:when test="${section eq 'students'}">
                                        <c:choose>
                                            <c:when test="${section2 eq 'final_inscription'}">
                                                <button name="final_inscription" class="btn btn-info" type="button"
                                                        data-inscription_id="${ inscription.id }" data-course_name="${ inscription.course.name }"
                                                        data-course_id="${inscription.course.id}"
                                                        data-course_code="${inscription.course.courseId}"
                                                        data-vacancy="${ inscription.vacancy}" data-final_exam_date="${ inscription.finalExamDate}"
                                                        data-toggle="modal" data-target="#finalInscriptionFormConfirmationModal">
                                                    <span class="fa fa-list-alt" aria-hidden="true"></span>
                                                    <spring:message code="finalInscriptionButton"/>
                                                </button>
                                            </c:when>
                                        </c:choose>
                                    </c:when>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <!--Final exams the user is currently sitting for-->


            <div class="row">
                <div class="col-xs-12">
                    <h1 class="page-header">
                        <spring:message code="finals_being_taken"/>
                    </h1>
                </div>
            </div>

            <div class="table-responsive">
                <table class="table table-hover <%--table-bordered--%> <%--table-condensed--%>">
                    <thead>
                    <tr>
                        <th><spring:message code="id"/></th>
                        <th><spring:message code="course"/></th>
                        <th><spring:message code="final_date"/></th>
                        <th><spring:message code="final_vacancy"/></th>
                        <th><spring:message code="actions"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${empty finalInscriptionsTaken}">
                        <tr class="bg-warning">
                            <td colspan="5" class="text-danger text-center"><spring:message code="noFinalInscriptionsTakenFound"/></td>
                        </tr>
                    </c:if>
                    <c:forEach items="${finalInscriptionsTaken}" var="inscription">
                        <tr>
                            <td>${ inscription.course.courseId }</td>
                            <td>${ inscription.course.name}</td>
                            <td>${ inscription.finalExamDate}</td>
                            <td>${ inscription.vacancy } / ${ inscription.maxVacancy }</td>
                            <td>
                                <c:choose>
                                    <c:when test="${section eq 'students'}">
                                        <c:choose>
                                            <c:when test="${section2 eq 'final_inscription'}">
                                                <button name="final_inscription_drop" class="btn btn-danger" type="button"
                                                        data-inscription_id="${ inscription.id }" data-course_name="${ inscription.course.name }"
                                                        data-course_id="${inscription.course.id}"
                                                        data-course_code="${inscription.course.courseId}"
                                                        data-vacancy="${ inscription.vacancy}" data-final_exam_date="${ inscription.finalExamDate}"
                                                        data-toggle="modal" data-target="#finalInscriptionFormDropModal">
                                                    <span class="fa fa-list-alt" aria-hidden="true"></span>
                                                    <spring:message code="finalInscriptionDropButton"/>
                                                </button>
                                            </c:when>
                                        </c:choose>
                                    </c:when>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <!-- content -->
        </div>
        <!-- /container-fluid -->
    </div>
    <!-- /#page-wrapper -->
    <jsp:include page="base/footer.jsp" />
</div>
<jsp:include page="base/scripts.jsp" />
</body>
</html>
