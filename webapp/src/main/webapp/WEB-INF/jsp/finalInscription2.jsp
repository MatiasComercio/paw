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

<script>
    $( document ).ready(function() {

        function loadfinalInscriptionForm(nameAttr) {
            /* Final Inscription Form Action Sequence */
            var loadfinalInscriptionFormButton = $("[name='" + nameAttr + "']");

            loadfinalInscriptionFormButton.on("click", function() {
                var inscriptionId = $(this).data("inscription_id");
                var courseName = $(this).data("course_name");
                var finalExamDate = $(this).data("finalExamDate");
                var vacancy = $(this).data("vacancy");

                var inscriptionForm = $("#final_inscription_form");
                inscriptionForm.find("input[name='id']").val(inscriptionId);
                inscriptionForm.find("input[name='courseName']").val(courseName);
                inscriptionForm.find("input[name='vacancy']").val(vacancy);
                inscriptionForm.find("input[name='finalExamDate']").val(finalExamDate);
            });

            $("#finalInscriptionFormConfirmAction").on("click", function() {
                $('#finalInscriptionFormConfirmationModal').modal('hide');
                $("#final_inscription_form").submit();
            });

            /* Remove focus on the modal trigger button */
            $('#finalInscriptionFormConfirmationModal').on('show.bs.modal', function(e){
                loadfinalInscription.one('focus', function(e){$(this).blur();});
            });
            /* /Inscription Form Action Sequence */
        }





        <c:choose>
        <c:when test="${section2=='final_inscription'}">
            loadfinalInscriptionForm("final_inscription");
        </c:when>
        </c:choose>
    });
</script>
<jsp:include page="base/scripts.jsp" />
</body>
</html>
