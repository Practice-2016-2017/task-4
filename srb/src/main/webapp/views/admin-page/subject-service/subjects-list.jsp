<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Список предметов</title>
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
        <h2>Список предметов</h2>
        <table class="table table-bordered">
            <tr>
                <th>Предмет</th>
                <th>Курс</th>
                <th>Преподаватель</th>

            </tr>

            <c:forEach var="subject" items="${allSubjects}">
                <tr>
                    <td><c:out value="${subject.name}" /></td>

                    <td><c:out value="${subject.year.name}" /></td>

                    <c:if test = "${subject.teacher !=null}">
                    <td><c:out value="${subject.teacher.name}" /></td>
                    </c:if>

                    <c:if test = "${subject.teacher ==null}">
                        <td><c:out value="Отсутствует" /></td>
                    </c:if>

                    <td>  <a href="<spring:url value="/admin/subjectsList/edit/${subject.id}" />">Редактировать</a> </td>

                    <td>  <a href="<spring:url value="/admin/subjectsList/delete/${subject.id}" />">Удалить</a> </td>
                </tr>
            </c:forEach>
        </table>
        <a href="<spring:url value="/admin/subjectsList/addSubject" />">Добавить предмет</a>

        <p>Ваш логин: <sec:authentication property="principal.username" /></p>
        <p><a href="<c:url value="/admin" />" class="btn btn-primary btn-lg" role="button">Назад</a>
        <a class="btn btn-lg btn-danger" href="<c:url value="/logout" />" role="button">Выйти</a></p>

    </div>
</div>

</body>
</html>