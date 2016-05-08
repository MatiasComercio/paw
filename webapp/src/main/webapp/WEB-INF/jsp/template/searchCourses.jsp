<%@ include file="../base/tags.jsp" %>

<!-- search -->
<%--Course Filter Form--%>
<c:url var="courseFilterFormAction" value="${courseFilterFormAction}"/>
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
            <th><spring:message code="semester"/></th>
            <th><spring:message code="actions"/></th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty courses}">
            <tr class="bg-warning">
                <td colspan="4" class="text-danger text-center"><spring:message code="noCoursesFound"/></td>
            </tr>
        </c:if>
        <c:forEach items="${courses}" var="eachCourse">
            <tr>
                <td>${ eachCourse.id }</td>
                <td>${ eachCourse.name }</td>
                <td>${ eachCourse.credits }</td>
                <td>${ eachCourse.semester }</td>
                <td>
                    <c:choose>
                        <c:when test="${section eq 'students'}">
                            <c:choose>
                                <c:when test="${section2 eq 'inscription'}">
                                    <sec:authorize access="hasAuthority('ROLE_ADD_INSCRIPTION')">
                                        <button name="inscription" class="btn btn-info" type="button"
                                                data-course_id="${ eachCourse.id }" data-course_name="${ eachCourse.name }"
                                                data-toggle="modal" data-target="#enrollFormConfirmationModal">
                                            <span class="fa fa-list-alt" aria-hidden="true"></span> <spring:message code="enroll"/>
                                        </button>
                                        <a class="btn btn-default" href="<c:url value="/courses/${eachCourse.id}/info" />" role="button">
                                            <span class="fa fa-info-circle" aria-hidden="true"></span> <spring:message code="see"/>
                                        </a>
                                    </sec:authorize>
                                </c:when>
                                <c:when test="${section2 eq 'courses'}">
                                        <a class="btn btn-default" href="<c:url value="/courses/${eachCourse.id}/info" />" role="button">
                                            <span class="fa fa-info-circle" aria-hidden="true"></span> <spring:message code="see"/>
                                        </a>
                                        <sec:authorize access="hasAuthority('ROLE_ADD_GRADE')">
                                            <button name="gradeButton" class="btn btn-info" type="button"
                                                    data-course_id="${ eachCourse.id }" data-course_name="${ eachCourse.name }"
                                                    data-toggle="modal" data-target="#gradeFormConfirmationModal">
                                                <span class="fa fa-graduation-cap" aria-hidden="true"></span> <spring:message code="rate"/>
                                            </button>
                                        </sec:authorize>
                                        <sec:authorize access="hasAuthority('ROLE_DELETE_INSCRIPTION')">
                                            <c:if test="${user.id eq student.docket}">
                                                <button name="unenroll" class="btn btn-danger" type="button"
                                                        data-course_id="${ eachCourse.id }" data-course_name="${ eachCourse.name }"
                                                        data-toggle="modal" data-target="#enrollFormConfirmationModal">
                                                    <span class="fa fa-trash" aria-hidden="true"></span> <spring:message code="unenroll"/>
                                                </button>
                                            </c:if>
                                        </sec:authorize>
                                </c:when>
                                <c:otherwise>
                                    <a class="btn btn-default" href="<c:url value="/courses/${eachCourse.id}/info" />" role="button">
                                        <span class="fa fa-info-circle" aria-hidden="true"></span> <spring:message code="see"/>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:when test="${section eq 'courses'}">
                            <c:choose>
                                <c:when test="${section2 eq 'addCorrelative'}">
                                    <sec:authorize access="hasAuthority('ROLE_ADD_CORRELATIVE')">
                                        <button name="correlativeButton" class="btn btn-info" type="button"
                                                data-course_id="${ course.id }" data-course_name="${ course.name }"
                                                data-correlative_id="${eachCourse.id}" data-correlative_name="${eachCourse.name}"
                                                data-toggle="modal" data-target="#correlativeFormConfirmationModal">
                                            <i class="fa fa-pencil-square-o" aria-hidden="true"></i> <spring:message code="add_correlative"/>
                                        </button>
                                    </sec:authorize>
                                    <a class="btn btn-default" href="<c:url value="/courses/${eachCourse.id}/info" />" role="button">
                                        <span class="fa fa-info-circle" aria-hidden="true"></span> <spring:message code="see"/>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a class="btn btn-default tableButton" href="<c:url value="/courses/${eachCourse.id}/info" />" role="button">
                                        <span class="fa fa-info-circle" aria-hidden="true"></span> <spring:message code="see"/>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- /content -->
