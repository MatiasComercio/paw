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

    <c:if test="${subsection_students}">
        <jsp:include page="template/deleteStudentForm.jsp" />
    </c:if>

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-xs-12">
                    <h1 class="page-header">
                        <c:choose>
                            <c:when test="${subsection_students}">
                                <spring:message code="students"/>
                            </c:when>
                            <c:when test="${subsection_courses}">
                                ${course.name} <small>- <spring:message code="students"/></small>
                            </c:when>
                        </c:choose>
                    </h1>
                </div>
            </div>

            <!-- content -->
            <div class="row">
                <div class="col-xs-12">
                    <jsp:include page="base/alerts.jsp" />
                </div>
            </div>
            <jsp:include page="template/searchStudents.jsp" />

            <!-- /content -->

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->
    <jsp:include page="base/footer.jsp" />
</div>
<!-- Scripts -->
<script type="text/javascript" charset="UTF-8"><%@include file="../js/template/searchStudents.js"%></script>
<c:if test="${subsection_students}">
    <script type="text/javascript" charset="UTF-8"><%@include file="../js/template/deleteStudentForm.js"%></script>
</c:if>
<script>
    $( document ).ready(function() {
        loadStudentSearch();
        <c:choose>
        <c:when test="${subsection_students}">
        loadDeleteStudentForm("deleteStudentButton");
        </c:when>
        </c:choose>
    });
</script>
</body>
</html>
