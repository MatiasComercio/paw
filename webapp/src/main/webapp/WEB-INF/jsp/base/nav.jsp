<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%--See: http://docs.spring.io/spring-security/site/docs/4.1.0.RELEASE/reference/htmlsingle/#taglibs-authorize--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%-- Spring Security: Choose variables --%>
<%--@elvariable id="section" type="ar.edu.itba.paw.webapp.controllers"--%>
<c:choose>
    <c:when test="${section=='admins'}">
        <c:set var="adminsActive" value="active" />
    </c:when>
    <c:when test="${section=='students'}">
        <c:set var="studentsActive" value="active" />
    </c:when>
    <c:when test="${section=='courses'}">
        <c:set var="coursesActive" value="active" />
    </c:when>
    <%--<c:when test="${section=='info'}">
        <c:set var="infoActive" value="active" />
    </c:when>--%>
</c:choose>
<c:set var="actionMenuList">
    <sec:authorize url="/admins">
        <li class="${adminsActive}">
            <a href="<c:url value="/admins" />" class="pushy-link"><i class="fa fa-lock" aria-hidden="true"></i> <spring:message code="admins"/></a>
        </li>
    </sec:authorize>
    <li class="${studentsActive}">
        <a href="<c:url value="/students" />" class="pushy-link"><i class="fa fa-fw fa-users"></i> <spring:message code="students"/></a>
    </li>
    <li class="${coursesActive}">
        <a href="<c:url value="/courses" />" class="pushy-link"><i class="fa fa-university" aria-hidden="true"></i> <spring:message code="courses"/></a>
    </li>
    <%--Both variables MUST be defined as:
    <c:set var="<varName>" scope="request"> -> scope="request is very important. --%>
    <li class="navbar_own_divider"></li>
    <li>
            <%--@elvariable id="currentActionsHeader" type="java.lang.String"--%>
        <p class="current_actions_header">${currentActionsHeader}</p>
    </li>
    <%--@elvariable id="currentActions" type="java.util.List"--%>
    <c:forEach items="${currentActions}" var="action">
        ${action}
    </c:forEach>
</c:set>

<c:set var="profileUrl" value="/students/${user.id}/info" />
<sec:authorize url="/admins">
    <c:set var="profileUrl" value="/admins/${user.id}/info" />
</sec:authorize>
<sec:authorize access="hasAuthority('ROLE_STUDENT')">
    <c:set var="studentMenu">
        <li>
            <a href="<c:url value="/students/${user.id}/courses" />"><i class="fa fa-fw fa-university"></i> <spring:message code="courses"/></a>
        </li>
        <li>
            <a href="<c:url value="/students/${user.id}/grades" />"><i class="fa fa-fw fa-graduation-cap"></i> <spring:message code="grades"/></a>
        </li>
        <li>
            <a href="<c:url value="/students/${user.id}/inscription" />"><i class="fa fa-fw fa-list-alt"></i> <spring:message code="inscriptions"/></a>
        </li>
    </c:set>
</sec:authorize>


<!-- Pushy Menu: Only accessible on xs resolution -->
<%-- Speciall thanks to Chris Yee. Twitter user: @cmyee.--%>
<%-- See Github repo: https://github.com/christophery/pushy --%>
<nav class="pushy pushy-left visible-xs-block">
    <ul class="nav side-nav">
        ${actionMenuList}
    </ul>
</nav>

<!-- Site Overlay -->
<div class="site-overlay visible-xs-block"></div>

<!-- Navigation -->
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">

    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <!-- Menu Button -->
        <button type="button" class="navbar-toggle menu-btn pull-left">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="<c:url value="/" />">
            <strong><spring:message code="webAbbreviation"/></strong>
            <span class="hidden-xs">
                - <spring:message code="webName"/>
            </span>
        </a>
    </div>

    <!-- User's Menu -->
    <%--@elvariable id="student" type="ar.edu.itba.paw.models.users.Student"--%>
    <%--@elvariable id="user" type="ar.edu.itba.paw.webapp.auth.UserSessionDetails"--%>
    <c:if test="${user != null}">
        <ul class="nav navbar-right pull-right top-nav">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-user"></i>
                    <span class="hidden-xs">${user.fullName}</span>
                    <strong class="caret"></strong>
                </a>
                <ul class="dropdown-menu dropdown-menu-right" role="menu">
                    <li>
                        <a href="<c:url value="${profileUrl}" />"><i class="fa fa-fw fa-user"></i> <spring:message code="profile"/></a>
                    </li>
                        ${studentMenu}
                    <li class="divider"></li>
                    <li>
                        <a href="<c:url value="/user/changePassword" />"><i class="fa fa-fw fa-gear"></i> <spring:message code="changePassword"/></a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="<c:url value="/logout" />"><i class="fa fa-fw fa-power-off"></i> <spring:message code="logout" /> </a>
                    </li>
                </ul>
            </li>
        </ul>
    </c:if>

    <!-- navbar-collapse -->
    <div class="collapse navbar-collapse navbar-ex1-collapse">
        <ul class="nav navbar-nav side-nav">
            ${actionMenuList}
        </ul>
    </div>
    <!-- /.navbar-collapse -->
</nav>