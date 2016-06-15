<%@include file="base/tags.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | <spring:message code="finalInscriptionDetail"/>  | ${finalInscription.id}
    </title>
    <jsp:include page="base/head.jsp" />
</head>
<body>
<div id="wrapper">

    <jsp:include page="base/sections.jsp" />
    <jsp:include page="base/nav.jsp" />
    <jsp:include page="template/finalGradeForm.jsp" />

    <div id="page-wrapper">
        <div class="container-fluid">
            <!-- Page Heading -->
            <div class="row">
                <div class="col-xs-12">
                    <h1 class="page-header">
                        <spring:message code="finalInscriptionDetail"/>
                    </h1>
                </div>
            </div>

            <!-- New details -->
            <div class="row">
                <form>
                    <div class="form-group col-xs-2">
                        <label class="control-label">
                            <span></span><spring:message code="id"/>
                        </label>
                        <p class="form-control-static active-overflow">${ finalInscription.course.courseId }</p>
                    </div>
                    <div class="form-group col-xs-6">
                        <label class="control-label">
                            <span></span><spring:message code="course"/>
                        </label>
                        <p class="form-control-static active-overflow">${finalInscription.course.name}</p>
                    </div>
                    <div class="form-group col-xs-2">
                        <label class="control-label">
                            <span></span><spring:message code="final_date"/>
                        </label>
                        <p class="form-control-static active-overflow">${finalInscription.finalExamDate}</p>
                    </div>
                    <div class="form-group col-xs-2">
                        <label class="control-label">
                            <span></span><spring:message code="final_vacancy"/>
                        </label>
                        <p class="form-control-static active-overflow"> ${ finalInscription.vacancy } / ${ finalInscription.maxVacancy }</p>
                    </div>
                </form>
            </div>
            <!-- /New details -->

            <!-- content -->
            <h2><spring:message code="inscribed" /></h2>
            <div class="table-responsive">
                <table class="table table-hover <%--table-bordered--%> <%--table-condensed--%>">
                    <thead>
                    <tr>
                        <th class="col-xs-1"><span><spring:message code="docket"/></span></th>
                        <th class="col-xs-1"><spring:message code="dni"/></th>
                        <th class="col-xs-2"><span><spring:message code="lastName"/></span></th>
                        <th class="col-xs-4"><spring:message code="name"/></th>
                        <th class="col-xs-4"><spring:message code="actions"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${empty studentsTakingFinal}">
                        <tr class="bg-warning">
                            <td colspan="5" class="text-danger text-center"><spring:message code="noStudentsTakingFinal"/></td>
                        </tr>
                    </c:if>
                    <c:forEach items="${studentsTakingFinal}" var="student">
                        <tr>
                            <td><span> ${ student.docket }</span></td>
                            <td><span> ${ student.dni }</span></td>
                            <td> ${ student.lastName }</td>
                            <td> ${ student.firstName }</td>
                            <td>
                                <sec:authorize access="hasAuthority('ROLE_ADD_GRADE')">
                                <button name="finalGradeButton" class="btn btn-info" type="button"
                                        data-docket="${student.docket}" data-dni="${student.dni}"
                                        data-finalInscriptionId="${ finalInscription.id }"
                                        data-firstName="${student.firstName}"
                                        data-lastName="${student.lastName}"
                                        data-course_code="${finalInscription.course.courseId}"
                                        data-course_id="${finalInscription.course.id}"
                                        data-course_name="${finalInscription.course.name }"
                                        data-toggle="modal"
                                        data-target="#finalGradeFormConfirmationModal">
                                    <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="rate"/>
                                </button>
                                </sec:authorize>
                            <td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
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