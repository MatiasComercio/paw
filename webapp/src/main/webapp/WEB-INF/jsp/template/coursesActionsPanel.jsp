<%@ include file="../base/tags.jsp" %>

<!-- Courses Action Panel definition -->
<%--@elvariable id="course" type="ar.edu.itba.paw.models.Course"--%>
<%--@elvariable id="addCourseActive" type="java.lang.String"--%>

<sec:authorize access="hasAuthority('ROLE_ADD_COURSE')">
    <c:set var="addCourse">
        <li class="${addCourseActive}">
            <a href="<c:url value="/courses/add_course"/>" class="pushy-link">
                <i class="fa fa-plus-circle" aria-hidden="true"></i> <spring:message code="addCourse"/>
            </a>
        </li>
    </c:set>
</sec:authorize>
<c:set var="currentActionsHeader" scope="request">
    <spring:message code="courses" />
</c:set>
<c:set var="currentActions" scope="request">
    ${addCourse}, <%--${editCourse}, ${viewStudents}, ${addCorrelative}, ${deleteCourse}--%>
</c:set>
<!-- /Courses Action Panel definition -->