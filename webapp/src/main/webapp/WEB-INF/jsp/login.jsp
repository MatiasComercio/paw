<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | <spring:message code="login"/>
    </title>
    <jsp:include page="base/head.jsp" />
</head>
<body class="remove_out_spaces">
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <a class="navbar-brand" href="/"><strong><spring:message code="webAbbreviation"/></strong> - <spring:message code="webName"/></a>
    </div>
</nav>
<div>
    <div class="jumbotron">
        <div class="container-fluid">
            <!-- Page Heading -->
            <div class="row">
                <div class="col-xs-12 col-sm-2"></div>
                <div class="col-xs-12 col-sm-8">
                    <div class="row">
                        <div class="col-xs-12">
                            <h1 class="page-header">
                                <spring:message code="login"/>
                            </h1>
                        </div>
                    </div>
                    <div class="row">
                        <c:url value="/login" var="loginProcessingUrl"/>
                        <form action="${loginProcessingUrl}" method="post">
                            <!-- use param.error assuming FormLoginConfigurer#failureUrl contains the query parameter error -->

                            <c:if test="${param.error != null}">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="alert alert-danger">
                                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                            <strong><spring:message code="error"/></strong> <spring:message code="login_failed"/>
                                            <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                                                - <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                            <!-- the configured LogoutConfigurer#logoutSuccessUrl is /login?logout and contains the query param logout -->
                            <c:if test="${param.logout != null}">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="alert alert-success">
                                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                            <strong><spring:message code="success"/></strong> <spring:message code="logout_success"/>
                                        </div>
                                    </div>
                                </div>

                            </c:if>
                            <div class="col-xs-12 form-group">
                                <label for="dni"><spring:message code="dni" /></label>
                                <input type="text" id="dni" name="j_dni" class="form-control"/>
                            </div>
                            <div class="col-xs-12 form-group">
                                <label for="password"><spring:message code="password" /></label>
                                <input type="password" id="password" name="j_password" class="form-control"/>
                            </div>
                            <!-- if using RememberMeConfigurer make sure remember-me matches RememberMeConfigurer#rememberMeParameter -->
                            <div class="col-xs-12 checkbox">
                                <label for="remember-me">
                                    <input type="checkbox" id="remember-me" name="j_remember_me"/>
                                    <spring:message code="remember_me"/>
                                </label>
                            </div>
                            <div class="col-xs-12 text-center">
                                <button type="submit" class="btn btn-info btn-lg center-block">
                                    <spring:message code="login"/>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="base/footer.jsp" />
</div>
<!-- Scripts -->
</body>
</html>
