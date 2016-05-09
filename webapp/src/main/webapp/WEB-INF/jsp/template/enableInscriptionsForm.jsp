<%@include file="../base/tags.jsp"%>
<%-- Confirmation Modal --%>

<!-- Modal -->
<div class="modal fade" id="enableInscriptionsConfirmationModal" tabindex="-1" role="dialog" aria-labelledby="enableInscriptionsConfirmationModal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">
                    ${confirm_action_message}
                </h4>
            </div><%-- Confirmation Form --%>
            <c:url var="confirm_action_url" value="${confirm_action_url}"/>
            <form:form class="form-horizontal" id="enableInscriptionsForm" action="${confirm_action_url}" method="post" enctype="application/x-www-form-urlencoded">

            </form:form>
            <%-- /Confirmation Form--%>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="cancel"/></button>
                <button id="enableInscriptionsConfirmAction" type="button" class="btn btn-info"><spring:message code="confirm"/></button>
            </div>
        </div>
    </div>
</div>

<%--  /Confirmation Modal --%>