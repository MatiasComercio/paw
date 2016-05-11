<%@include file="base/tags.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | <spring:message code="admins"/>
    </title>
    <jsp:include page="base/head.jsp" />
</head>
<body>

<div id="wrapper">


    <jsp:include page="base/sections.jsp"/>
    <jsp:include page="base/nav.jsp" />

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-xs-12">
                    <h1 class="page-header">
                        <spring:message code="admins"/>
                    </h1>
                </div>
            </div>

            <jsp:include page="base/alerts.jsp" />

            <!-- content -->
            <jsp:include page="template/searchAdmins.jsp" />
            <!-- /content -->

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->
    <jsp:include page="base/footer.jsp" />
</div>
<!-- Scripts -->
<jsp:include page="base/scripts.jsp"/>
</body>
</html>
