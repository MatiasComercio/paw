<%@ include file="../base/tags.jsp" %>

<!-- Course Action Panel definition -->
<%--@elvariable id="course" type="ar.edu.itba.paw.models.Course"--%>
<%--@elvariable id="infoActive" type="java.lang.String"--%>
<%--@elvariable id="editActive" type="java.lang.String"--%>
<%--@elvariable id="studentActionsActive" type="java.lang.String"--%>
<%--@elvariable id="addCorrelativeActive" type="java.lang.String"--%>
<sec:authorize access="hasAuthority('ROLE_VIEW_COURSE')">
    <c:set var="viewCourse">
        <li class="${infoActive}">
            <a href="<c:url value="/courses/${course.id}/info"/>" class="pushy-link">
                <i class="fa fa-info-circle" aria-hidden="true"></i> <spring:message code="information"/>
            </a>
        </li>
    </c:set>
</sec:authorize>
<sec:authorize access="hasAuthority('ROLE_EDIT_COURSE')">
    <c:set var="editCourse">
        <li class="${editActive}">
            <a href="<c:url value="/courses/${course.id}/edit"/>" class="pushy-link">
                <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="edit"/>
            </a>
        </li>
    </c:set>
</sec:authorize>
<sec:authorize access="hasAuthority('ROLE_VIEW_STUDENTS')">
    <c:set var="viewStudents">
        <li class="${studentActionsActive}">
            <a href="<c:url value="/courses/${course.id}/students" />" type="button" class="btn btn-info" role="button">
                <i class="fa fa-users" aria-hidden="true"></i> <spring:message code="students"/>
            </a>
        </li>
    </c:set>
</sec:authorize>
<sec:authorize access="hasAuthority('ROLE_ADD_CORRELATIVE')">
    <jsp:include page="CorrelativeForm.jsp" />
    <c:set var="addCorrelative">
        <li class="${addCorrelativeActive}">
            <a href="<c:url value="/courses/${course.id}/add_correlative" />" type="button" class="btn btn-info" role="button">
                <i class="fa fa-fw fa-list-alt"></i> <spring:message code="add_correlatives"/>
            </a>
        </li>
    </c:set>
</sec:authorize>
<sec:authorize access="hasAuthority('ROLE_DELETE_COURSE')">
    <jsp:include page="deleteCourseForm.jsp" />
    <c:set var="deleteCourse">
        <li>
            <button name="deleteCourseButton" class="menu-btn btn-danger" type="button"
                    data-course_id="${ course.id }" data-course_name="${ course.name }"
                    data-toggle="modal" data-target="#deleteCourseFormConfirmationModal">
                <span class="fa fa-trash" aria-hidden="true"></span> <spring:message code="delete"/>
            </button>
        </li>
    </c:set>
</sec:authorize>
<c:set var="currentActionsHeader" scope="request">
    <c:out value="${course.name}" />
</c:set>
<c:set var="currentActions" scope="request">
    ${viewCourse}, ${editCourse}, ${viewStudents}, ${addCorrelative}, ${deleteCourse}
</c:set>
<!-- /Course Action Panel definition -->