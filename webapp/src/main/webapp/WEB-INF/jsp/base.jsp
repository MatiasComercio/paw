<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <jsp:include page="./head.jsp" />
</head>

<body>

<div id="wrapper">

    <jsp:include page="./nav.jsp" />

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                        Alumnos <small>Lista de alumnos</small>
                    </h1>
                </div>
            </div>

            <!--<div class="alert alert-{{ message.tags }} alert-dismissible fade in" role="alert">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <strong>{{ message.tags | title }}!</strong> {{ message }}.
            </div>-->

            <!-- content -->

            <h1>Creating user: ${user.username}</h1>

            <!-- /content -->

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->
<!-- Bootstrap Core JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>

<!-- Morris Charts JavaScript -->
<script src="<c:url value="/static/js/plugins/morris/raphael.min.js" />"></script>
<script src="<c:url value="/static/js/plugins/morris/morris.min.js"/>"></script>
<script src="<c:url value="/static/js/plugins/morris/morris-data.js"/>"></script>

</body>

</html>


