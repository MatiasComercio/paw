<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- search -->
<%--Student Filter Form--%>
<form:form id="admin_filter_form" modelAttribute="adminsFilterForm" action="${adminFilterFormAction}" method="get" enctype="application/x-www-form-urlencoded">

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
                                <span class="input-group-addon"><spring:message code="dni"/></span>
                                <spring:message code="dni" var="dniPlaceholder"/>
                                <form:input path="dni"  type="text" class="form-control" placeholder="${dniPlaceholder}..."/>
                            </div>
                        </div>
                        <div class="col-xs-12">
                            <form:errors path="dni" cssClass="text-danger bg-danger" element="div"/>
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
            <th><spring:message code="dni"/></th>
            <th><spring:message code="firstName"/></th>
            <th><spring:message code="lastName"/></th>
            <%--<th><spring:message code="email"/></th>--%>
            <th><spring:message code="actions"/></th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty admins}">
            <tr class="bg-warning">
                <td colspan="4" class="text-danger text-center"><spring:message code="noStudentsFound"/></td>
            </tr>
        </c:if>
        <c:forEach items="${admins}" var="admin">
            <tr>
                <td>${ admin.dni }</td>
                <td>${ admin.firstName }</td>
                <td>${ admin.lastName }</td>
                <td>
                    <a class="btn btn-default btn-xs" href="<c:url value="/admins/${admin.dni}/info" />" role="button">
                        <span class="fa fa-info-circle" aria-hidden="true"></span> <spring:message code="see"/>
                    </a>
                    <a class="btn btn-info btn-xs" href="/admins/${admin.dni}/edit" role="button">
                        <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="edit"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- /content -->