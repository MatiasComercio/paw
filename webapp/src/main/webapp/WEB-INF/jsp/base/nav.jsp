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
    <c:if test="${student != null}">
        <ul class="nav navbar-right top-nav">
            <li class="dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> ${student.fullName}<strong class="caret"></strong></a>
                <ul class="dropdown-menu" role="menu">
                    <li>
                        <a href="/students/${student.docket}/info"><i class="fa fa-fw fa-user"></i> <spring:message code="profile"/></a>
                    </li>
                    <li>
                        <a href="/students/${student.docket}/courses"><i class="fa fa-fw fa-university"></i> <spring:message code="courses"/></a>
                    </li>
                    <li>
                        <a href="/students/${student.docket}/grades"><i class="fa fa-fw fa-graduation-cap"></i> <spring:message code="grades"/></a>
                    </li>
                    <li>
                        <a href="/students/${student.docket}/inscription"><i class="fa fa-fw fa-list-alt"></i> <spring:message code="inscriptions"/></a>
                    </li>
                        <%--                <li>
                                            <a href="#"><i class="fa fa-fw fa-envelope"></i> Inbox</a>
                                        </li>
                                        <li>
                                            <a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
                                        </li>
                                        <li class="divider"></li>
                                        <li>
                                            <a href="#"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                                        </li>--%>
                </ul>
            </li>
        </ul>
    </c:if>

    <!-- navbar-collapse -->
    <%--@elvariable id="section" type="ar.edu.itba.paw.webapp.controllers"--%>
    <div class="collapse navbar-collapse navbar-ex1-collapse">
        <ul class="nav navbar-nav side-nav">
        <c:choose>
            <c:when test="${section=='students'}">
            <li class="active">
                </c:when>
            <c:otherwise><li></c:otherwise>
            </c:choose>
            <a href="<c:url value="/" />"><i class="fa fa-fw fa-dashboard"></i> <spring:message code="students"/></a>
        </li>

            <c:choose>
            <c:when test="${section=='courses'}">
            <li class="active">
                </c:when>
            <c:otherwise><li></c:otherwise>
            </c:choose>
            <a href="<c:url value="/courses" />"><i class="fa fa-fw fa-edit"></i> <spring:message code="courses"/></a>
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