<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- search -->
<%--Student Filter Form--%>
<form:form id="student_filter_form" modelAttribute="studentFilterForm" action="${studentFilterFormAction}" method="get" enctype="application/x-www-form-urlencoded">

    <div class="row well">
        <div class="col-xs-12 col-md-2">
            <p class="lead"><spring:message code="searchBy"/>:</p>
        </div>
        <div class="col-xs-12 col-md-7">
            <div class="row">
                <div class="col-xs-12 col-md-4">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="input-group">
                                <span class="input-group-addon"><spring:message code="docket"/></span>
                                <spring:message code="docket" var="docketPlaceholder"/>
                                <form:input path="docket"  type="text" class="form-control" placeholder="${docketPlaceholder}..."/>
                            </div>
                        </div>
                        <div class="col-xs-12">
                            <form:errors path="docket" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12 col-md-4">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="input-group">
                                <span class="input-group-addon"><spring:message code="firstName"/></span>
                                <spring:message code="firstName" var="firstNamePlaceholder"/>
                                <form:input path="firstName" type="text" class="form-control" placeholder="${firstNamePlaceholder}..."/>
                            </div>
                        </div>
                        <div class="col-xs-12">
                            <form:errors path="firstName" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12 col-md-4">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="input-group">
                                <span class="input-group-addon"><spring:message code="lastName"/></span>
                                <spring:message code="lastName" var="lastNamePlaceholder"/>
                                <form:input path="lastName" type="text" class="form-control" placeholder="${lastNamePlaceholder}..."/>
                            </div>
                        </div>
                        <div class="col-xs-12">
                            <form:errors path="lastName" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="col-xs-12 col-md-3">
            <div class="row">
                <div class="col-xs-1 hidden-md hidden-lg"></div>
                <div class="col-xs-5 col-md-6 text-center">
                    <button id="search" class="btn btn-default" type="submit">
                        <span class="fa fa-search" aria-hidden="true"></span>
                        <spring:message code="search"/>
                    </button>
                </div>
                <div class="col-xs-5 col-md-6 text-center">
                    <button id="resetSearch" class="btn btn-default" type="submit">
                        <span class="fa fa-repeat" aria-hidden="true"></span>
                        <spring:message code="reset"/>
                    </button>
                </div>
            </div>
        </div>
    </div>
</form:form>
<%--/Student Filter Form--%>

<!-- content -->
<div class="table-responsive">
    <table class="table table-hover <%--table-bordered--%> <%--table-condensed--%>">
        <thead>
        <tr>
            <th><spring:message code="docket"/></th>
            <th><spring:message code="firstName"/></th>
            <th><spring:message code="lastName"/></th>
            <%--<th><spring:message code="email"/></th>--%>
            <th><spring:message code="actions"/></th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty students}">
            <tr class="bg-warning">
                <td colspan="4" class="text-danger text-center"><spring:message code="noStudentsFound"/></td>
            </tr>
        </c:if>
        <c:forEach items="${students}" var="student">
            <tr>
                <td>${ student.docket }</td>
                <td>${ student.firstName }</td>
                <td>${ student.lastName }</td>
                <td>
                    <c:choose>
                        <c:when test="${subsection_students}">
                            <a class="btn btn-info tableButton" href="/students/${student.docket}/edit" role="button">
                                <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="edit"/>
                            </a>
                            <button name="deleteStudentButton" class="btn btn-danger btn-xs" type="button"
                                    data-student_docket="${student.docket}"
                                    data-student_first_name="${student.firstName}"
                                    data-student_last_name="${student.lastName}"
                                    data-toggle="modal" data-target="#deleteStudentFormConfirmationModal">
                                <span class="fa fa-trash" aria-hidden="true"></span> <spring:message code="delete"/>
                            </button>
                        </c:when>
                        <c:when test="${subsection_courses}">
                            <button name="gradeButton" class="btn btn-info btn-xs" type="button"
                                    data-student_docket="${student.docket}"
                                    data-toggle="modal" data-target="#gradeFormConfirmationModal">
                                <span class="fa fa-graduation-cap" aria-hidden="true"></span> <spring:message code="rate"/>
                            </button>
                        </c:when>
                    </c:choose>
                    <a class="btn btn-default tableButton" href="<c:url value="/students/${student.docket}/info" />" role="button">
                        <span class="fa fa-info-circle" aria-hidden="true"></span> <spring:message code="see"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- /content -->