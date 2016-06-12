<%@ include file="base/tags.jsp"%>
<html>
<head>
    <title>
        <spring:message code="webAbbreviation"/> | <spring:message code="inMaintenance_title"/>
    </title>
    <jsp:include page="base/head.jsp" />
</head>
<body>
<!-- Navigation -->
<nav class="navbar navbar-inverse navbar-fixed-top">

    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <a class="navbar-brand" href="#">
            <strong><spring:message code="webAbbreviation"/></strong>
            <span class="hidden-xs">
                - <spring:message code="webName"/>
            </span>
        </a>
    </div>
</nav>
<div class="jumbotron">
    <div class="container">
        <div class="row">
            <div class="col-xs-12">
                <h1><spring:message code="inMaintenance_title"/></h1>
            </div>
            <div class="col-xs-12">
                <p><spring:message code="inMaintenance_reason"/></p>
            </div>
        </div>
    </div>
</div>
<!-- Scripts -->
<jsp:include page="base/footer.jsp" />
</body>
</html>
