<%@ include file="base/tags.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> |
        <c:choose>
            <c:when test="${section2 eq 'addCourse' }">
                <spring:message code="addCourse"/>
            </c:when>
            <c:when test="${section2 eq 'edit' }">
                ${course.name} | <spring:message code="edit"/>
            </c:when>
        </c:choose>
    </title>
    <jsp:include page="base/head.jsp" />
</head>
<body>

<div id="wrapper">

    <jsp:include page="base/sections.jsp" />
    <jsp:include page="base/nav.jsp" />

    <%-- Task decision --%>
    <%--@elvariable id="section2" type="java.lang.String"--%>
    <%--@elvariable id="course" type="ar.edu.itba.paw.models.Course"--%>
    <c:choose>
        <c:when test="${section2 eq 'addCourse'}" >
            <c:set var="title">
                <spring:message code="courses"/> <small> - <spring:message code="addCourse"/></small>
            </c:set>
            <c:set var="formAction">
                <c:url value="/courses/add_course" />
            </c:set>
            <c:set var="hidden" value="hidden" />
            <c:set var="readonly" value="false" />
            <spring:message var="formButton" code="add"/>
        </c:when>
        <c:when test="${section2 eq 'edit'}" >
            <c:set var="title">
                ${course.name} <small> - <spring:message code="edit"/></small>
            </c:set>
            <c:set var="formAction">
                <c:url value="/courses/${course.id}/edit" />
            </c:set>
            <c:set var="hidden" value="" />
            <c:set var="readonly" value="true" />
            <spring:message var="formButton" code="saveChanges"/>
        </c:when>
    </c:choose>

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                        ${title}
                    </h1>
                </div>
            </div>
            <!-- /Page Heading -->

            <!-- Alerts -->
            <jsp:include page="base/alerts.jsp" />
            <!-- /Alerts -->

            <!-- Content -->
            <div class="row">
                <form:form modelAttribute="courseForm" method="post" action="${formAction}">
                    <div class="col-xs-12 requiredFields">
                        <spring:message code="requiredFields"/>
                        (<span class="text-danger"><spring:message code="requiredIcon"/></span>)
                    </div>

                    <div class="form-group col-xs-12 ${hidden}">
                        <form:label path="id"><spring:message code="id"/>
                            (<span class="text-danger"><spring:message code="requiredIcon"/></span>)</form:label>
                        <form:input path="id" type="text" class="form-control" readonly="${readonly}"/>
                        <form:errors path="id" cssClass="text-danger bg-danger" element="div"/>
                    </div>
                    <div class="form-group col-xs-12">
                        <form:label path="courseId"><spring:message code="id"/>
                            (<span class="text-danger"><spring:message code="requiredIcon"/></span>)</form:label>
                        <form:input type="text" class="form-control" path="courseId"/>
                        <form:errors path="courseId" cssClass="text-danger bg-danger" element="div"/>
                    </div>
                    <div class="form-group col-xs-12">
                        <form:label path="name"><spring:message code="name"/>
                            (<span class="text-danger"><spring:message code="requiredIcon"/></span>)</form:label>
                        <form:input type="text" class="form-control" path="name"/>
                        <form:errors path="name" cssClass="text-danger bg-danger" element="div"/>
                    </div>
                    <div class="form-group col-xs-12">
                        <form:label path="credits"><spring:message code="credits"/>
                            (<span class="text-danger"><spring:message code="requiredIcon"/></span>)</form:label>
                        <form:input type="text" class="form-control" path="credits"/>
                        <form:errors path="credits" cssClass="text-danger bg-danger" element="div"/>
                    </div>
                    <div class="form-group col-xs-12">
                        <form:label path="semester"><spring:message code="semester"/>
                            (<span class="text-danger"><spring:message code="requiredIcon"/></span>)</form:label>
                        <form:input type="text" class="form-control" path="semester"/>
                        <form:errors path="semester" cssClass="text-danger bg-danger" element="div"/>
                    </div>
                    <div class="col-sm-2 hidden-xs"></div>
                    <div class="col-xs-6 col-sm-4 text-center">
                        <button type="submit" class="btn btn-info center-block fullWidthButton">
                                ${formButton}
                        </button>
                    </div>
                    <div class="col-xs-6 col-sm-4 text-center">
                        <button id="cancelButton" class="btn btn-default center-block fullWidthButton" role="button">
                            <spring:message code="cancel"/>
                        </button>
                    </div>
                </form:form>
            </div>
            <!-- /Content -->

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->
    <jsp:include page="base/footer.jsp" />
</div>
<!-- Scripts -->
<jsp:include page="base/scripts.jsp" />
</body>
</html>