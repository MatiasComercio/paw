<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<!-- Navigation -->
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="<c:url value="/" />"><strong><spring:message code="webAbbreviation"/></strong> - <spring:message code="webName"/></a>
    </div>
    <!-- Top Menu Items -->
    <%--@elvariable id="student" type="ar.edu.itba.paw.models.users.Student"--%>
    <%--@elvariable id="user" type="ar.edu.itba.paw.webapp.auth.UserSessionDetails"--%>
    <c:if test="${user != null}">
        <ul class="nav navbar-right top-nav">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> ${user.fullName}<strong class="caret"></strong></a>
                <ul class="dropdown-menu" role="menu">
                    <%--+++xtodo: decide this --%>
                    <%--<li>--%>
                        <%--<a href="<c:url value="${userInfo}" />"><i class="fa fa-fw fa-user"></i> <spring:message code="profile"/></a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a href="<c:url value="${userCourses}" />"><i class="fa fa-fw fa-university"></i> <spring:message code="courses"/></a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a href="<c:url value="${userGrades}" />"><i class="fa fa-fw fa-graduation-cap"></i> <spring:message code="grades"/></a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a href="<c:url value="${userInscription}" />"><i class="fa fa-fw fa-list-alt"></i> <spring:message code="inscriptions"/></a>--%>
                    <%--</li>--%>
                    <%--<li class="divider"></li>--%>
                        <%--<li>
                            <a href="#"><i class="fa fa-fw fa-envelope"></i> Inbox</a>
                        </li>--%>
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
    <%--@elvariable id="section" type="ar.edu.itba.paw.webapp.controllers"--%>
    <div class="collapse navbar-collapse navbar-ex1-collapse">
        <ul class="nav navbar-nav side-nav">
            <c:if test="${user.hasAuthority('VIEW_ADMIN')}">
                <c:choose>
                    <c:when test="${section=='admins'}">
                        <li class="active" >
                    </c:when>
                    <c:otherwise>
                        <li>
                    </c:otherwise>
                </c:choose>
                <a href="<c:url value="/admins" />"><i class="fa fa-lock" aria-hidden="true"></i> <spring:message code="admins"/></a>
                </li>
            </c:if>


            <c:choose>
            <c:when test="${section=='students'}">
            <li class="active" >
                </c:when>
                <c:otherwise>
            <li>
                </c:otherwise>
                </c:choose>
                <a href="<c:url value="/students" />"><i class="fa fa-fw fa-users"></i> <spring:message code="students"/></a>
            </li>

            <c:choose>
            <c:when test="${section=='courses'}">
            <li class="active">
                </c:when>
                <c:otherwise>
            <li>
                </c:otherwise>
                </c:choose>
                <a href="<c:url value="/courses" />"><i class="fa fa-graduation-cap" aria-hidden="true"></i> <spring:message code="courses"/></a>
            </li>


            <!--<li>
                <a href="#"><i class="fa fa-fw fa-bar-chart-o"></i> Buscar Alumno</a>
            </li>
            <li>
                <a href="#" />"><i class="fa fa-fw fa-desktop"></i> Buscar Materia</a>
            </li>
            <li>
            <a href="#"><i class="fa fa-fw fa-wrench"></i> Settings</a>
            </li>
             <li>
                <a href="tables.html"><i class="fa fa-fw fa-table"></i> Tables</a>
            </li>
            <li>
                <a href="javascript:;" data-toggle="collapse" data-target="#demo"><i class="fa fa-fw fa-arrows-v"></i> Dropdown <i class="fa fa-fw fa-caret-down"></i></a>
                <ul id="demo" class="collapse">
                    <li>
                        <a href="#">Dropdown Item</a>
                    </li>
                    <li>
                        <a href="#">Dropdown Item</a>
                    </li>
                </ul>
            </li>
            <li>
                <a href="blank-page.html"><i class="fa fa-fw fa-file"></i> Blank Page</a>
            </li>
            <li>
                <a href="index-rtl.html"><i class="fa fa-fw fa-dashboard"></i> RTL Dashboard</a>
            </li>-->
        </ul>
    </div>
    <!-- /.navbar-collapse -->
</nav>