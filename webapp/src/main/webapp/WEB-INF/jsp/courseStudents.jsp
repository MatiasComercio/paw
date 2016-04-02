<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:include page="base/head.jsp" />
    <title>${courseStudents.name}</title>
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
                        Accessing course's profile:
                    </h1>
                </div>
            </div>
            <!-- Content -->
            <div class="row">
                <h1>Accessing ${courseStudents.name}.</h1>
                <h1> Students: ${courseStudents.students}</h1>
            </div>
            <!-- Content -->
        </div>
    </div>
</div>
</body>
</html>
