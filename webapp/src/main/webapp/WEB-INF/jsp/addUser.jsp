<%@ include file="base/tags.jsp"%>

<%--@elvariable id="section" type="java.lang.String"--%>
<%--@elvariable id="section2" type="java.lang.String"--%>
<%--@elvariable id="admin" type="ar.edu.itba.paw.models.Admin"--%>
<%--@elvariable id="student" type="ar.edu.itba.paw.models.Student"--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> |
        <c:choose>
            <c:when test="${section eq 'admins'}">
                <c:choose>
                    <c:when test="${section2 eq 'addAdmin' }">
                        <spring:message code="addAdmin"/>
                    </c:when>
                    <c:when test="${section2 eq 'edit' }">
                        ${admin.fullName} | <spring:message code="edit"/>
                    </c:when>
                </c:choose>
            </c:when>
            <c:when test="${section eq 'students'}">
                <c:choose>
                    <c:when test="${section2 eq 'addStudent' }">
                        <spring:message code="addStudent"/>
                    </c:when>
                    <c:when test="${section2 eq 'edit' }">
                        ${student.fullName} | <spring:message code="edit"/>
                    </c:when>
                </c:choose>
            </c:when>
        </c:choose>
    </title>
    <jsp:include page="base/head.jsp" />
</head>
<body>
<div id="wrapper">

    <jsp:include page="base/sections.jsp" />
    <jsp:include page="base/nav.jsp" />


    <c:choose>
        <c:when test="${section eq 'admins'}">
            <c:set var="formModelAttribute" value="adminForm" />
            <c:choose>
                <c:when test="${section2 eq 'addAdmin' }">
                    <c:set var="title">
                        <spring:message code="admins"/> <small> - <spring:message code="addAdmin"/></small>
                    </c:set>

                    <c:set var="dniDisabled" value="false" />

                    <c:url var="formAction" value="/admins/add_admin" />
                    <spring:message var="formButton" code="addAdmin" />
                </c:when>
                <c:when test="${section2 eq 'edit' }">
                    <c:set var="title">
                        ${admin.fullName} <small> - <spring:message code="edit"/></small>
                    </c:set>

                    <c:set var="dniDisabled" value="true" />

                    <c:url var="formAction" value="/admins/${admin.dni}/edit" />
                    <spring:message var="formButton" code="saveChanges" />
                </c:when>
            </c:choose>
        </c:when>
        <c:when test="${section eq 'students'}">
            <c:set var="formModelAttribute" value="studentForm" />
            <c:choose>
                <c:when test="${section2 eq 'addStudent'}" >
                    <c:set var="title">
                        <spring:message code="students"/> <small> - <spring:message code="addStudent"/></small>
                    </c:set>

                    <c:set var="dniDisabled" value="false" />

                    <c:url var="formAction" value="/students/add_student" />
                    <spring:message var="formButton" code="addStudent" />
                </c:when>

                <c:when test="${section2 eq 'edit'}" >
                    <c:set var="title">
                        ${student.fullName} <small> - <spring:message code="edit"/></small>
                    </c:set>

                    <c:set var="dniDisabled" value="true" />

                    <c:url var="formAction" value="/students/${student.docket}/edit" />
                    <spring:message var="formButton" code="saveChanges" />
                </c:when>
            </c:choose>
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

            <!-- Alerts -->
            <jsp:include page="base/alerts.jsp" />
            <!-- /Alerts -->

            <!-- Student's Form -->
            <div class="row">
                <form:form modelAttribute="${formModelAttribute}" method="post" action="${formAction}">
                    <div class="col-xs-12 requiredFields">
                        <spring:message code="requiredFields"/>
                        (<span class="text-danger"><spring:message code="requiredIcon"/></span>)
                    </div>

                    <div class="col-xs-12 col-md-6">
                        <div class="row">
                            <!-- User Data -->
                            <div class="form-group col-xs-12">
                                <form:label for="dni" path="dni">
                                    <span></span><spring:message code="dni"/>
                                    (<span class="text-danger"><spring:message code="requiredIcon"/></span>)
                                </form:label>
                                <form:input type="text" class="form-control" id="dni" path="dni" readonly="${dniDisabled}"/>
                                <form:errors path="dni" cssClass="text-danger bg-danger" element="div"/>
                            </div>
                            <div class="form-group col-xs-6">
                                <form:label for="firstName" path="firstName">
                                    <spring:message code="firstName"/>
                                    (<span class="text-danger"><spring:message code="requiredIcon"/></span>)
                                </form:label>
                                <form:input type="text" class="form-control" id="firstName" path="firstName"/>
                                <form:errors path="firstName" cssClass="text-danger bg-danger" element="div"/>
                            </div>
                            <div class="form-group col-xs-6">
                                <form:label for="lastName" path="lastName">
                                    <spring:message code="lastName"/>
                                    (<span class="text-danger"><spring:message code="requiredIcon"/></span>)
                                </form:label>
                                <form:input type="text" class="form-control" id="lastName" path="lastName"/>
                                <form:errors path="lastName" cssClass="text-danger bg-danger" element="div"/>
                            </div>
                            <div class="form-group col-xs-12">
                                <form:label for="birthday" path="birthday">
                                    <spring:message code="birthday"/>
                                </form:label>
                                <spring:message code="birthdayPlaceholder" var="birthdayPlaceholder"/>
                                <form:input id="birthday" type="text" class="form-control" placeholder="${birthdayPlaceholder}" path="birthday"/>
                                <form:errors path="birthday" cssClass="text-danger bg-danger" element="div"/>
                            </div>
                                <%-- for future use --%>
                                <%--                            <div class="form-group col-xs-12">
                                                                <form:label for="email" path="email">
                                                                    <spring:message code="email"/>
                                                                </form:label>
                                                                <form:input type="text" class="form-control" id="email" path="email"/>
                                                                <form:errors path="email" cssClass="text-danger bg-danger" element="div"/>
                                                            </div>--%>
                            <div class ="form-group col-xs-12 col-md-6">
                                <form:label path="genre"><spring:message code="genre"/></form:label>
                                <div class="row">

                                    <div class="form-group">
                                        <div class="col-xs-12 col-sm-6">
                                            <label class="radio-inline">
                                                <form:radiobutton path="genre" value="M" /> <spring:message code="M"/>
                                            </label>
                                        </div>
                                        <div class="col-xs-12 col-sm-6">
                                            <label class="radio-inline">
                                                <form:radiobutton path="genre" value="F" /> <spring:message code="F"/>
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group col-xs-12 col-md-6">
                                <form:label for="telephone" path="telephone"><spring:message code="telephone"/></form:label>
                                <form:input type="text" class="form-control" id="telephone" path="telephone"/>
                                <form:errors path="telephone" cssClass="text-danger bg-danger" element="div"/>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-md-6">
                        <div class="row">
                            <!-- Address Data -->
                            <div class="form-group col-xs-12">
                                <form:label for="country" path="country">
                                    <spring:message code="country"/>
                                    (<span class="text-danger"><spring:message code="requiredIcon"/></span>)
                                </form:label>
                                <form:input type="text" class="form-control" id="country" path="country"/>
                                <form:errors path="country" cssClass="text-danger bg-danger" element="div"/>
                            </div>
                            <div class="form-group col-xs-6">
                                <form:label for="city" path="city">
                                    <spring:message code="city"/>
                                    (<span class="text-danger"><spring:message code="requiredIcon"/></span>)
                                </form:label>
                                <form:input type="text" class="form-control" id="city" path="city"/>
                                <form:errors path="city" cssClass="text-danger bg-danger" element="div"/>
                            </div>
                            <div class="form-group col-xs-6">
                                <form:label for="neighborhood" path="neighborhood">
                                    <spring:message code="neighbourhood"/>
                                    (<span class="text-danger"><spring:message code="requiredIcon"/></span>)
                                </form:label>
                                <form:input type="text" class="form-control" id="neighborhood" path="neighborhood"/>
                                <form:errors path="neighborhood" cssClass="text-danger bg-danger" element="div"/>
                            </div>
                            <div class="form-group col-xs-6">
                                <form:label for="street" path="street">
                                    <spring:message code="street"/>
                                    (<span class="text-danger"><spring:message code="requiredIcon"/></span>)
                                </form:label>
                                <form:input type="text" class="form-control" id="street" path="street"/>
                                <form:errors path="street" cssClass="text-danger bg-danger" element="div"/>
                            </div>
                            <div class="form-group col-xs-6">
                                <form:label for="number" path="number">
                                    <spring:message code="number"/>
                                    (<span class="text-danger"><spring:message code="requiredIcon"/></span>)
                                </form:label>
                                <form:input type="text" class="form-control" id="number" path="number"/>
                                <form:errors path="number" cssClass="text-danger bg-danger" element="div"/>
                            </div>
                            <div class="col-xs-12">
                                <div class="row">
                                    <div class="form-group col-xs-4">
                                        <form:label for="floor" path="floor"><spring:message code="floor"/></form:label>
                                        <form:input type="text" class="form-control" id="floor" path="floor"/>
                                        <form:errors path="floor" cssClass="text-danger bg-danger" element="div"/>
                                    </div>
                                    <div class="form-group col-xs-4">
                                        <form:label for="door" path="door"><spring:message code="door"/></form:label>
                                        <form:input type="text" class="form-control" id="door" path="door"/>
                                        <form:errors path="door" cssClass="text-danger bg-danger" element="div"/>
                                    </div>
                                    <div class="form-group col-xs-4">
                                        <form:label for="zipCode" path="zipCode"><spring:message code="zipCode"/></form:label>
                                        <form:input type="text" class="form-control" id="zipCode" path="zipCode"/>
                                        <form:errors path="zipCode" cssClass="text-danger bg-danger" element="div"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 hidden-xs"></div>
                    <div class="col-xs-12 col-md-6 addAdminFormButtons">
                        <div class="row">
                            <div class="col-xs-6 text-center">
                                <button type="submit" class="btn btn-info center-block fullWidthButton">
                                        ${formButton}
                                </button>
                            </div>
                            <div class="col-xs-6 text-center">
                                <button id="cancelButton" class="btn btn-default center-block fullWidthButton" role="button">
                                    <spring:message code="cancel"/>
                                </button>
                            </div>
                        </div>
                    </div>
                    <!-- End Data Input -->
                </form:form>
            </div>

            <!-- Student's Form -->

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->
    <jsp:include page="base/footer.jsp" />
</div>
<!-- Scripts -->
<jsp:include page="base/scripts.jsp" />
<script>
    $(document).ready(function(){

        $.datepicker.setDefaults( $.datepicker.regional[ "us" ] );

        $('#birthday').datepicker({
            dateFormat: "yy-mm-dd",
            defaultDate: "1994-01-01"
        });
    });
</script>
</body>
</html>