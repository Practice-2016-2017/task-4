<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Редактировать преподавателя</title>
    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/views/css/bootstrap.css" />" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<c:url value="/views/css/jumbotron-narrow.css" />" rel="stylesheet">

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">
    <div class="jumbotron" style="margin-top: 20px;">

        <form:form action="${pageContext.request.contextPath}/admin/teachersList/edit/${teacher.id}" method="post" >

            <table>
                <tbody>
                <tr>
                    <td><input type="text" name="teacherName" value="${teacher.name}" placeholder="Имя" required/></td>
                </tr>

                <tr>
                    <td><input type="text" name="login" value="${teacher.login}" placeholder="Login" pattern="[1-9]{1}[0-9]{5}" required/></td>
                </tr>

                <tr>
                    <td><input type="text" name="password" value="${teacher.password}" placeholder="Password" minlength="5" maxlength="10" required/></td>
                </tr>
                </tbody>
            </table>
            <input type="submit" value="Подтвердить" />
        </form:form>

        <p>Ваш логин: <sec:authentication property="principal.username" /></p>
        <p><a href="<c:url value="/admin/teachersList" />" class="btn btn-primary btn-lg active" role="button">Назад</a>
        <a class="btn btn-lg btn-danger" href="<c:url value="/logout" />" role="button">Выйти</a></p>

    </div>
</div>

</body>
</html>