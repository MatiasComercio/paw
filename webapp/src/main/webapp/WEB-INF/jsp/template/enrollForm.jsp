<%@ include file="../base/tags.jsp"%>
<%-- Confirmation Modal --%>

<!-- Modal -->
<div class="modal fade" id="enrollFormConfirmationModal" tabindex="-1" role="dialog" aria-labelledby="enrollFormConfirmationModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">
                    <c:choose>
                        <c:when test="${subsection_enroll}">
                            <spring:message code="enroll"/>
                        </c:when>
                        <c:when test="${subsection_courses}">
                            <spring:message code="unenroll"/>
                        </c:when>
                    </c:choose>
                </h4>
            </div>
            <div class="modal-body">
                <%-- Inscription Form --%>
                <c:url var="inscriptionFormAction" value="${inscriptionFormAction}" />
                <form:form class="form-horizontal" id="inscription_form" modelAttribute="inscriptionForm" action="${inscriptionFormAction}" method="post" enctype="application/x-www-form-urlencoded">
                    <div class="form-group">
                        <form:label path="studentDocket" class="col-xs-4 control-label"><spring:message code="studentDocket"/></form:label>
                        <div class="col-xs-8">
                            <form:input class="form-control" id="disabledInput" type="text" path="studentDocket" value="${docket}" readonly="true"/>
                        </div>
                        <div class="col-xs-4"></div>
                        <div class="col-xs-8">
                            <form:errors path="studentDocket" cssClass="text-danger bg-danger" element="div"/>
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
                </form:form>
                <%-- /Inscription Form--%>
            </div>
            <div class="modal-footer">
                <c:if test="${subsection_enroll}">
                    <button id="enrollFormConfirmAction" type="button" class="btn btn-info"><spring:message code="confirm"/></button>
                </c:if>
                <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="cancel"/></button>
                <c:if test="${subsection_courses}">
                    <button id="enrollFormConfirmAction" type="button" class="btn btn-danger"><spring:message code="confirm"/></button>
                </c:if>
            </div>
        </div>
    </div>
</div>

<%--  /Confirmation Modal --%>