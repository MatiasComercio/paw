<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%-- Confirmation Modal --%>

<!-- Modal -->
<div class="modal fade" id="finalInscriptionFormConfirmationModal" tabindex="-1" role="dialog" aria-labelledby="finalInscriptionFormConfirmationModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">
                    <spring:message code="rate"/>
                </h4>
            </div>
            <div class="modal-body">
                <%-- FinalInscription Form --%>
                <c:url var="finalInscriptionFormAction" value="${finalInscriptionFormAction}" />
                <form:form class="form-horizontal" id="final_inscription_form" modelAttribute="finalInscriptionForm" action="${finalInscriptionFormAction}" method="post" enctype="application/x-www-form-urlencoded">
                    <%-- Seq id = Acta --%>
                    <div class="form-group">
                        <form:label path="id" class="col-xs-4 control-label"><spring:message code="inscriptionCode"/></form:label>
                        <div class="col-xs-8">
                            <form:input class="form-control" id="disabledInput" type="text" path="id" value="${id}" readonly="true"/>
                        </div>
                        <div class="col-xs-4"></div>
                        <div class="col-xs-8">
                            <form:errors path="id" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="courseName" class="col-xs-4 control-label"><spring:message code="course"/></form:label>
                        <div class="col-xs-8">
                            <form:input class="form-control" id="disabledInput" type="text" path="courseName" value="${docket}" readonly="true"/>
                        </div>
                        <div class="col-xs-4"></div>
                        <div class="col-xs-8">
                            <form:errors path="courseName" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="vacancy" class="col-xs-4 control-label"><spring:message code="final_vacancy"/></form:label>
                        <div class="col-xs-8">
                            <form:input class="form-control" id="disabledInput" type="text" path="vacancy" readonly="true"/>
                        </div>
                        <div class="col-xs-4"></div>
                        <div class="col-xs-8">
                            <form:errors path="vacancy" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                    <!--
                    <div class="form-group">
                        <form:label path="finalExamDate" class="col-xs-4 control-label"><spring:message code="final_date"/></form:label>
                        <div class="col-xs-8">
                            <form:input class="form-control" id="disabledInput" type="text" path="finalExamDate" readonly="true"/>
                        </div>
                        <div class="col-xs-4"></div>
                        <div class="col-xs-8">
                            <form:errors path="finalExamDate" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                    -->
                    <div class="form-group">
                        <div class="col-xs-8">
                            <form:input class="form-control" id="disabledInput" type="hidden" path="courseId" readonly="true"/>
                        </div>
                        <div class="col-xs-12">
                            <form:errors path="courseId" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>

                </form:form>
                <%-- FinalInscription Form--%>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="cancel"/></button>
                <button id="finalInscriptionFormConfirmAction" type="button" class="btn btn-info"><spring:message code="confirm"/></button>
            </div>
        </div>
    </div>
</div>

<%--  /Confirmation Modal --%>
