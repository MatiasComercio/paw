<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%-- Confirmation Modal --%>

<!-- Modal -->
<div class="modal fade" id="gradeFormConfirmationModal" tabindex="-1" role="dialog" aria-labelledby="gradeFormConfirmationModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">
                    <spring:message code="rate"/>
                </h4>
            </div>
            <div class="modal-body">
                <%-- Grade Form --%>
                <c:url var="gradeFormAction" value="${gradeFormAction}" />
                <form:form class="form-horizontal" id="grade_form" modelAttribute="gradeForm" action="${gradeFormAction}" method="post" enctype="application/x-www-form-urlencoded">
                    <%-- Seq id = Acta --%>
                    <div class="form-group">
                        <form:label path="id" class="col-xs-4 control-label"><spring:message code="minutes"/></form:label>
                        <div class="col-xs-8">
                            <form:input class="form-control" id="disabledInput" type="text" path="id" value="${id}" readonly="true"/>
                        </div>
                        <div class="col-xs-4"></div>
                        <div class="col-xs-8">
                            <form:errors path="id" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="docket" class="col-xs-4 control-label"><spring:message code="studentDocket"/></form:label>
                        <div class="col-xs-8">
                            <form:input class="form-control" id="disabledInput" type="text" path="docket" value="${docket}" readonly="true"/>
                        </div>
                        <div class="col-xs-4"></div>
                        <div class="col-xs-8">
                            <form:errors path="docket" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
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
                        <form:label path="grade" class="col-xs-4 control-label"><spring:message code="grade"/></form:label>
                        <div class="col-xs-8">
                            <form:input class="form-control" id="disabledInput" type="text" path="grade"/>
                        </div>
                        <div class="col-xs-4"></div>
                        <div class="col-xs-8">
                            <form:errors path="grade" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>
                    <c:if test="${subsection_grades}">
                        <div class="form-group">
                            <div class="col-xs-8">
                                <form:input class="form-control" id="disabledInput" type="hidden" path="modified" readonly="true"/>
                            </div>
                            <div class="col-xs-12">
                                <form:errors path="modified" cssClass="text-danger bg-danger" element="div"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-xs-8">
                                <form:input class="form-control" id="disabledInput" type="hidden" path="oldGrade" readonly="true"/>
                            </div>
                            <div class="col-xs-12">
                                <form:errors path="oldGrade" cssClass="text-danger bg-danger" element="div"/>
                            </div>
                        </div>
                    </c:if>
                </form:form>
                <%-- /Grade Form--%>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="cancel"/></button>
                <button id="gradeFormConfirmAction" type="button" class="btn btn-info"><spring:message code="confirm"/></button>
            </div>
        </div>
    </div>
</div>

<%--  /Confirmation Modal --%>
