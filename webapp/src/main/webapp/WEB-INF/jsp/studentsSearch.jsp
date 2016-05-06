<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | <spring:message code="students"/>
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
                        <spring:message code="students"/>
                    </h1>
                </div>
            </div>

            <!-- search -->
            <div class="row">
                <div class="col-xs-12">
                    <jsp:include page="base/alerts.jsp" />
                </div>
                <div class="col-xs-9">
                    <div class="row">
                        <div class="input-group">
                            <spring:message code="docket" var="docketPlaceholder"/>
                            <spring:message code="firstName" var="firstNamePlaceholder"/>
                            <spring:message code="lastName" var="lastNamePlaceholder"/>
                            <spring:message code="searchBy" var="searchByPlaceholder"/>
                            <span class="input-group-addon" id="sizing-addon"><spring:message code="docket"/></span>
                            <input id="docket_text" type="text" class="form-control" placeholder="${searchByPlaceholder} ${docketPlaceholder}..." aria-describedby="sizing-addon2">
                            <span class="input-group-addon" id="sizing-addon2"><spring:message code="firstName"/></span>
                            <input id="first_name_text" type="text" class="form-control" placeholder="${searchByPlaceholder} ${firstNamePlaceholder}..." aria-describedby="sizing-addon2">
                            <span class="input-group-addon" id="sizing-addon3"><spring:message code="lastName"/></span>
                            <input id="last_name_text" type="text" class="form-control" placeholder="${searchByPlaceholder} ${lastNamePlaceholder}..." aria-describedby="sizing-addon2">
                        </div>
                    </div>
                </div>
                <div class="col-md-1">
                    <div id="search" type="button" class="btn btn-default"><spring:message code="search"/></div>
                </div>
                <div class="col-md-1">
                    <div id="addStudent" type="button" class="btn btn-info"><spring:message code="add"/></div>
                </div>
            </div>

            <!-- content -->
            <div class="table-responsive">
                <table class="table table-hover <%--table-bordered--%> <%--table-condensed--%>">
                    <thead>
                    <tr>
                        <th><spring:message code="docket"/></th>
                        <th><spring:message code="firstName"/></th>
                        <th><spring:message code="lastName"/></th>
                        <th><spring:message code="email"/></th>
                        <th><spring:message code="actions"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${students}" var="student">
                        <tr>
                            <td>${ student.docket }</td>
                            <td>${ student.firstName }</td>
                            <td>${ student.lastName }</td>
                            <td>${ student.email }</td>
                            <td><a href="<c:url value="/students/${student.docket}/info" />"><spring:message code="see"/></a>
                                <form action="students/${student.docket}/delete" method="post">
                                    <button type="submit" value="students/${student.docket}/delete"><spring:message code="delete"/></button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- /content -->

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- Scripts -->
<jsp:include page="base/footer.jsp" />
<script>
    $( document ).ready(function() {
        var urlWithFilters = function() {
            var docket_text = $("#docket_text").val();
            var first_name_text = $("#first_name_text").val();
            var last_name_text = $("#last_name_text").val();

            document.location.href = "?docket=" + docket_text + "&" + "firstName=" + first_name_text + "&" + "lastName=" + last_name_text;
        }

        $('#search').click(urlWithFilters);

        var addStudent = function(){window.location="/students/add_student";}
        $('#addStudent').on("click", addStudent);

        /* source: http://stackoverflow.com/questions/10905345/pressing-enter-on-a-input-type-text-how */
        $("input").bind("keypress", {}, keypressInBox);

        function keypressInBox(e) {
            var code = (e.keyCode ? e.keyCode : e.which);
            if (code == 13) { //Enter keycode
                e.preventDefault();
                urlWithFilters();
            }
        };
    });
</script>
</body>
</html>