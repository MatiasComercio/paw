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

            <jsp:include page="base/alerts.jsp" />

            <!-- Details -->
            <div class="row">
                <div class="col-xs-12 col-md-5">
                    <div class="well">
                        <div class="row">

                            <div class="col-xs-12">
                                <div class="row">
                                    <div class="col-xs-9">
                                        <strong><spring:message code="id" /></strong>
                                    </div>
                                    <div class="col-xs-3 right-effect">
                                        ${ finalInscription.course.courseId }
                                    </div>
                                </div>
                            </div>

                            <div class="col-xs-12">
                                <div class="row">
                                    <div class="col-xs-9">
                                        <strong><spring:message code="course" /></strong>
                                    </div>
                                    <div class="col-xs-3 right-effect">
                                        ${ finalInscription.course.name }
                                    </div>
                                </div>
                            </div>

                            <div class="col-xs-12">
                                <div class="row">
                                    <div class="col-xs-7">
                                        <strong><spring:message code="final_date" /></strong>
                                    </div>
                                    <div class="col-xs-5 right-effect">
                                        ${ finalInscription.finalExamDate }
                                    </div>
                                </div>
                            </div>

                            <div class="col-xs-12">
                                <div class="row">
                                    <div class="col-xs-9">
                                        <strong><spring:message code="final_vacancy" /></strong>
                                    </div>
                                    <div class="col-xs-3 right-effect">
                                        ${ finalInscription.vacancy } / ${ finalInscription.maxVacancy }
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            <!-- /Details -->

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
<script>
    $(document).ready(function() {
        function loadFinalGradeForm(nameAttr) {
            /* Grade Form Action Sequence */
            var gradeFormButton = $("[name='" + nameAttr + "']");

            gradeFormButton.on("click", function () {
                var docket = $(this).data("docket");
                var courseId = $(this).data("course_id");
                var courseName = $(this).data("course_name");
                var courseCodId = $(this).data("course_code");
                var url = $(this).data("url");
                var gradeForm = $("#final_grade_form");
                gradeForm.find("input[name='docket']").val(docket);
                gradeForm.find("input[name='courseId']").val(courseId);
                gradeForm.find("input[name='courseName']").val(courseName);
                gradeForm.find("input[name='courseCodId']").val(courseCodId);
                gradeForm.attr("action", url);


            });

            $("#finalGradeFormConfirmAction").on("click", function () {
                $('#finalGradeFormConfirmationModal').modal('hide');
                $("#final_grade_form").submit();
            });

            /* Remove focus on the modal trigger button */
            $('#finalGradeFormConfirmationModal').on('show.bs.modal', function (e) {
                gradeFormButton.one('focus', function (e) {
                    $(this).blur();
                });
            });
            /* /Grade Form Action Sequence */
        }

        loadFinalGradeForm("finalGradeButton");
    });
</script>

</body>
</html>