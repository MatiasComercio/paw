<%@ include file="tags.jsp" %>

<%--@elvariable id="section" type="ar.edu.itba.paw.webapp.controllers"--%>
<%--@elvariable id="section2" type="java.lang.String"--%>
<c:choose>
    <c:when test="${section=='admins'}">
        <c:set var="adminsActive" value="active" scope="request"/>
    </c:when>
    <c:when test="${section=='students'}">
        <c:set var="studentsActive" value="active" scope="request"/>
    </c:when>
    <c:when test="${section=='courses'}">
        <c:set var="coursesActive" value="active" scope="request"/>
        <c:choose>
            <c:when test="${section2=='info'}">
                <jsp:include page="../template/CorrelativeForm.jsp" />
                <jsp:include page="../template/deleteCourseForm.jsp" />
                <c:set var="infoActive" value="active" scope="request"/>
            </c:when>
            <c:when test="${section2=='edit'}">
                <c:set var="editActive" value="active" scope="request"/>
            </c:when>
            <c:when test="${section2=='students'}">
                <c:set var="studentsActive" value="active" scope="request"/>
            </c:when>
            <c:when test="${section2=='addCorrelative'}">
                <jsp:include page="../template/CorrelativeForm.jsp" />
                <c:set var="addCorrelativeActive" value="active" scope="request"/>
            </c:when>
        </c:choose>
    </c:when>
</c:choose>