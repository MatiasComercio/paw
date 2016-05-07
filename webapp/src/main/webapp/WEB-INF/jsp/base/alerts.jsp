<%@ include file="tags.jsp" %>

<div class="row">
    <div class="col-xs-12">
        <c:if test="${not empty alert and not empty message}">

            <c:if test="${alert == 'success'}">
                <div class="alert alert-success">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong><spring:message code="success"/></strong> ${message}
                </div>
            </c:if>

            <c:if test="${alert == 'info'}">
                <div class="alert alert-info">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong><spring:message code="attention"/></strong> ${message}
                </div>
            </c:if>

            <c:if test="${alert == 'warning'}">
                <div class="alert alert-warning">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong><spring:message code="attention"/></strong> ${message}
                </div>
            </c:if>

            <c:if test="${alert == 'danger'}">
                <div class="alert alert-danger">
                    <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    <strong><spring:message code="error"/></strong> ${message}
                </div>
            </c:if>

        </c:if>
    </div>
</div>