<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%-- Confirmation Modal --%>

<!-- Modal -->
<div class="modal fade" id="correlativeFormConfirmationModal" tabindex="-1" role="dialog" aria-labelledby="correlativeFormConfirmationModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">
                    <c:choose>
                        <c:when test="${subsection_add_correlative}">
                            <spring:message code="add_correlative"/>
                        </c:when>
                        <c:when test="${subsection_delete_correlative}">
                            <spring:message code="delete_correlative"/>
                        </c:when>
                    </c:choose>
                </h4>
            </div>
            <div class="modal-body">
                <%-- Correlative Form --%>
                <c:url var="correlativeFormAction" value="${correlativeFormAction}" />
                <form:form class="form-horizontal" id="correlative_form" modelAttribute="correlativeForm" action="${correlativeFormAction}" method="post" enctype="application/x-www-form-urlencoded">
                    <div class="form-group">
                        <form:label path="courseId" class="col-xs-4 control-label"><spring:message code="courseId"/></form:label>
                        <div class="col-xs-8">
                            <form:input class="form-control" id="disabledInput" type="text" path="courseId" readonly="true"/>
                        </div>
                        <div class="col-xs-4"></div>
                        <div class="col-xs-8">
                            <form:errors path="courseId" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="courseName" class="col-xs-4 control-label"><spring:message code="courseName"/></form:label>
                        <div class="col-xs-8">
                            <form:input class="form-control" id="disabledInput" type="text" path="courseName" readonly="true"/>
                        </div>
                        <div class="col-xs-4"></div>
                        <div class="col-xs-8">
                            <form:errors path="courseName" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="correlativeId" class="col-xs-4 control-label"><spring:message code="correlativeId"/></form:label>
                        <div class="col-xs-8">
                            <form:input class="form-control" id="disabledInput" type="text" path="correlativeId" readonly="true"/>
                        </div>
                        <div class="col-xs-4"></div>
                        <div class="col-xs-8">
                            <form:errors path="correlativeId" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="correlativeName" class="col-xs-4 control-label"><spring:message code="correlativeName"/></form:label>
                        <div class="col-xs-8">
                            <form:input class="form-control" id="disabledInput" type="text" path="correlativeName" readonly="true"/>
                        </div>
                        <div class="col-xs-4"></div>
                        <div class="col-xs-8">
                            <form:errors path="correlativeName" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                </form:form>
                <%-- /Correlative Form--%>
            </div>
            <div class="modal-footer">
                <c:if test="${subsection_add_correlative}">
                    <button id="correlativeFormConfirmAction" type="button" class="btn btn-info"><spring:message code="confirm"/></button>
                </c:if>
                <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="cancel"/></button>
                <c:if test="${subsection_delete_correlative}">
                    <button id="correlativeFormConfirmAction" type="button" class="btn btn-danger"><spring:message code="confirm"/></button>
                </c:if>
            </div>
        </div>
    </div>
</div>

<%--  /Confirmation Modal --%>