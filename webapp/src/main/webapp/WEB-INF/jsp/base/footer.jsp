<%@ include file="tags.jsp" %>

<div class="footer">
    <div class="container-fluid">
        <div class="row">
            <div class="col-xs-12 text-center">
                <p><strong><spring:message code="developedByFooter" /></strong></p>
            </div>
        </div>
    </div>
</div>

<script src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
<%-- Pushy CSS --%>
<%-- Speciall thanks to Chris Yee. Twitter user: @cmyee.--%>
<%-- See Github repo: https://github.com/christophery/pushy --%>
<script src="<c:url value="/static/pushy/js/pushy.min.js"/>"></script>