<%--@elvariable id="student" type="ar.edu.itba.paw.models.users.Student"--%>
<%@ include file="base/tags.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>
        <spring:message code="webAbbreviation"/> | ${student.fullName} | <spring:message code="profile"/>
    </title>
    <jsp:include page="base/head.jsp" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div id="wrapper">

    <jsp:include page="base/sections.jsp"/>
    <jsp:include page="base/nav.jsp" />

    <div id="page-wrapper">
        <div class="container-fluid">
            <!-- Page Heading -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                        ${student.fullName} <small> - <spring:message code="profile"/></small>
                    </h1>
                </div>
            </div>

            <jsp:include page="base/alerts.jsp" />


            <!-- Content -->
            <div class="row">
                <form>
                    <div class="col-xs-12 col-md-6">
                        <div class="row">
                            <div class="form-group col-xs-6">
                                <label class="control-label">
                                    <span></span><spring:message code="docket"/>
                                </label>
                                <p class="form-control-static">${student.docket}</p>
                            </div>
                            <!-- User Data -->
                            <div class="form-group col-xs-6">
                                <%--<label for="dni">--%>
                                <label class="control-label">
                                    <span></span><spring:message code="dni"/>
                                </label>
                                <p class="form-control-static active-overflow">${student.dni}</p>
                                <%--<input type="text" class="form-control" id="dni" value="${student.dni}" readonly/>--%>
                            </div>
                            <div class="form-group col-xs-6">
                                <%--<label for="firstName">--%>
                                <label class="control-label">
                                    <spring:message code="firstName"/>
                                </label>
                                <%--<input type="text" class="form-control" id="firstName" value="${student.firstName}" readonly/>--%>
                                <p class="form-control-static active-overflow">${student.firstName}</p>
                            </div>
                            <div class="form-group col-xs-6">
                                <%--<label for="lastName">--%>
                                <label class="control-label">
                                    <spring:message code="lastName"/>
                                </label>
                                <%--<input type="text" class="form-control" id="lastName"  value="${student.lastName}" readonly/>--%>
                                <p class="form-control-static active-overflow">${student.lastName}</p>
                            </div>
                            <div class="form-group col-xs-6">
                                <%--<label for="birthday">--%>
                                <label class="control-label">
                                    <spring:message code="birthday"/>
                                </label>
                                <spring:message code="birthdayPlaceholder" var="birthdayPlaceholder"/>
                                <%--<input id="birthday" type="text" class="form-control"  value="${student.birthday}" readonly/>--%>
                                <p class="form-control-static active-overflow">${student.birthday}</p>
                            </div>
                            <%-- for future use --%>
                            <div class="form-group col-xs-6">
                                <%--<label for="email">--%>
                                <label class="control-label">
                                    <spring:message code="email"/>
                                </label>
                                <%--<input type="text" class="form-control" id="email" value="${student.email}" readonly/>--%>
                                <p class="form-control-static active-overflow">${student.email}</p>
                            </div>
                            <div class ="form-group col-xs-12 col-md-6">
                                <%--<label for="genre">--%>
                                <label class="control-label">
                                    <spring:message code="genre"/>
                                </label>
                                <p class="form-control-static active-overflow"><spring:message code="${student.genre}"/></p>
                                <%--<input type="text" class="form-control" id="genre" value="<spring:message code="${student.genre}"/>" readonly/>--%>
                            </div>
                            <div class="form-group col-xs-12 col-md-6">
                                <%--<label for="telephone">--%>
                                <label class="control-label">
                                    <spring:message code="telephone"/>
                                </label>
                                <p class="form-control-static active-overflow">${student.address.telephone}</p>
                                <%--<input type="text" class="form-control" id="telephone"  value="${student.address.telephone}" readonly/>--%>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-md-6">
                        <div class="row">
                            <!-- Address Data -->
                            <div class="form-group col-xs-12">
                                <%--<label for="country">--%>
                                <label class="control-label">
                                    <spring:message code="country"/>
                                </label>
                                <p class="form-control-static active-overflow">${student.address.country}</p>

                                <%--<input type="text" class="form-control" id="country"  value="${student.address.country}" readonly/>--%>
                            </div>
                            <div class="form-group col-xs-6">
                                <%--<label for="city">--%>
                                <label class="control-label">
                                    <spring:message code="city"/>
                                </label>
                                <p class="form-control-static active-overflow">${student.address.city}</p>

                                <%--<input type="text" class="form-control" id="city"  value="${student.address.city}" readonly/>--%>
                            </div>
                            <div class="form-group col-xs-6">
                                <%--<label for="neighborhood">--%>
                                <label class="control-label">
                                    <spring:message code="neighbourhood"/>
                                </label>
                                <p class="form-control-static active-overflow">${student.address.neighborhood}</p>

                                <%--<input type="text" class="form-control" id="neighborhood"  value="${student.address.neighborhood}" readonly/>--%>
                            </div>
                            <div class="form-group col-xs-6">
                                <%--<label for="street">--%>
                                <label class="control-label">
                                    <spring:message code="street"/>
                                </label>
                                <p class="form-control-static active-overflow">${student.address.street}</p>

                                <%--<input type="text" class="form-control" id="street"  value="${student.address.street}" readonly/>--%>
                            </div>
                            <div class="form-group col-xs-6">
                                <%--<label for="number">--%>
                                <label class="control-label">
                                    <spring:message code="number"/>
                                </label>
                                <p class="form-control-static active-overflow">${student.address.number}</p>

                                <%--<input type="text" class="form-control" id="number"  value="${student.address.number}" readonly/>--%>
                            </div>
                            <div class="form-group col-xs-4">
                                <%--<label for="floor">--%>
                                <label class="control-label">
                                    <spring:message code="floor"/>
                                </label>
                                <p class="form-control-static active-overflow">${student.address.floor}</p>

                                <%--<input type="text" class="form-control" id="floor"  value="${student.address.floor}" readonly/>--%>
                            </div>
                            <div class="form-group col-xs-4">
                                <%--<label for="door">--%>
                                <label class="control-label">
                                    <spring:message code="door"/>
                                </label>
                                <p class="form-control-static active-overflow">${student.address.door}</p>

                                <%--<input type="text" class="form-control" id="door"  value="${student.address.door}" readonly/>--%>
                            </div>
                            <div class="form-group col-xs-4">
                                <%--<label for="zipCode">--%>
                                <label class="control-label">
                                    <spring:message code="zipCode"/>
                                </label>
                                <p class="form-control-static active-overflow">${student.address.zipCode}</p>

                                <%--<input type="text" class="form-control" id="zipCode"  value="${student.address.zipCode}" readonly/>--%>
                            </div>
                        </div>
                    </div>
                    <!-- End Data Input -->
                </form>
            </div>
            <!-- Content -->
        </div>
    </div>
    <jsp:include page="base/footer.jsp" />
</div>
<!-- Scripts -->
<jsp:include page="base/scripts.jsp" />
</body>
</html>
