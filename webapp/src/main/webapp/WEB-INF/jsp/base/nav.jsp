<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <a class="navbar-brand" href="#"><strong>SGA</strong> Sistema de gestión académica</a>
    </div>
    <!-- Top Menu Items -->
    <ul class="nav navbar-right top-nav">
        <!--<li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> Username <b class="caret"></b></a>
            <ul class="dropdown-menu">
                <li>
                    <a href="#"><i class="fa fa-fw fa-user"></i> Profile</a>
                </li>
                <li>
                    <a href="#"><i class="fa fa-fw fa-envelope"></i> Inbox</a>
                </li>
                <li>
                    <a href="#"><i class="fa fa-fw fa-gear"></i> Settings</a>
                </li>
                <li class="divider"></li>
                <li>
                    <a href="#"><i class="fa fa-fw fa-power-off"></i> Log Out</a>
                </li>
            </ul>
        </li>-->
    </ul>

    <!-- navbar-collapse -->
    <div class="collapse navbar-collapse navbar-ex1-collapse">
        <ul class="nav navbar-nav side-nav">
            <c:choose>
            <c:when test="${section=='index'}">
            <li class="active">
            </c:when>
                <c:otherwise><li></c:otherwise>
            </c:choose>
                <a href="<c:url value="/app/" />"><i class="fa fa-fw fa-dashboard"></i> Alumnos</a>
            </li>

            <c:choose>
                <c:when test="${section=='courses'}">
            <li class="active">
                </c:when>
                <c:otherwise><li></c:otherwise>
            </c:choose>
                <a href="<c:url value="/app/courses/" />"><i class="fa fa-fw fa-edit"></i> Materias</a>
            </li>
            <li>
                <a href="#"><i class="fa fa-fw fa-bar-chart-o"></i> Buscar Alumno</a>
            </li>
            <c:choose>
            <c:when test="${section=='search_courses'}">
            <li class="active">
                </c:when>
            <c:otherwise><li></c:otherwise>
            </c:choose>
                <a href="<c:url value="/app/courses" />"><i class="fa fa-fw fa-desktop"></i> Buscar Materia</a>
            </li>
            <!--<li>
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