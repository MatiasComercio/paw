<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | <spring:message code="addStudent"/>
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
                        <spring:message code="students"/> <small> - <spring:message code="add"/></small>
                    </h1>
                </div>
            </div>

            <!-- -->
            <div class="container">
                <jsp:include page="base/alerts.jsp" />

                <form:form modelAttribute="studentForm" method="post" action="/students/add_student">
                    <!-- User Data -->
                    <div class="form-group">
                        <form:label for="dni" path="dni"><span><spring:message code="requiredIcon"/> </span><spring:message code="dni"/></form:label>
                        <form:input type="text" class="form-control" id="dni" path="dni"/>
                        <form:errors path="dni" cssClass="text-danger bg-danger" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="firstName" path="firstName"><span><spring:message code="requiredIcon"/> </span><spring:message code="firstName"/></form:label>
                        <form:input type="text" class="form-control" id="firstName" path="firstName"/>
                        <form:errors path="firstName" cssClass="text-danger bg-danger" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="lastName" path="lastName"><span><spring:message code="requiredIcon"/> </span><spring:message code="lastName"/></form:label>
                        <form:input type="text" class="form-control" id="lastName" path="lastName"/>
                        <form:errors path="lastName" cssClass="text-danger bg-danger" element="div"/>
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
                        <form:errors path="birthday" cssClass="text-danger bg-danger" element="div"/>
                    </div>
                     <!-- Address Data -->
                    <div class="form-group">
                        <form:label for="country" path="country"><span><spring:message code="requiredIcon"/> </span><spring:message code="country"/></form:label>
                        <form:input type="text" class="form-control" id="country" path="country"/>
                        <form:errors path="country" cssClass="text-danger bg-danger" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="city" path="city"><span><spring:message code="requiredIcon"/> </span><spring:message code="city"/></form:label>
                        <form:input type="text" class="form-control" id="city" path="city"/>
                        <form:errors path="city" cssClass="text-danger bg-danger" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="neighborhood" path="neighborhood"><span><spring:message code="requiredIcon"/> </span><spring:message code="neighbourhood"/></form:label>
                        <form:input type="text" class="form-control" id="neighborhood" path="neighborhood"/>
                        <form:errors path="neighborhood" cssClass="text-danger bg-danger" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="street" path="street"><span><spring:message code="requiredIcon"/> </span><spring:message code="street"/></form:label>
                        <form:input type="text" class="form-control" id="street" path="street"/>
                        <form:errors path="street" cssClass="text-danger bg-danger" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="number" path="number"><span><spring:message code="requiredIcon"/> </span><spring:message code="number"/></form:label>
                        <form:input type="text" class="form-control" id="number" path="number"/>
                        <form:errors path="number" cssClass="text-danger bg-danger" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="floor" path="floor"><spring:message code="floor"/></form:label>
                        <form:input type="text" class="form-control" id="floor" path="floor"/>
                        <form:errors path="floor" cssClass="text-danger bg-danger" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="door" path="door"><spring:message code="door"/></form:label>
                        <form:input type="text" class="form-control" id="door" path="door"/>
                        <form:errors path="door" cssClass="text-danger bg-danger" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="telephone" path="telephone"><spring:message code="telephone"/></form:label>
                        <form:input type="text" class="form-control" id="telephone" path="telephone"/>
                        <form:errors path="telephone" cssClass="text-danger bg-danger" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="zipCode" path="zipCode"><spring:message code="zipCode"/></form:label>
                        <form:input type="text" class="form-control" id="zipCode" path="zipCode"/>
                        <form:errors path="zipCode" cssClass="text-danger bg-danger" element="div"/>
                    </div>
                    <!-- End Data Input -->
                    <input type="submit" class="btn btn-info" value="Agregar alumno"/>
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