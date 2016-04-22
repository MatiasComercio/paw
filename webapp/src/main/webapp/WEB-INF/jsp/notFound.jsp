<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="base/head.jsp" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<c:url value="/static/css/course-detail.css" />" rel="stylesheet" type="text/css"/>
</head>
<body>
<h1>404 - Page Not Found</h1>
<div class="jumbotron">
    <div class="container">
        <div class="row">
            <div class="col-xs-12">
                Parece que ha ingresado una URL inválida.
                Por favor, vaya al inicio, y comience de nuevo.
            </div>
            <div class="col-xs-12">
                <a class="btn btn-info" href="/" role="button">Ir al Inicio</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
