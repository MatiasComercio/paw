<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%-- Confirmation Modal --%>

<!-- Modal -->
<div class="modal fade" id="deleteCourseFormConfirmationModal" tabindex="-1" role="dialog" aria-labelledby="deleteCourseFormConfirmationModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">
                    <c:choose>
                        <c:when test="${section == 'courses'}">
                            <spring:message code="delete"/>
                        </c:when>
                    </c:choose>
                </h4>
            </div>
            <div class="modal-body">
                <form:form class="form-horizontal" id="delete_course_form" modelAttribute="deleteCourseForm" action="" method="post" enctype="application/x-www-form-urlencoded">
                    <div class="form-group">
                        <form:label path="id" class="col-xs-4 control-label"><spring:message code="id"/></form:label>
                        <div class="col-xs-8">
                            <form:input class="form-control" id="disabledInput" type="text" path="id" readonly="true"/>
                        </div>
                        <div class="col-xs-4"></div>
                        <div class="col-xs-8">
                            <form:errors path="id" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="name" class="col-xs-4 control-label"><spring:message code="name"/></form:label>
                        <div class="col-xs-8">
                            <form:input class="form-control" id="disabledInput" type="text" path="name" readonly="true"/>
                        </div>
                        <div class="col-xs-4"></div>
                        <div class="col-xs-8">
                            <form:errors path="name" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                </form:form>
                <%-- /Delete Course Form--%>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="cancel"/></button>
                <c:if test="${section == 'courses'}">
                    <button id="deleteCourseFormConfirmAction" type="button" class="btn btn-danger"><spring:message code="confirm"/></button>
                </c:if>
            </div>
        </div>
    </div>
</div>

<%--  /Confirmation Modal --%>