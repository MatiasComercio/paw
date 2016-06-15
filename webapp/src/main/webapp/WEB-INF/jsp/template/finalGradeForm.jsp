<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%-- Confirmation Modal --%>

<!-- Modal -->
<div class="modal fade" id="finalGradeFormConfirmationModal" tabindex="-1" role="dialog" aria-labelledby="finalGradeFormConfirmationModal">
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
                <c:url var="finalGradeFormAction" value="${finalGradeFormAction}" />
                <form:form class="form-horizontal" id="final_grade_form" modelAttribute="gradeForm" action="${finalGradeFormAction}" method="post" enctype="application/x-www-form-urlencoded">
                    <%-- Seq id = Acta --%>
                    <div class="form-group">
                        <form:label path="docket" class="col-xs-4 control-label"><spring:message code="studentDocket"/></form:label>
                        <div class="col-xs-8">
                            <form:input class="form-control" id="disabledInput" type="text" path="docket" value="" readonly="true"/>
                        </div>
                        <div class="col-xs-4"></div>
                        <div class="col-xs-8">
                            <form:errors path="docket" cssClass="text-danger bg-danger" element="div"/>
                        </div>
                    </div>

                    <div class="form-group hidden">
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
                        <form:label path="courseCodId" class="col-xs-4 control-label"><spring:message code="courseId"/></form:label>
                        <div class="col-xs-8">
                            <form:input class="form-control" id="disabledInput" type="text" path="courseCodId" readonly="true"/>
                        </div>
                        <div class="col-xs-4"></div>
                        <div class="col-xs-8">
                            <form:errors path="courseCodId" cssClass="text-danger bg-danger" element="div"/>
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

                </form:form>
                <%-- /Grade Form--%>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="cancel"/></button>
                <button id="finalGradeFormConfirmAction" type="button" class="btn btn-info"><spring:message code="confirm"/></button>
            </div>
        </div>
    </div>
</div>

<%--  /Confirmation Modal --%>
