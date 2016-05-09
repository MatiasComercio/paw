<%@ include file="../base/tags.jsp" %>

<!-- Student Action Panel definition -->
<%--@elvariable id="admin" type="ar.edu.itba.paw.models.users.Admin"--%>
<%--@elvariable id="user" type="ar.edu.itba.paw.webapp.auth.UserSessionDetails"--%>
<%--@elvariable id="infoActive" type="java.lang.String"--%>
<%--@elvariable id="editActive" type="java.lang.String"--%>
<%--@elvariable id="changePasswordActive" type="java.lang.String"--%>
<%--@elvariable id="resetPasswordActive" type="java.lang.String"--%>
<%--@elvariable id="coursesStudentActive" type="java.lang.String"--%>
<%--@elvariable id="gradesActive" type="java.lang.String"--%>
<%--@elvariable id="inscriptionActive" type="java.lang.String"--%>
<sec:authorize access="hasAuthority('ROLE_VIEW_ADMIN')">
    <c:choose>
        <c:when test="${admin.dni eq user.id}">
            <c:set var="profileMessage">
                <spring:message code="profile" />
            </c:set>
        </c:when>
        <c:otherwise>
            <c:set var="profileMessage">
                <spring:message code="profile" />
            </c:set>
        </c:otherwise>
    </c:choose>
    <c:set var="viewAdmin">
        <li class="${infoActive}">
            <a href="<c:url value="/admins/${admin.dni}/info"/>" class="pushy-link">
                <i class="fa fa-user" aria-hidden="true"></i> ${profileMessage}
            </a>
        </li>
    </c:set>
</sec:authorize>
<sec:authorize access="hasAuthority('ROLE_EDIT_ADMIN')">
    <c:set var="tmpEditAdmin">
        <li class="${editActive}">
            <a href="<c:url value="/admins/${admin.dni}/edit"/>" class="pushy-link">
                <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="edit"/>
            </a>
        </li>
    </c:set>

    <%-- if (student.docket == logged.id || logged.hasAuthority(admin)) --> can edit --%>
    <c:choose>
        <c:when test="${admin.dni eq user.id}">
            <c:set var="editAdmin" value="${tmpEditAdmin}" />
        </c:when>
        <c:otherwise>
            <c:set var="editAdmin" value="" />
            <sec:authorize access="hasAuthority('ROLE_ADMIN')">
                <c:set var="editAdmin" value="${tmpEditAdmin}" />
            </sec:authorize>
        </c:otherwise>
    </c:choose>
</sec:authorize>
<sec:authorize access="hasAuthority('ROLE_RESET_PASSWORD')">
    <%-- All admins and the user can change his/her password --%>
    <c:set var="resetPassword">
        <li>
            <button name="resetPasswordButton" class="menu-btn btn-danger" type="button"
                    data-user_dni="${admin.dni}"
                    data-user_first_name="${admin.firstName}"
                    data-user_last_name="${admin.lastName}"
                    data-toggle="modal" data-target="#resetPasswordFormConfirmationModal">
                <i class="fa fa-repeat" aria-hidden="true"></i> <spring:message code="resetPassword"/>
            </button>
        </li>
    </c:set>
</sec:authorize>
<c:set var="currentActionsHeader" scope="request">
    <c:out value="${admin.fullName}" />
</c:set>
<c:set var="currentActions" scope="request">
    ${viewAdmin}, ${editAdmin}, ${resetPassword}
</c:set>
<!-- /Student Action Panel definition -->