<%@ include file="base/tags.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <%-- +++xchange when students is implemented --%>
        <c:choose>
            <c:when test="${section eq 'students'}">
                <spring:message code="webAbbreviation"/> | ${student.fullName} | <spring:message code="courses"/>
            </c:when>
            <c:when test="${section eq 'courses'}">
                <c:choose>
                    <c:when test="${section2 eq 'addCorrelative'}">
                        <spring:message code="webAbbreviation"/> | ${course.name} | <spring:message code="add_correlative"/>
                    </c:when>
                    <c:otherwise>
                        <spring:message code="webAbbreviation"/> | <spring:message code="courses"/>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise><spring:message code="webAbbreviation"/></c:otherwise>
        </c:choose>
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
                        <c:choose>
                            <c:when test="${section=='students'}">
                                <c:choose>
                                    <c:when test="${subsection_enroll}">
                                        <spring:message code="inscriptions"/> <small>- <spring:message code="availableCourses"/></small>
                                    </c:when>
                                    <c:when test="${subsection_courses}">
                                        <spring:message code="courses"/>
                                    </c:when>
                                </c:choose>
                            </c:when>
                            <c:when test="${section=='courses'}">
                                <c:choose>
                                    <c:when test="${section2 eq 'addCorrelative'}">
                                        ${course.name} <small>- <spring:message code="add_correlative"/></small>
                                    </c:when>
                                    <c:otherwise>
                                        <spring:message code="courses"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:otherwise>
                                <spring:message code="webAbbreviation"/>
                            </c:otherwise>
                        </c:choose>
                    </h1>
                </div>
            </div>

            <!-- content -->
            <jsp:include page="base/alerts.jsp" />
            <jsp:include page="template/searchCourses.jsp"/>
            <%--            <jsp:include page="template/searchCourses.jsp">
                            <jsp:param name="searchCoursesActions" value="${searchCoursesActions}"/>
                        </jsp:include>--%>

            <!-- /content -->

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->
    <jsp:include page="base/footer.jsp" />
</div>
<!-- Scripts -->
<script type="text/javascript" charset="UTF-8"><%@include file="../js/template/searchCourses.js"%></script>

<c:choose>
    <c:when test="${section=='students'}">
        <script type="text/javascript" charset="UTF-8"><%@include file="../js/template/enrollForm.js"%></script>
        <c:if test="${subsection_courses}">
            <script type="text/javascript" charset="UTF-8"><%@include file="../js/template/gradeForm.js"%></script>
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
        loadGradeForm("gradeButton")
        </c:when>
        </c:choose>
        </c:when>
        </c:choose>
    });
</script>

<jsp:include page="base/scripts.jsp" />
</body>
</html>
