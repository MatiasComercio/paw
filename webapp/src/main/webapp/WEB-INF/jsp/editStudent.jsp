<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | <spring:message code="editStudent"/>
    </title>
    <jsp:include page="base/head.jsp" />
</head>
<body>

<div id="wrapper">

    <jsp:include page="base/nav.jsp" />

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                        <spring:message code="editStudent"/>
                    </h1>
                </div>
            </div>

            <!-- -->
            <div class="container">
                <jsp:include page="base/alerts.jsp" />
                <form:form modelAttribute="studentForm" method="post" action="/students/${docket}/edit">
                    <!-- User Data -->
                    <div class="form-group">
                        <form:label for="dni" path="dni"><span style="color:red"><spring:message code="requiredIcon"/> </span><spring:message code="dni"/></form:label>
                        <form:input type="text" class="form-control" id="dni" path="dni"/>
                        <form:errors path="dni" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="firstName" path="firstName"><span style="color:red"><spring:message code="requiredIcon"/> </span><spring:message code="firstName"/></form:label>
                        <form:input type="text" class="form-control" id="firstName" path="firstName"/>
                        <form:errors path="firstName" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="lastName" path="lastName"><span style="color:red"><spring:message code="requiredIcon"/> </span><spring:message code="lastName"/></form:label>
                        <form:input type="text" class="form-control" id="lastName" path="lastName"/>
                        <form:errors path="lastName" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class ="form-group">
                        <div class ="row">
                            <div class="col-md-3">
                                <form:label path="genre"><spring:message code="genre"/></form:label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <form:radiobutton path="genre" value="M" /> <spring:message code="M"/>
                            </div>
                            <div class="col-md-3">
                                <form:radiobutton path="genre" value="F" /> <spring:message code="F"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label for="birthday" path="birthday"><spring:message code="birthday"/>:</form:label>
                        <spring:message code="birthdayPlaceholder" var="birthdayPlaceholder"/>
                        <form:input id="birthday" type="text" class="form-control" placeholder="${birthdayPlaceholder}" path="birthday"/>
                        <form:errors path="birthday" cssStyle="color: red;" element="div"/>
                    </div>
                    <!-- Address Data -->
                    <div class="form-group">
                        <form:label for="country" path="country"><span style="color:red"><spring:message code="requiredIcon"/> </span><spring:message code="country"/></form:label>
                        <form:input type="text" class="form-control" id="country" path="country"/>
                        <form:errors path="country" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="city" path="city"><span style="color:red"><spring:message code="requiredIcon"/> </span><spring:message code="city"/></form:label>
                        <form:input type="text" class="form-control" id="city" path="city"/>
                        <form:errors path="city" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="neighborhood" path="neighborhood"><span style="color:red"><spring:message code="requiredIcon"/> </span><spring:message code="neighbourhood"/></form:label>
                        <form:input type="text" class="form-control" id="neighborhood" path="neighborhood"/>
                        <form:errors path="neighborhood" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="street" path="street"><span style="color:red"><spring:message code="requiredIcon"/> </span><spring:message code="street"/></form:label>
                        <form:input type="text" class="form-control" id="street" path="street"/>
                        <form:errors path="street" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="number" path="number"><span style="color:red"><spring:message code="requiredIcon"/> </span><spring:message code="number"/></form:label>
                        <form:input type="text" class="form-control" id="number" path="number"/>
                        <form:errors path="number" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="floor" path="floor"><spring:message code="floor"/></form:label>
                        <form:input type="text" class="form-control" id="floor" path="floor"/>
                        <form:errors path="floor" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="door" path="door"><spring:message code="door"/></form:label>
                        <form:input type="text" class="form-control" id="door" path="door"/>
                        <form:errors path="door" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="telephone" path="telephone"><spring:message code="telephone"/></form:label>
                        <form:input type="text" class="form-control" id="telephone" path="telephone"/>
                        <form:errors path="telephone" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="zipCode" path="zipCode"><spring:message code="zipCode"/></form:label>
                        <form:input type="text" class="form-control" id="zipCode" path="zipCode"/>
                        <form:errors path="zipCode" cssStyle="color: red;" element="div"/>
                    </div>
                    <!-- End Data Input -->
                    <spring:message code="saveChanges" var="saveChangesButton"/>
                    <input type="submit" class="btn btn-info" value="${saveChangesButton}"/>
                </form:form>
            </div>

            <!-- -->

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->
    <jsp:include page="base/footer.jsp" />
</div>
<!-- Scripts -->
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