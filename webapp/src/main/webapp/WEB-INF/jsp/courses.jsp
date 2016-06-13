<%@ include file="base/tags.jsp" %>

<%--@elvariable id="section" type="java.lang.String"--%>
<%--@elvariable id="section2" type="java.lang.String"--%>
<%--@elvariable id="student" type="ar.edu.itba.paw.models.users.Student"--%>
<%--@elvariable id="course" type="ar.edu.itba.paw.models.Course"--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <%-- +++xchange when students is implemented --%>
        <c:choose>
            <c:when test="${section eq 'students'}"> <%--section2 == studentCourses--%>
                <c:choose>
                    <c:when test="${section2 eq 'courses'}">
                        <c:set var="title">
                            <spring:message code="webAbbreviation"/> | ${student.fullName} | <spring:message code="currentCourses"/>
                        </c:set>
                        <c:set var="pageHead">
                            ${student.fullName} <small> - <spring:message code="currentCourses"/></small>
                        </c:set>
                    </c:when>
                    <c:when test="${section2 eq 'grades'}">
                        <c:set var="title">
                            <spring:message code="webAbbreviation"/> | ${student.fullName} | <spring:message code="grades"/>
                        </c:set>
                        <c:set var="pageHead">
                            ${student.fullName} <small> - <spring:message code="grades"/></small>
                        </c:set>
                    </c:when>
                    <c:when test="${section2 eq 'inscription'}">
                        <c:set var="title">
                            <spring:message code="webAbbreviation"/> | ${student.fullName} | <spring:message code="inscriptions"/>
                        </c:set>
                        <c:set var="pageHead">
                            ${student.fullName} <small> - <spring:message code="inscriptions"/></small>
                        </c:set>
                    </c:when>
                </c:choose>
            </c:when>
            <c:when test="${section eq 'courses'}">
                <c:choose>
                    <c:when test="${section2 eq 'addCorrelative'}">
                        <c:set var="title">
                            <spring:message code="webAbbreviation"/> | ${course.name} | <spring:message code="add_correlative"/>
                        </c:set>
                        <c:set var="pageHead">
                            ${course.name} <small> - <spring:message code="add_correlative"/></small>
                        </c:set>
                    </c:when>
                    <c:otherwise>
                        <c:set var="title">
                            <spring:message code="webAbbreviation"/> | <spring:message code="courses"/>
                        </c:set>
                        <c:set var="pageHead">
                            <spring:message code="courses"/>
                        </c:set>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <c:set var="title">
                    <spring:message code="webAbbreviation"/>
                </c:set>
            </c:otherwise>
        </c:choose>
        ${title}
    </title>
    <jsp:include page="base/head.jsp" />
</head>
<body>

<div id="wrapper">
    <jsp:include page="base/sections.jsp" />
    <jsp:include page="base/nav.jsp" />

    <c:choose>
        <c:when test="${section=='students'}">
            <jsp:include page="template/enrollForm.jsp" />
            <c:if test="${subsection_courses}">
                <jsp:include page="template/gradeForm.jsp" />
            </c:if>
        </c:when>
    </c:choose>

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-xs-12">
                    <h1 class="page-header">
                        ${pageHead}
                    </h1>
                </div>
            </div>

            <!-- content -->
            <jsp:include page="base/alerts.jsp" />
            <jsp:include page="template/searchCourses.jsp" />

            <!-- /content -->

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->
    <jsp:include page="base/footer.jsp" />
</div>
<!-- Scripts -->
<script type="text/javascript" charset="UTF-8" src="<c:url value="/static/js/template/searchCourses.js" />"></script>

<c:choose>
    <c:when test="${section=='students'}">
        <script type="text/javascript" charset="UTF-8"  src="<c:url value="/static/js/template/enrollForm.js"/>"></script>
        <c:if test="${subsection_courses}">
            <script type="text/javascript" charset="UTF-8"  src="<c:url value="/static/js/template/gradeForm.js"/>"></script>
        </c:if>
    </c:when>
</c:choose>

<script>
    $( document ).ready(function() {
        loadSearchCourses();

        <c:choose>
        <c:when test="${section=='students'}">
        <c:choose>
        <c:when test="${subsection_enroll}">
        loadEnrollForm("inscription");
        </c:when>
        <c:when test="${subsection_courses}">
        loadEnrollForm("unenroll");
        loadGradeForm("gradeButton");
        </c:when>
        </c:choose>
        </c:when>
        </c:choose>
    });
</script>

<jsp:include page="base/scripts.jsp" />
</body>
</html>
