<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>
    <jsp:include page="base/head.jsp" />
    <title>Course ${course.name}</title>
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
                    <dl>
                        <dt>ID:</dt>
                        <dd>${course.id}</dd>
                        <dt>Course Name:</dt>
                        <dd>${course.name}</dd>
                        <dt>Credits:</dt>
                        <dd>${course.credits}</dd>
                    </dl>
                </div>
                <!-- Content -->
            </div>
        </div>
    </div>
</body>
</html>
