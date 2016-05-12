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

        <c:choose>
            <c:when test="${empty section2}">
                <jsp:include page="../template/adminsActionsPanel.jsp" />
                <jsp:include page="../template/enableInscriptionsForm.jsp" />

                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file="/WEB-INF/js/template/searchAdmins.js"%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} `
                    loadAdminSearch();
                </c:set>

                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file="/WEB-INF/js/template/enableInscriptionsForm.js"%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} `
                    loadEnableInscriptionsForm();
                </c:set>
            </c:when>
            <c:when test="${section2 eq 'addAdmin'}">
                <c:set var="addAdminActive" value="active" scope="request"/>

                <jsp:include page="../template/adminsActionsPanel.jsp" />
                <jsp:include page="../template/enableInscriptionsForm.jsp" />

                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file="/WEB-INF/js/template/searchAdmins.js"%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} `
                    loadAdminSearch();
                </c:set>

                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file="/WEB-INF/js/template/enableInscriptionsForm.js"%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} `
                    loadEnableInscriptionsForm();
                </c:set>

                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file='/WEB-INF/js/cancelButton.js'%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} `
                    loadCancelButton("cancelButton");
                </c:set>
            </c:when>
            <c:otherwise>
                <jsp:include page="../template/adminActionsPanel.jsp" />
                <jsp:include page="../template/resetPasswordForm.jsp" />
                <%--<jsp:include page="../template/deleteAdminForm.jsp" /> +++xtodo --%>

                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file="/WEB-INF/js/template/resetPasswordForm.js"%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} `
                    loadResetPasswordForm("resetPasswordButton");
                </c:set>

                <%--<c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file='/WEB-INF/js/template/deleteStudentForm.js'%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} ` loadDeleteStudentForm("deleteStudentButton");
                </c:set> +++xtodo --%>
                <c:choose>
                    <c:when test="${section2 eq 'info'}">
                        <c:set var="infoActive" value="active" scope="request"/>
                    </c:when>
                    <c:when test="${section2 eq 'edit'}">
                        <c:set var="editActive" value="active" scope="request"/>

                        <c:set var="includeScripts" >
                            ${includeScripts} ` <%@include file='/WEB-INF/js/cancelButton.js'%>
                        </c:set>
                        <c:set var="loadScripts">
                            ${loadScripts} `
                            loadCancelButton("cancelButton");
                        </c:set>
                    </c:when>

                </c:choose>
            </c:otherwise>
        </c:choose>
    </c:when>






    <c:when test="${section=='students'}">
        <c:set var="studentsActive" value="active" scope="request"/>

        <c:choose>
            <c:when test="${empty section2}">
                <jsp:include page="../template/studentsActionsPanel.jsp" />

                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file="/WEB-INF/js/template/searchStudents.js"%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} `
                    loadSearchStudents();
                </c:set>
            </c:when>

            <c:when test="${section2 eq 'addStudent'}">
                <c:set var="addStudentActive" value="active" scope="request"/>

                <jsp:include page="../template/studentsActionsPanel.jsp" />

                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file='/WEB-INF/js/cancelButton.js'%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} `
                    loadCancelButton("cancelButton");
                </c:set>
            </c:when>

            <c:otherwise>
                <jsp:include page="../template/studentActionsPanel.jsp" />
                <jsp:include page="../template/resetPasswordForm.jsp" />
                <jsp:include page="../template/deleteStudentForm.jsp" />

                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file="/WEB-INF/js/template/resetPasswordForm.js"%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} `
                    loadResetPasswordForm("resetPasswordButton");
                </c:set>

                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file='/WEB-INF/js/template/deleteStudentForm.js'%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} ` loadDeleteStudentForm("deleteStudentButton");
                </c:set>
            </c:otherwise>
        </c:choose>

        <c:if test="${not empty section2}">
            <c:choose>
                <c:when test="${section2 eq 'info'}">
                    <c:set var="infoActive" value="active" scope="request"/>
                </c:when>

                <c:when test="${section2 eq 'edit'}">
                    <c:set var="editActive" value="active" scope="request"/>

                    <c:set var="includeScripts" >
                        ${includeScripts} ` <%@include file='/WEB-INF/js/cancelButton.js'%>
                    </c:set>
                    <c:set var="loadScripts">
                        ${loadScripts} `
                        loadCancelButton("cancelButton");
                    </c:set>
                </c:when>

                <c:when test="${section2 eq 'courses'}">
                    <c:set var="coursesStudentActive" value="active" scope="request"/>


                </c:when>

                <c:when test="${section2 eq 'grades'}">
                    <c:set var="gradesActive" value="active" scope="request"/>
                    <c:set var="includeScripts" >
                        ${includeScripts} ` <%@include file="/WEB-INF/js/template/gradeForm.js"%>
                    </c:set>
                    <c:set var="loadScripts">
                        ${loadScripts} `
                        loadGradeForm("gradeButton");
                    </c:set>


                </c:when>

                <c:when test="${section2 eq 'inscription'}">
                    <c:set var="inscriptionActive" value="active" scope="request"/>


                </c:when>
            </c:choose>
        </c:if>
    </c:when>







    <c:when test="${section=='courses'}">
        <c:set var="coursesActive" value="active" scope="request"/>

        <c:choose>
            <c:when test="${empty section2}">
                <jsp:include page="../template/coursesActionsPanel.jsp" />

            </c:when>

            <c:when test="${section2 eq 'addCourse'}">
                <c:set var="addCourseActive" value="active" scope="request"/>

                <jsp:include page="../template/coursesActionsPanel.jsp" />

                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file='/WEB-INF/js/cancelButton.js'%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} `
                    loadCancelButton("cancelButton");
                </c:set>
            </c:when>

            <c:otherwise>
                <jsp:include page="../template/courseActionsPanel.jsp" />

                <jsp:include page="../template/deleteCourseForm.jsp" />
                <c:set var="includeScripts" >
                    ${includeScripts} ` <%@include file='/WEB-INF/js/template/deleteCourseForm.js'%>
                </c:set>
                <c:set var="loadScripts">
                    ${loadScripts} ` loadDeleteCourseForm("deleteCourseButton");
                </c:set>
            </c:otherwise>
        </c:choose>

        <c:if test="${not empty section2}">
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
                    <c:set var="courseStudentsActive" value="active" scope="request"/>

                    <c:set var="includeScripts" >
                        ${includeScripts} ` <%@include file="../../js/template/searchStudents.js"%>
                    </c:set>
                    <c:set var="loadScripts">
                        ${loadScripts} ` loadStudentSearch();
                    </c:set>
                </c:when>

                <c:when test="${section2=='studentsPassed'}">
                    <c:set var="courseStudentsApprovedActive" value="active" scope="request"/>

                    <c:set var="includeScripts" >
                        ${includeScripts} ` <%@include file="../../js/template/searchStudents.js"%>
                    </c:set>
                    <c:set var="loadScripts">
                        ${loadScripts} ` loadStudentSearch();
                    </c:set>
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
        </c:if>
    </c:when>
</c:choose>

<%-- Prepare variables to be loaded from other JSPs --%>
<c:set var="includeScripts" value="${includeScripts}" scope="request" />
<c:set var="loadScripts" value="${loadScripts}" scope="request" />