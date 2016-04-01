<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Student ${student.docket}</title>
</head>
<body>
<h1>Student Information</h1>
<dl>
    <dt>Docket</dt>
    <dd>${student.docket}</dd>
    <dt>DNI</dt>
    <dd>${student.dni}</dd>
    <dt>First Name</dt>
    <dd>${student.firstName}</dd>
    <dt>Last Name</dt>
    <dd>${student.lastName}</dd>
    <dt>Genre</dt>
    <dd>${student.genre}</dd>
    <dt>Birthday</dt>
    <dd>${student.birthday}</dd>
    <dt>Email</dt>
    <dd>${student.email}</dd>
</dl>
</body>
</html>
