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
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
        <a class="navbar-brand" href="/"><strong><spring:message code="webAbbreviation"/></strong> - <spring:message code="webName"/></a>
    </div>
</nav>
<div id="page-wrapper">

    <div class="container-fluid">

        <!-- Page Heading -->
        <div class="row">
            <div class="col-xs-12">
                <h1 class="page-header">
                    <spring:message code="login"/>
                </h1>
            </div>
        </div>

        <!-- content -->
<%-- +++ximprove: change username for dni; change labels for spring messages --%>
<%-- Copied from WebAuthConfig -> configure(http) -> loginPage --%>
<c:url value="/login" var="loginProcessingUrl"/>
<form action="${loginProcessingUrl}" method="post">
    <fieldset>
        <legend>Please Login</legend>
        <!-- use param.error assuming FormLoginConfigurer#failureUrl contains the query parameter error -->
        <c:if test="${param.error != null}">
            <div>
                Failed to login.
                <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                    Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />
                </c:if>
            </div>
        </c:if>
        <!-- the configured LogoutConfigurer#logoutSuccessUrl is /login?logout and contains the query param logout -->
        <c:if test="${param.logout != null}">
            <div>
                You have been logged out.
            </div>
        </c:if>
        <p>
            <label for="dni"><spring:message code="dni" /></label>
            <input type="text" id="dni" name="j_dni"/>
        </p>
        <p>
            <label for="password"><spring:message code="password" /></label>
            <input type="password" id="password" name="j_password"/>
        </p>
        <!-- if using RememberMeConfigurer make sure remember-me matches RememberMeConfigurer#rememberMeParameter -->
        <p>
            <label for="remember-me"><spring:message code="remember_me"/></label>
            <input type="checkbox" id="remember-me" name="j_remember_me"/>
        </p>
        <div>
            <button type="submit" class="btn"><spring:message code="login"/></button>
        </div>
    </fieldset>
</form>
<!-- Scripts -->
<jsp:include page="base/footer.jsp" />
</body>
</html>
