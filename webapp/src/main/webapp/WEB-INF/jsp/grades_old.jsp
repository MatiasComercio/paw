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
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><spring:message code="id"/></th>
                    <th><spring:message code="course"/></th>
                    <th><spring:message code="grade"/></th>
                    <th><spring:message code="modified"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${student.grades}" var="grade">
                    <tr>
                        <td>${ grade.courseId }</td>
                        <td>${ grade.courseName }</td>
                        <td>${ grade.grade }</td>
                        <td>${ grade.modified }</td>
                        <td><a href="<c:url value="/courses/${grade.courseId}/info" />"><spring:message code="see"/> <spring:message code="course"/></a></td>

                        <td><button name="gradeButton" class="btn btn-info btn-xs" type="button"
                                    data-course_id="${ grade.courseId }" data-course_name="${ grade.courseName }"
                                    data-grade="${grade.grade}" data-modified="${grade.modified}" data-toggle="modal"
                                    data-target="#gradeFormConfirmationModal">
                            <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="edit"/>
                        </button><td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

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
        //Function TODO: Create editGradeForm.js
        /*function edit_loadGradeForm(nameAttr) {

            $("[name='" + nameAttr + "']").on("click", function() {
                var courseId = $(this).data("course_id");
                var courseName = $(this).data("course_name");
                var modified = $(this).data("modified");
                var gradeForm = $("#grade_form");
                gradeForm.find("input[name='courseId']").val(courseId);
                gradeForm.find("input[name='courseName']").val(courseName);
                gradeForm.find("input[name='modified']").val(modified);

                var grade = $(this).data("grade");
                if (grade != null){
                    gradeForm.find("input[name='grade']").val(grade);
                }
                //TODO: DELETE $("#grade_form").submit();
            });

            $("#gradeFormConfirmAction").on("click", function() {
             $('#gradeFormConfirmationModal').modal('hide');
             $("#grade_form").submit();
             });

        }*/
        //END function

        loadGradeForm("gradeButton")
    });


</script>

</body>
</html>