<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head><jsp:include page="base/head.jsp" /></head>
<body>

<div id="wrapper">

    <jsp:include page="base/nav.jsp" />

    <div id="page-wrapper">

        <div class="container-fluid">

            <!-- Page Heading -->
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">
                        Alumnos <small>Lista de alumnos</small>
                    </h1>
                </div>
            </div>

            <!-- -->
            <div class="container">
                <jsp:include page="base/alerts.jsp" />
                <h2>Agregar alumno</h2>
                <form:form modelAttribute="studentForm" method="post" action="/app/students/add_student">
                    <!-- User Data -->
                    <div class="form-group">
                        <form:label for="dni" path="dni"><span style="color:red">*</span>DNI</form:label>
                        <form:input type="text" class="form-control" id="dni" path="dni"/>
                        <form:errors path="dni" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="firstName" path="firstName"><span style="color:red">*</span>Nombre</form:label>
                        <form:input type="text" class="form-control" id="firstName" path="firstName"/>
                        <form:errors path="firstName" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="lastName" path="lastName"><span style="color:red">*</span>Apellido</form:label>
                        <form:input type="text" class="form-control" id="lastName" path="lastName"/>
                        <form:errors path="lastName" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class ="form-group">
                        <div class ="row">
                            <div class="col-md-3">
                                <form:label path="genre">Género</form:label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <form:radiobutton path="genre" value="M" /> Masculino
                             </div>
                            <div class="col-md-3">
                                <form:radiobutton path="genre" value="F" /> Femenino
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label for="birthday" path="birthday">Cumpleaños:</form:label>
                        <form:input id="birthday" type="text" class="form-control" placeholder="yyyy-mm-dd" path="birthday"/>
                        <form:errors path="birthday" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="email" path="email">Email</form:label>
                        <form:input type="text" class="form-control" id="email" path="email"/>
                        <form:errors path="email" cssStyle="color: red;" element="div"/>
                    </div>
                    <!-- Student Data -->
                    <div class="form-group">
                        <form:label for="docket" path="docket"><span style="color:red">*</span>Legajo</form:label>
                        <form:input type="text" class="form-control" id="docket" path="docket"/>
                        <form:errors path="docket" cssStyle="color: red;" element="div"/>
                    </div>
                    <!-- Address Data -->
                    <div class="form-group">
                        <form:label for="country" path="country"><span style="color:red">*</span>País</form:label>
                        <form:input type="text" class="form-control" id="country" path="country"/>
                        <form:errors path="country" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="city" path="city"><span style="color:red">*</span>Ciudad</form:label>
                        <form:input type="text" class="form-control" id="city" path="city"/>
                        <form:errors path="city" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="neighborhood" path="neighborhood"><span style="color:red">*</span>Localidad</form:label>
                        <form:input type="text" class="form-control" id="neighborhood" path="neighborhood"/>
                        <form:errors path="neighborhood" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="street" path="street"><span style="color:red">*</span>Calle</form:label>
                        <form:input type="text" class="form-control" id="street" path="street"/>
                        <form:errors path="street" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="number" path="number"><span style="color:red">*</span>Altura</form:label>
                        <form:input type="text" class="form-control" id="number" path="number"/>
                        <form:errors path="number" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="floor" path="floor">Piso Nº</form:label>
                        <form:input type="text" class="form-control" id="floor" path="floor"/>
                        <form:errors path="floor" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="door" path="door">Dpto. Nº</form:label>
                        <form:input type="text" class="form-control" id="door" path="door"/>
                        <form:errors path="door" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="telephone" path="telephone">Teléfono</form:label>
                        <form:input type="text" class="form-control" id="telephone" path="telephone"/>
                        <form:errors path="telephone" cssStyle="color: red;" element="div"/>
                    </div>
                    <div class="form-group">
                        <form:label for="zipCode" path="zipCode">Código Postal</form:label>
                        <form:input type="text" class="form-control" id="zipCode" path="zipCode"/>
                        <form:errors path="zipCode" cssStyle="color: red;" element="div"/>
                    </div>
                    <!-- End Data Input -->
                    <input type="submit" class="btn btn-info" value="Agregar alumno"/>
                </form:form>
            </div>

            <!-- -->

        </div>
        <!-- /.container-fluid -->

    </div>
    <!-- /#page-wrapper -->

</div>
<!-- Scripts -->

<script>
    $(document).ready(function(){

        $.datepicker.setDefaults( $.datepicker.regional[ "us" ] );

        $('#birthday').datepicker({
            dateFormat: "yy-mm-dd",
            defaultDate: "1994-01-01"
        });
    });
</script>

<jsp:include page="base/footer.jsp" />
</body>
</html>