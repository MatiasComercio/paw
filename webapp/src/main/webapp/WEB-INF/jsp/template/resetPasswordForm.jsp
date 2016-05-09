<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%-- Confirmation Modal --%>

<!-- Modal -->
<div class="modal fade" id="resetPasswordFormConfirmationModal" tabindex="-1" role="dialog" aria-labelledby="resetPasswordFormConfirmationModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">
                    <spring:message code="resetPassword"/>
                </h4>
            </div>
            <div class="modal-body">
                <%-- Reset Password Form --%>
                <%--                    <form action="students/${student.docket}/delete" method="post">
                                        <button type="submit" value="students/${student.docket}/delete"><spring:message code="delete"/></button>
                                    </form>--%>
                <c:url var="formAction" value="/user/resetPassword" />
                <form:form class="form-horizontal" id="reset_password_form" modelAttribute="resetPasswordForm" action="${formAction}" method="post" enctype="application/x-www-form-urlencoded">
                    <div class="form-group">
                        <form:label path="dni" class="col-xs-4 control-label"><spring:message code="dni"/></form:label>
                        <div class="col-xs-8">
                            <form:input class="form-control" id="disabledInput" type="text" path="dni" readonly="true"/>
                        </div>
                        <div class="col-xs-4"></div>
                        <div class="col-xs-8">
                            <form:errors path="dni" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="firstName" class="col-xs-4 control-label"><spring:message code="firstName"/></form:label>
                        <div class="col-xs-8">
                            <form:input class="form-control" id="disabledInput" type="text" path="firstName" readonly="true"/>
                        </div>
                        <div class="col-xs-4"></div>
                        <div class="col-xs-8">
                            <form:errors path="firstName" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="lastName" class="col-xs-4 control-label"><spring:message code="lastName"/></form:label>
                        <div class="col-xs-8">
                            <form:input class="form-control" id="disabledInput" type="text" path="lastName" readonly="true"/>
                        </div>
                        <div class="col-xs-4"></div>
                        <div class="col-xs-8">
                            <form:errors path="lastName" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                </form:form>
                <%-- /Reset Password Form --%>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="cancel"/></button>
                <button id="resetPasswordFormConfirmAction" type="button" class="btn btn-danger"><spring:message code="confirm"/></button>
            </div>
        </div>
    </div>
</div>

<%--  /Confirmation Modal --%>