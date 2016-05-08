<%@ include file="tags.jsp" %>

<%--@elvariable id="section" type="ar.edu.itba.paw.webapp.controllers"--%>
<%--@elvariable id="section2" type="java.lang.String"--%>

<%-- WEB-INF files cannot be load with <script src="..."></script> (static ones can).
That's why we are usig <%@include ...%>--%>
<c:set var="includeScripts" value="" />
<c:set var="loadScripts" value="" />
<c:choose>





    <c:when test="${section=='admins'}">
        <c:set var="adminsActive" value="active" scope="request"/>
    </c:when>






    <c:when test="${section=='students'}">
        <c:set var="studentsActive" value="active" scope="request"/>

        <c:choose>
            <c:when test="${empty section2}">
                <jsp:include page="../template/studentsActionsPanel.jsp" />

            </c:when>

            <c:when test="${section2 eq 'addStudent'}">
                <c:set var="addStudentActive" value="active" scope="request"/>

                <jsp:include page="../template/studentsActionsPanel.jsp" />

                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file='/WEB-INF/js/cancelButton.js'%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} `
                    loadCancelButton("cancelButton");
                </c:set>
            </c:when>
        </c:choose>

    </c:when>







    <c:when test="${section=='courses'}">
        <c:set var="coursesActive" value="active" scope="request"/>

        <c:choose>
            <c:when test="${empty section2}">
                <jsp:include page="../template/coursesActionsPanel.jsp" />

            </c:when>

            <c:when test="${section2 eq 'addCourse'}">
                <c:set var="addCourseActive" value="active" scope="request"/>

                <jsp:include page="../template/coursesActionsPanel.jsp" />

                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file='/WEB-INF/js/cancelButton.js'%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} `
                    loadCancelButton("cancelButton");
                </c:set>
            </c:when>

            <c:otherwise>
                <jsp:include page="../template/courseActionsPanel.jsp" />

                <jsp:include page="../template/deleteCourseForm.jsp" />
                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file='/WEB-INF/js/template/deleteCourseForm.js'%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} ` loadDeleteCourseForm("deleteCourseButton");
                </c:set>
            </c:otherwise>
        </c:choose>

        <c:choose>
            <c:when test="${section2=='info'}">
                <c:set var="infoActive" value="active" scope="request"/>
                <jsp:include page="../template/CorrelativeForm.jsp" />
                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file='/WEB-INF/js/template/CorrelativeForm.js'%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} ` loadCorrelativeForm("deleteCorrelativeButton");
                </c:set>
            </c:when>

            <c:when test="${section2=='edit'}">
                <c:set var="editActive" value="active" scope="request"/>
                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file='/WEB-INF/js/cancelButton.js'%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} `
                    loadCancelButton("cancelButton");
                </c:set>
            </c:when>

            <c:when test="${section2=='students'}">
                <c:set var="courseStudentsActive" value="active" scope="request"/>

                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file="../../js/template/searchStudents.js"%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} ` loadStudentSearch();
                </c:set>
            </c:when>


            <c:when test="${section2=='addCorrelative'}">
                <c:set var="addCorrelativeActive" value="active" scope="request"/>

                <jsp:include page="../template/CorrelativeForm.jsp" />
                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file='/WEB-INF/js/template/CorrelativeForm.js'%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} ` loadCorrelativeForm("correlativeButton");
                </c:set>

<%--                <c:set var="searchCoursesActions" scope="request">
                    <button name="correlativeButton" class="btn btn-info btn-xs" type="button"
                            data-course_id="${ course.id }" data-course_name="${ course.name }"
                            data-correlative_id="${eachCourse.id}" data-correlative_name="${eachCourse.name}"
                            data-toggle="modal" data-target="#correlativeFormConfirmationModal">
                        <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="add_correlative"/>
                    </button>
                </c:set>--%>
            </c:when>
        </c:choose>
    </c:when>
</c:choose>

<%-- Prepare variables to be loaded from other JSPs --%>
<c:set var="includeScripts" value="${includeScripts}" scope="request" />
<c:set var="loadScripts" value="${loadScripts}" scope="request" />