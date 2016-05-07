<%-- Taglib definitions --%>
<%--
To include this taglibs, you MUST use

<%@ include file="base/tags.jsp" %>

because taglibs are not recognised using the syntax <jsp:include page="base/tags.jsp" />

Info. about this:
* http://stackoverflow.com/questions/4318891/jstl-taglibs-not-recognized-when-declared-in-common-header
* https://objectpartners.com/2011/04/14/jsp-to-include-or-jspinclude/
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%--See: http://docs.spring.io/spring-security/site/docs/4.1.0.RELEASE/reference/htmlsingle/#taglibs-authorize--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
