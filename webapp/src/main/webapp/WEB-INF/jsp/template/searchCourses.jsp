<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- search -->
<%--Course Filter Form--%>
<form:form id="course_filter_form" modelAttribute="courseFilterForm" action="${courseFilterFormAction}" method="get" enctype="application/x-www-form-urlencoded">

    <div class="row well">
        <div class="col-xs-12 col-md-2">
            <p class="lead"><spring:message code="searchBy"/>:</p>
        </div>
        <div class="col-xs-12 col-md-6">
            <div class="row">
                <div class="col-xs-12 col-md-5">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="input-group">
                                <span class="input-group-addon"><spring:message code="id"/></span>
                                <spring:message code="id" var="idPlaceholder"/>
                                <form:input path="id"  type="text" class="form-control" placeholder="${idPlaceholder}..."/>
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
                                <span class="input-group-addon"><spring:message code="name"/></span>
                                <spring:message code="name" var="namePlaceholder"/>
                                <form:input path="name" type="text" class="form-control" placeholder="${namePlaceholder}..."/>
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
                <spring:message code="search"/>
            </button>
        </div>
        <div class="col-xs-5 col-md-2 text-center">
            <button id="resetSearch" class="btn btn-default" type="submit">
                <span class="fa fa-repeat" aria-hidden="true"></span>
                <spring:message code="reset"/>
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
            <th><spring:message code="id"/></th>
            <th><spring:message code="name"/></th>
            <th><spring:message code="credits"/></th>
            <th><spring:message code="actions"/></th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty courses}">
            <tr class="bg-warning">
                <td colspan="4" class="text-danger text-center"><spring:message code="noCoursesFound"/></td>
            </tr>
        </c:if>
        <c:forEach items="${courses}" var="course">
            <tr>
                <td>${ course.id }</td>
                <td>${ course.name }</td>
                <td>${ course.credits }</td>
                <td>
                    <c:choose>
                        <c:when test="${section=='students'}">
                            <c:choose>
                                <c:when test="${subsection_enroll}">
                                    <button name="inscription" class="btn btn-info btn-xs" type="button"
                                            data-course_id="${ course.id }" data-course_name="${ course.name }"
                                            data-toggle="modal" data-target="#enrollFormConfirmationModal">
                                        <span class="fa fa-list-alt" aria-hidden="true"></span> <spring:message code="enroll"/>
                                    </button>
                                </c:when>
                                <c:when test="${subsection_courses}">
                                    <button name="gradeButton" class="btn btn-info btn-xs" type="button"
                                            data-course_id="${ course.id }" data-course_name="${ course.name }"
                                            data-toggle="modal" data-target="#gradeFormConfirmationModal">
                                        <span class="fa fa-graduation-cap" aria-hidden="true"></span> <spring:message code="rate"/>
                                    </button>
                                    <button name="unenroll" class="btn btn-danger btn-xs" type="button"
                                            data-course_id="${ course.id }" data-course_name="${ course.name }"
                                            data-toggle="modal" data-target="#enrollFormConfirmationModal">
                                        <span class="fa fa-trash" aria-hidden="true"></span> <spring:message code="unenroll"/>
                                    </button>
                                </c:when>
                            </c:choose>
                        </c:when>
                        <c:when test="${section=='courses'}">
                            <c:choose>
                                <c:when test="${subsection_get_courses}">
                                    <a class="btn btn-info btn-xs" href="<c:url value="/courses/${course.id}/edit"/>">
                                        <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="edit"/>
                                    </a>
                                    <button name="deleteCourseButton" class="btn btn-danger btn-xs" type="button"
                                            data-course_id="${ course.id }" data-course_name="${ course.name }"
                                            data-toggle="modal" data-target="#deleteCourseFormConfirmationModal">
                                        <span class="fa fa-trash" aria-hidden="true"></span> <spring:message code="delete"/>
                                    </button>
                                </c:when>
                                <c:when test="${subsection_add_correlative}">
                                    <button name="correlativeButton" class="btn btn-info btn-xs" type="button"
                                            data-course_id="${ course_details.id }" data-course_name="${ course_details.name }"
                                            data-correlative_id="${course.id}" data-correlative_name="${course.name}"
                                            data-toggle="modal" data-target="#correlativeFormConfirmationModal">
                                        <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="add_correlative"/>
                                    </button>
                                </c:when>
                            </c:choose>
                        </c:when>
                    </c:choose>
                    <a class="btn btn-default btn-xs" href="<c:url value="/courses/${course.id}/info" />" role="button">
                        <span class="fa fa-info-circle" aria-hidden="true"></span> <spring:message code="see"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- /content -->
