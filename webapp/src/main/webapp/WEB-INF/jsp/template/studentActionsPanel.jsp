<%@ include file="../base/tags.jsp" %>

<!-- Student Action Panel definition -->
<%--@elvariable id="student" type="ar.edu.itba.paw.models.Student"--%>
<%--@elvariable id="user" type="ar.edu.itba.paw.webapp.auth.UserSessionDetails"--%>
<%--@elvariable id="infoActive" type="java.lang.String"--%>
<%--@elvariable id="editActive" type="java.lang.String"--%>
<%--@elvariable id="changePasswordActive" type="java.lang.String"--%>
<%--@elvariable id="resetPasswordActive" type="java.lang.String"--%>
<%--@elvariable id="viewCoursesActive" type="java.lang.String"--%>
<%--@elvariable id="viewGradesActive" type="java.lang.String"--%>
<%--@elvariable id="viewInscriptionActive" type="java.lang.String"--%>
<sec:authorize access="hasAuthority('ROLE_VIEW_STUDENT')">
    <c:set var="viewStudent">
        <li class="${infoActive}">
            <a href="<c:url value="/students/${student.docket}/info"/>" class="pushy-link">
                <i class="fa fa-info-circle" aria-hidden="true"></i> <spring:message code="information"/>
            </a>
        </li>
    </c:set>
</sec:authorize>
<sec:authorize access="hasAuthority('ROLE_EDIT_STUDENT')">
    <c:set var="tmpEditStudent">
        <li class="${editActive}">
            <a href="<c:url value="/students/${student.docket}/edit"/>" class="pushy-link">
                <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="edit"/>
            </a>
        </li>
    </c:set>

    <%-- if (student.docket == logged.id || logged.hasAuthority(admin)) --> can edit --%>
    <c:choose>
        <c:when test="${student.docket eq user.id}">
            <c:set var="editStudent" value="${tmpEditStudent}" />
        </c:when>
        <c:otherwise>
            <c:set var="editStudent" value="" />
            <sec:authorize access="hasAuthority('ROLE_ADMIN')">
                <c:set var="editStudent" value="${tmpEditStudent}" />
            </sec:authorize>
        </c:otherwise>
    </c:choose>
</sec:authorize>
<sec:authorize access="hasAuthority('ROLE_CHANGE_PASSWORD')">
    <%-- Only the user can change his/her password --%>
    <c:if test="${student.docket eq user.id}">
        <c:set var="changePassword">
            <li class="${changePasswordActive}">
                <a href="<c:url value="/user/changePassword" />" class="pushy-link">
                    <i class="fa fa-key" aria-hidden="true"></i> <spring:message code="changePassword"/>
                </a>
            </li>
        </c:set>
    </c:if>
</sec:authorize>
<sec:authorize access="hasAuthority('ROLE_RESET_PASSWORD')">
    <%-- All admins and the user can change his/her password --%>
    <%-- +++xtodo --%>
    <%--<c:set var="resetPassword">
        <li class="${resetPasswordActive}">
            <a href="<c:url value="/courses/${course.id}/students" />" type="button" class="btn btn-info" role="button">
                <i class="fa fa-users" aria-hidden="true"></i> <spring:message code="students"/>
            </a>
        </li>
    </c:set>--%>
</sec:authorize>
<sec:authorize access="hasAuthority('ROLE_VIEW_COURSES')">
    <c:set var="viewCourses">
        <li class="${viewCoursesActive}">
            <a href="<c:url value="/students/${student.docket}/courses" />" class="pushy-link">
                <i class="fa fa-university"></i> <spring:message code="courses"/>
            </a>
        </li>
    </c:set>
</sec:authorize>
<sec:authorize access="hasAuthority('ROLE_VIEW_GRADES')">
    <c:set var="viewGrades">
        <li class="${viewGradesActive}">
            <a href="<c:url value="/students/${student.docket}/grades" />" class="pushy-link">
                <i class="fa fa-graduation-cap"></i> <spring:message code="grades"/>
            </a>
        </li>
    </c:set>
</sec:authorize>
<sec:authorize access="hasAuthority('ROLE_VIEW_INSCRIPTION')">
    <sec:authorize access="hasAuthority('ROLE_ADD_INSCRIPTION')">
        <c:set var="viewInscription">
            <li class="${viewInscriptionActive}">
                <a href="<c:url value="/students/${student.docket}/inscription" />" class="pushy-link">
                    <i class="fa fa-list-alt"></i> <spring:message code="inscriptions"/>
                </a>
            </li>
        </c:set>
    </sec:authorize>
</sec:authorize>
<sec:authorize access="hasAuthority('ROLE_DELETE_STUDENT')">
    <c:set var="deleteStudent">
        <li>
            <button name="deleteStudentButton" class="menu-btn btn-danger" type="button"
                    data-student_docket="${student.docket}"
                    data-student_first_name="${student.firstName}"
                    data-student_last_name="${student.lastName}"
                    data-toggle="modal" data-target="#deleteStudentFormConfirmationModal">
                <span class="fa fa-trash" aria-hidden="true"></span> <spring:message code="delete"/>
            </button>
        </li>
    </c:set>
</sec:authorize>
<c:set var="currentActionsHeader" scope="request">
    <c:out value="${student.fullName}" />
</c:set>
<c:set var="currentActions" scope="request">
    ${viewStudent}, ${editStudent}, ${changePassword}, ${resetPassword}, ${viewCourses},
    ${viewGrades}, ${viewInscription}, ${deleteStudent}
</c:set>
<!-- /Student Action Panel definition -->