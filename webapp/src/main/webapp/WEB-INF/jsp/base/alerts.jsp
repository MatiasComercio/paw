<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<c:if test="${not empty alert and not empty message}">

    <c:if test="${alert == 'success'}">
        <div class="alert alert-success">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong>Éxito!</strong> ${message}
        </div>
    </c:if>

    <c:if test="${alert == 'info'}">
        <div class="alert alert-info">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong>Atención!</strong> ${message}
        </div>
    </c:if>

    <c:if test="${alert == 'warning'}">
        <div class="alert alert-warning">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong>¡Atención!</strong> ${message}
        </div>
    </c:if>

    <c:if test="${alert == 'danger'}">
        <div class="alert alert-danger">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <strong>¡Error!</strong> ${message}
        </div>
    </c:if>

</c:if>