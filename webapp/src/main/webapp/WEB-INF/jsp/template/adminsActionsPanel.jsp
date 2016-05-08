<%@ include file="../base/tags.jsp" %>

<!-- Admins Action Panel definition -->
<%--@elvariable id="addAdminActive" type="java.lang.String"--%>
<%--@elvariable id="deleteUserActive" type="java.lang.String"--%>

<sec:authorize access="hasAuthority('ROLE_ADD_ADMIN')">
    <c:set var="addAdmin">
        <li class="${addAdminActive}">
            <a href="<c:url value="/admins/add_admin"/>" class="pushy-link">
                <i class="fa fa-plus-circle" aria-hidden="true"></i> <spring:message code="addAdmin"/>
            </a>
        </li>
    </c:set>
</sec:authorize>
<%-- +++xfix: with authority reload --%>
<%--<sec:authorize access="hasAuthority('ROLE_ENABLE_INSCRIPTION')">
    <c:set var="enableInscription">
        <li>
            <button name="enableInscriptionsButton" class="menu-btn" type="button"
                    data-toggle="modal" data-target="#enableInscriptionsConfirmationModal">
                <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="enable_inscriptions"/>
            </button>
        </li>
    </c:set>
</sec:authorize>
<sec:authorize access="hasAuthority('ROLE_DISABLE_INSCRIPTION')">
    <c:set var="disableInscription">
        <li>
            <button name="disableInscriptionsButton" class="menu-btn" type="button"
                    data-toggle="modal" data-target="#enableInscriptionsConfirmationModal">
                <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="disable_inscriptions"/>
            </button>
        </li>
    </c:set>
</sec:authorize>--%>
<c:choose>
    <c:when test="${isInscriptionEnabled}">
        <c:set var="disableInscription">
            <li>
                <button name="disableInscriptionsButton" class="menu-btn" type="button"
                        data-toggle="modal" data-target="#enableInscriptionsConfirmationModal">
                    <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="disable_inscriptions"/>
                </button>
            </li>
        </c:set>
    </c:when>
    <c:when test="${!isInscriptionEnabled}">
        <c:set var="enableInscription">
            <li>
                <button name="enableInscriptionsButton" class="menu-btn" type="button"
                        data-toggle="modal" data-target="#enableInscriptionsConfirmationModal">
                    <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="enable_inscriptions"/>
                </button>
            </li>
        </c:set>
    </c:when>
</c:choose>
<%--+++xtodo--%>
<%--<sec:authorize access="hasAuthority('ROLE_DELETE_USER')">
    <c:set var="deleteUser">
        <li class="${deleteUserActive}">
            <a href="<c:url value="/admins/deleteUser"/>" class="pushy-link">
                <i class="fa fa-plus-circle" aria-hidden="true"></i> <spring:message code="addStudent"/>
            </a>
        </li>
    </c:set>
</sec:authorize>--%>
<c:set var="currentActionsHeader" scope="request">
    <spring:message code="admins" />
</c:set>
<c:set var="currentActions" scope="request">
    ${addAdmin}, ${enableInscription}, ${disableInscription}<%--${editCourse}, ${viewStudents}, ${addCorrelative}, ${deleteCourse}--%>
</c:set>
<!-- /Admins Action Panel definition -->