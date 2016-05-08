<%@ include file="../base/tags.jsp" %>

<!-- Students Action Panel definition -->
<%--@elvariable id="addStudentActive" type="java.lang.String"--%>

<sec:authorize access="hasAuthority('ROLE_ADD_STUDENT')">
    <c:set var="addStudent">
        <li class="${addStudentActive}">
            <a href="<c:url value="/students/add_student"/>" class="pushy-link">
                <i class="fa fa-plus-circle" aria-hidden="true"></i> <spring:message code="addStudent"/>
            </a>
        </li>
    </c:set>
</sec:authorize>
<c:set var="currentActionsHeader" scope="request">
    <spring:message code="students" />
</c:set>
<c:set var="currentActions" scope="request">
    ${addStudent}, <%--${editCourse}, ${viewStudents}, ${addCorrelative}, ${deleteCourse}--%>
</c:set>
<!-- /Students Action Panel definition -->