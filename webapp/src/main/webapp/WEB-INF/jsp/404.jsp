<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="base/head.jsp" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<%--<div id="wrapper">--%>
    <%--<jsp:include page="base/nav.jsp" />--%>
    <%--<h1>404 - Page Not Found</h1>--%>
    <div class="jumbotron">
        <div class="container">
            <div class="row">
                <div class="col-xs-12">
                    <h1>404 - Page Not Found</h1>
                </div>
                <div class="col-xs-12">
                    <p>Parece que ha ingresado una URL inválida.
                    Por favor, vaya al inicio y comience de nuevo.</p>
                </div>
                <div class="col-xs-4"></div>
                <div class="col-xs-4">
                    <a class="btn btn-info btn-lg center-block" href="/" role="button">Ir al Inicio</a>
                </div>
            </div>
        </div>
    </div>
<%--</div>--%>
</body>
</html>
