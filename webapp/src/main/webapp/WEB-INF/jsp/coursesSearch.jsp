<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | <spring:message code="courses"/>
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
                        <spring:message code="courses"/>
                        <%-- +++xfix TODO: currently, if you want to add a course, and write invalid input --> DB exception not catched --> exception 500 --%>
                        <%--<a href="<c:url value="courses/add_course"/>" class="btn btn-info pull-right" role="button">Agregar materia</a>--%>
                    </h1>
                </div>
            </div>

            <!-- Result Message -->
            <!-- search -->
            <div class="row">
                <jsp:include page="base/alerts.jsp" />
                <div class="col-md-6">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="input-group">
                                <span class="input-group-addon" id="sizing-addon"><spring:message code="id"/></span>
                                <spring:message code="searchBy" var="searchByPlaceholder"/>
                                <spring:message code="id" var="idPlaceholder"/>
                                <input id="id_text" type="text" class="form-control" placeholder="${searchByPlaceholder} ${idPlaceholder}..." aria-describedby="sizing-addon2">
                                <span class="input-group-addon" id="sizing-addon2"><spring:message code="name"/></span>
                                <spring:message code="name" var="namePlaceholder"/>
                                <input id="name_text" type="text" class="form-control" placeholder="${searchByPlaceholder} ${namePlaceholder}..." aria-describedby="sizing-addon2">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-1">
                    <div id="search" type="button" class="btn btn-default"><spring:message code="search"/></div>
                </div>
                <div class="col-md-2">
                    <div id="addCourse" type="button" class="btn btn-default"><spring:message code="add"/></div>
                </div>
            </div>

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
                    <c:forEach items="${courses}" var="course">
                        <tr>
                            <td>${ course.id }</td>
                            <td>${ course.name }</td>
                            <td>${ course.credits }</td>
                            <td>${ course.semester }</td>
                            <td>
                                <a href="<c:url value="courses/${course.id}/info" />">Ver</a>
                                <form action="courses/${course.id}/delete" method="post">
                                    <button type="submit" value="courses/${course.id}/delete"><spring:message code="delete"/></button>
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
    <jsp:include page="base/footer.jsp" />
</div>

<!-- Scripts -->
<script>
    $( document ).ready(function() {

        var urlWithFilters = function() {
            var id_text = $("#id_text").val();
            var name_text = $("#name_text").val();

            document.location.href = "?keyword=" + name_text + "&" + "id=" + id_text;
        }

        $('#search').click(urlWithFilters);

        var addCourse = function(){window.location="/courses/add_course";}
        $('#addCourse').on("click", addCourse);

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