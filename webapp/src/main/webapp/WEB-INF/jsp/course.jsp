<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title> ${course.name}</title>
    <jsp:include page="base/head.jsp" />
    <link href="<c:url value="/static/css/course-detail.css" />" rel="stylesheet" type="text/css"/>
</head>

<body>
    <div id="wrapper">
        <jsp:include page="base/nav.jsp" />

        <div id="page-wrapper">

            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="row">
                    <div class="col-lg-12">
                        <h1 class="page-header">
                            Información de la Materia
                            <a class="btn btn-info" href="<c:url value="/courses/${course.id}/edit"/>">Editar</a>
                        </h1>
                    </div>
                </div>
                <!-- Content -->
               <div class="row">
                   <div class="col-xs-12 col-md-6">
                       <div class="well">
                           <div class="row">

                               <div class="row">
                                   <div class="col-xs-3 right-effect">
                                       <strong>ID</strong>
                                   </div>
                                   <div class="col-xs-9">
                                       ${course.id}
                                   </div>
                               </div>
                               <div class="row">
                                   <div class="col-xs-3 right-effect">
                                       <strong>Nombre</strong>
                                   </div>
                                   <div class="col-xs-9">
                                       ${course.name}
                                   </div>
                               </div>
                               <div class="row">
                                   <div class="col-xs-3 right-effect">
                                       <strong>Créditos</strong>
                                   </div>
                                   <div class="col-xs-9">
                                       ${course.credits}
                                   </div>
                               </div>
                           </div>
                       </div>
                   </div>
                   <div class="col-xs-12 col-md-6">
                       <div class="row">
                           <div class="col-xs-12 col-md-6">
                               <a href="<c:url value="/courses/${course.id}/students" />" type="button" class="btn btn-link">Ver Alumnos Inscriptos</a>
                           </div>
                       </div>
                   </div>
               </div>

                <!-- Content -->
            </div>
        </div>
    </div>
</body>
</html>
