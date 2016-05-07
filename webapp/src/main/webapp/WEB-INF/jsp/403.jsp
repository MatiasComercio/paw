<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>
        <spring:message code="webAbbreviation"/> | <spring:message code="pageNotFound_head"/>
    </title>
    <jsp:include page="base/head.jsp" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div class="jumbotron">
    <div class="container">
        <div class="row">
            <div class="col-xs-12">
                <h1><spring:message code="pageForbidden_title"/></h1>
            </div>
            <div class="col-xs-12">
                <p><spring:message code="pageForbidden_reason"/></p>
            </div>
            <div class="col-xs-4"></div>
            <div class="col-xs-4 text-center">
                <a class="btn btn-info btn-lg" href="/" role="button">
                    <spring:message code="goHomeButton"/>
                </a>
            </div>
        </div>
    </div>
</div>
<!-- Scripts -->
<jsp:include page="base/footer.jsp" />
</body>
</html>
