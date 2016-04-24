<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!-- search -->
<%--Course Filter Form--%>
<form:form id="course_filter_form" modelAttribute="courseFilterForm" action="${courseFilterFormAction}" method="get" enctype="application/x-www-form-urlencoded">

    <div class="row well">
        <div class="col-xs-12 col-md-2">
            <p class="lead">Buscar por:</p>
        </div>
        <div class="col-xs-12 col-md-6">
            <div class="row">
                <div class="col-xs-12 col-md-5">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="input-group">
                                <span class="input-group-addon">Id</span>
                                <form:input path="id"  type="text" class="form-control" placeholder="Id..."/>
                            </div>
                        </div>
                        <div class="col-xs-12">
                            <form:errors path="id" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12 col-md-7">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="input-group">
                                <span class="input-group-addon">Nombre</span>
                                <form:input path="name" type="text" class="form-control" placeholder="Nombre..."/>
                            </div>
                        </div>
                        <div class="col-xs-12">
                            <form:errors path="name" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-xs-1 hidden-md hidden-lg"></div>
        <div class="col-xs-5 col-md-2 text-center">
            <button id="search" class="btn btn-default" type="submit">
                <span class="fa fa-search" aria-hidden="true"></span>
                Buscar
            </button>
        </div>
        <div class="col-xs-5 col-md-2 text-center">
            <button id="resetSearch" class="btn btn-default" type="submit">
                <span class="fa fa-repeat" aria-hidden="true"></span>
                Resetear
            </button>
        </div>
    </div>
</form:form>
<%--/Course Filter Form--%>

<!-- content -->
<div class="table-responsive">
    <table class="table table-hover <%--table-bordered--%> <%--table-condensed--%>">
        <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Créditos</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty courses}">
            <tr class="bg-warning">
                <td colspan="4" class="text-danger text-center"> No se encontraron materias disponibles. </td>
            </tr>
        </c:if>
        <c:forEach items="${courses}" var="course">
            <tr>
                <td>${ course.id }</td>
                <td>${ course.name }</td>
                <td>${ course.credits }</td>
                <td>
                    <c:choose>
                        <c:when test="${subsection_enroll}">
                            <button name="inscription" class="btn btn-info btn-xs" type="button"
                                    data-course_id="${ course.id }" data-course_name="${ course.name }"
                                    data-toggle="modal" data-target="#enrollFormConfirmationModal">
                                <span class="fa fa-list-alt" aria-hidden="true"></span> Inscribir
                            </button>
                        </c:when>
                        <c:when test="${subsection_courses}">
                            <button name="gradeButton" class="btn btn-info btn-xs" type="button"
                                    data-course_id="${ course.id }" data-course_name="${ course.name }"
                                    data-toggle="modal" data-target="#gradeFormConfirmationModal">
                                <span class="fa fa-graduation-cap" aria-hidden="true"></span> Calificar
                            </button>
                            <button name="unenroll" class="btn btn-danger btn-xs" type="button"
                                    data-course_id="${ course.id }" data-course_name="${ course.name }"
                                    data-toggle="modal" data-target="#enrollFormConfirmationModal">
                                <span class="fa fa-trash" aria-hidden="true"></span> Dar de Baja
                            </button>
                        </c:when>
                    </c:choose>
                    <a class="btn btn-default btn-xs" href="<c:url value="/courses/${course.id}/info" />" role="button">
                        <span class="fa fa-info-circle" aria-hidden="true"></span> Ver
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- /content -->
