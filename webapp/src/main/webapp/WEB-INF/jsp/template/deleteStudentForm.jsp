<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%-- Confirmation Modal --%>

<!-- Modal -->
<div class="modal fade" id="deleteStudentFormConfirmationModal" tabindex="-1" role="dialog" aria-labelledby="deleteStudentFormConfirmationModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">
                    <c:choose>
                        <c:when test="${subsection_students}">
                            <spring:message code="delete"/>
                        </c:when>
                    </c:choose>
                </h4>
            </div>
            <div class="modal-body">
                <%-- Delete Student Form --%>
<%--                    <form action="students/${student.docket}/delete" method="post">
                        <button type="submit" value="students/${student.docket}/delete"><spring:message code="delete"/></button>
                    </form>--%>
                <form:form class="form-horizontal" id="delete_student_form" modelAttribute="deleteStudentForm" action="" method="post" enctype="application/x-www-form-urlencoded">
                    <div class="form-group">
                        <form:label path="docket" class="col-xs-4 control-label"><spring:message code="docket"/></form:label>
                        <div class="col-xs-8">
                            <form:input class="form-control" id="disabledInput" type="text" path="docket" readonly="true"/>
                        </div>
                        <div class="col-xs-4"></div>
                        <div class="col-xs-8">
                            <form:errors path="docket" cssClass="text-danger bg-danger" element="div"/>
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
                <%-- /Delete Student Form--%>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="cancel"/></button>
                <c:if test="${subsection_students}">
                    <button id="deleteStudentFormConfirmAction" type="button" class="btn btn-danger"><spring:message code="confirm"/></button>
                </c:if>
            </div>
        </div>
    </div>
</div>

<%--  /Confirmation Modal --%>