<%@ include file="tags.jsp" %>

<%--@elvariable id="section" type="ar.edu.itba.paw.webapp.controllers"--%>
<%--@elvariable id="section2" type="java.lang.String"--%>

<%-- WEB-INF files cannot be load with <script src="..."></script> (static ones can).
That's why we are usig <%@include ...%>--%>
<c:set var="includeScripts" value="" />
<c:set var="loadScripts" value="" />
<c:choose>





    <c:when test="${section=='admins'}">
        <c:set var="adminsActive" value="active" scope="request"/>
    </c:when>






    <c:when test="${section=='students'}">
        <c:set var="studentsActive" value="active" scope="request"/>
    </c:when>







    <c:when test="${section=='courses'}">
        <c:set var="coursesActive" value="active" scope="request"/>
        <jsp:include page="../template/deleteCourseForm.jsp" />
        <c:set var="includeScripts" >
             ${includeScripts} ` <%@include file='/WEB-INF/js/template/deleteCourseForm.js'%>
        </c:set>
        <c:set var="loadScripts">
            ${loadScripts} ` loadDeleteCourseForm("deleteCourseButton");
        </c:set>

        <c:choose>
            <c:when test="${section2=='info'}">
                <c:set var="infoActive" value="active" scope="request"/>
                <jsp:include page="../template/CorrelativeForm.jsp" />
                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file='/WEB-INF/js/template/CorrelativeForm.js'%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} ` loadCorrelativeForm("deleteCorrelativeButton");
                </c:set>
            </c:when>

            <c:when test="${section2=='edit'}">
                <c:set var="editActive" value="active" scope="request"/>
                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file='/WEB-INF/js/cancelButton.js'%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} `
                    loadCancelButton("cancelButton");
                </c:set>
            </c:when>

            <c:when test="${section2=='students'}">
                <c:set var="studentsActive" value="active" scope="request"/>
            </c:when>
            <c:when test="${section2=='addCorrelative'}">
                <c:set var="addCorrelativeActive" value="active" scope="request"/>
                <jsp:include page="../template/CorrelativeForm.jsp" />
                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file='/WEB-INF/js/template/CorrelativeForm.js'%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} ` loadCorrelativeForm("correlativeButton");
                </c:set>
            </c:when>
        </c:choose>
    </c:when>
</c:choose>
<c:set var="includeScripts" value="${includeScripts}" scope="request" />
<c:set var="loadScripts" value="${loadScripts}" scope="request" />
<%--<c:set var="includeScripts">
    &lt;%&ndash;${coursesScripts}&ndash;%&gt;
    Hola
</c:set>
<c:set var="includeScripts">
    ${includeScripts},
    Como, Estas
    &lt;%&ndash;${coursesInfoScripts},&ndash;%&gt;
    &lt;%&ndash;${coursesEditScripts},&ndash;%&gt;
    &lt;%&ndash;${coursesStudent},&ndash;%&gt;
    &lt;%&ndash;${coursesAddCorrelativeScript}&ndash;%&gt;
</c:set>
<c:forEach items="${includeScripts}" var="script">
    ${script}
</c:forEach>--%>
