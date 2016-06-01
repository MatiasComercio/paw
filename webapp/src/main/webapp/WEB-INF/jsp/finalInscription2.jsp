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

    <jsp:include page="base/nav.jsp" />

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-xs-12">
                    <h1 class="page-header">
                        <spring:message code="final_inscription"/>
                    </h1>
                </div>
            </div>

            <!-- content -->
            <div class="row">
                <div class="col-xs-12">
                    <jsp:include page="base/alerts.jsp" />
                </div>
            </div>

            <!-- /content -->

            <div class="container">
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
                                <td>${ inscription.id }</td>
                                <td>${ inscription.course.name}</td>
                                <td>${ inscription.finalExamDate}</td>
                                <td>${ inscription.vacancy }</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${section eq 'students'}">
                                            <c:choose>
                                                <c:when test="${section2 eq 'final_inscription'}">
                                                    <button name="final_inscription" class="btn btn-info" type="button"
                                                            data-inscription_id="${ inscription.id }" data-course_name="${ inscription.course.name }"
                                                            data-vacancy="${ inscription.vacancy}" data-finalExamDate="${ inscription.finalExamDate}
                                                            data-toggle="modal" data-target="#finalInscriptionFormConfirmationModal">
                                                    <span class="fa fa-list-alt" aria-hidden="true"></span> <spring:message code="enroll"/>
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
            </div>

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->
    <jsp:include page="base/footer.jsp" />
</div>
<!-- Scripts -->
<jsp:include page="base/scripts.jsp" />
<%--<script type="text/javascript" charset="UTF-8"><%@include file="../js/template/searchStudents.js"%></script>--%>
<%--<c:if test="${subsection_students}">
    <script type="text/javascript" charset="UTF-8"><%@include file="../js/template/deleteStudentForm.js"%></script>
</c:if>--%>
<%--<script>
    $( document ).ready(function() {
        loadSearchStudents();
/*        <c:choose>
        <c:when test="${subsection_students}">
        loadDeleteStudentForm("deleteStudentButton");
        </c:when>
        </c:choose>*/
    });
</script>--%>
</body>
</html>
