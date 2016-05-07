<%@ include file="tags.jsp" %>

<%--@elvariable id="section" type="ar.edu.itba.paw.webapp.controllers"--%>
<c:choose>
    <c:when test="${section=='admins'}">
        <c:set var="adminsActive" value="active" scope="request"/>
    </c:when>
    <c:when test="${section=='students'}">
        <c:set var="studentsActive" value="active" scope="request"/>
    </c:when>
    <c:when test="${section=='courses'}">
        <c:set var="coursesActive" value="active" scope="request"/>
    </c:when>
    <c:when test="${section=='info'}">
        <c:set var="infoActive" value="active" scope="request"/>
    </c:when>
    <c:when test="${section=='edit'}">
        <c:set var="editActive" value="active" scope="request"/>
    </c:when>
    <c:when test="${section=='studentsAction'}">
        <c:set var="studentsActionActive" value="active" scope="request"/>
    </c:when>
    <c:when test="${section=='addCorrelative'}">
        <c:set var="addCorrelativeActive" value="active" scope="request"/>
    </c:when>
</c:choose>