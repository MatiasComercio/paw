<%@ include file="tags.jsp" %>

<%--@elvariable id="includeScripts" type="java.util.List"--%>
<c:forTokens items="${includeScripts}" var="script" delims="`">
    <script type="text/javascript" charset="UTF-8">
        ${script}
    </script>
</c:forTokens>

<script>
    $(document).ready(function() {
        <%--@elvariable id="loadScripts" type="java.util.List"--%>
        <c:forTokens items="${loadScripts}" var="loadScript" delims="`">
        ${loadScript}
        </c:forTokens>
    });
</script>
