<%@include file="../base/tags.jsp"%>

<!-- search -->
<%--Student Filter Form--%>
<%--@elvariable id="studentFilterFormAction" type="java.lang.String"--%>
<c:url var="studentFilterFormAction" value="${studentFilterFormAction}" />
<form:form id="student_filter_form" modelAttribute="studentFilterForm" action="${studentFilterFormAction}" method="get" enctype="application/x-www-form-urlencoded">

    <div class="row well">
        <div class="col-xs-12 col-md-2 text-center">
            <p class="lead"><spring:message code="searchBy"/>:</p>
        </div>
        <div class="col-xs-12 col-md-7">
            <div class="row">
                <div class="col-xs-12">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="input-group">
                                <span class="input-group-addon search-label"><spring:message code="docket"/></span>
                                <spring:message code="docket" var="docketPlaceholder"/>
                                <form:input path="docket"  type="text" class="form-control" placeholder="${docketPlaceholder}..."/>
                            </div>
                        </div>
                        <div class="col-xs-12">
                            <form:errors path="docket" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="input-group">
                                <span class="input-group-addon search-label"><spring:message code="firstName"/></span>
                                <spring:message code="firstName" var="firstNamePlaceholder"/>
                                <form:input path="firstName" type="text" class="form-control" placeholder="${firstNamePlaceholder}..."/>
                            </div>
                        </div>
                        <div class="col-xs-12">
                            <form:errors path="firstName" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                </div>
                <div class="col-xs-12">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="input-group">
                                <span class="input-group-addon search-label"><spring:message code="lastName"/></span>
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
                <div class="col-xs-5 col-md-12 col-lg-6 text-center">
                    <button id="search" class="btn btn-default" type="submit">
                        <span class="fa fa-search" aria-hidden="true"></span>
                        <spring:message code="search"/>
                    </button>
                </div>
                <div class="col-xs-5 col-md-12 col-lg-6 text-center">
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
        <%--@elvariable id="students" type="ar.edu.itba.paw.models.users.Student"--%>
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
                        <%--@elvariable id="section" type="java.lang.String"--%>
                        <%--@elvariable id="section2" type="java.lang.String"--%>
                        <c:when test="${section eq 'courses' && section2 eq 'students'}">
                            <c:url var="gradeFormAction" value="/students/${student.docket}/grades/add" />
                            <sec:authorize access="hasAuthority('ROLE_ADD_GRADE')"><%--@elvariable id="course" type="ar.edu.itba.paw.models.Course"--%>
                                <c:set var="gradeButtonDef">
                                    <button name="gradeButton" class="btn btn-info" type="button"
                                            data-url="${gradeFormAction}"
                                            data-docket="${student.docket}"
                                            data-course_id="${ course.id }" data-course_name="${ course.name }"
                                            data-toggle="modal" data-target="#gradeFormConfirmationModal">
                                        <span class="fa fa-graduation-cap" aria-hidden="true"></span> <spring:message code="rate"/>
                                    </button>
                                </c:set>
                                <c:set var="buttonClass" value="" />
                            </sec:authorize>
                        </c:when>
                        <c:otherwise>
                            <c:set var="buttonClass" value="fullWidthButton" />
                        </c:otherwise>
                    </c:choose>
                    <a class="btn btn-default ${buttonClass}" href="<c:url value="/students/${student.docket}/info" />" role="button">
                        <span class="fa fa-user" aria-hidden="true"></span> <spring:message code="profile"/>
                    </a>
                    ${gradeButtonDef}
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- /content -->