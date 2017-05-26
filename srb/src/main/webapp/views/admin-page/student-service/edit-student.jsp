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
    <title>Редактировать студента</title>
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
        <h2 >Изменение данных</h2>
        <form:form action="${pageContext.request.contextPath}/admin/studentsList/edit/${student.id}" method="post" >
            <c:if test = "${error !=null}">
                <div class="alert alert-danger" style="width: 285px; margin: 0px auto;" role="alert">
                    <p>${error}</p>
                </div>
            </c:if>


            <form class="form-horizontal">
            <div class="form-group">
            <input class="form-control" type="text" name="studentName" value="${student.name}" placeholder="Имя" required />
            </div>
            <div class="form-group">

            <input class="form-control" type="text" pattern="[1-9]{1}[0-9]{5}" name="login" value="${student.login}" placeholder="Login" required/>
            </div>
            <div class="form-group">
            <input class="form-control" type="text" name="password" pattern="[A-Za-z0-9]{5,10}" value="${student.password}" placeholder="Password" required/>
            </div>

                     <p><div><select name="year" class="form-control">
                        <option value="${student.year.name}"selected>${student.year.name}</option>

                        <c:forEach var="value" begin="1" end="4">
                            <c:if test = "${student.year.name !=value}">
                                <option  value="${value}">${value} </option>
                            </c:if>
                        </c:forEach>

                </select></div><p>


            <div class="form-group">
                <input class="btn btn-primary btn-info" type="submit" value="Подтвердить" />
            </div>
            </form>
        </form:form>


        <p>Ваш логин: <sec:authentication property="principal.username" /></p>
        <p><a href="<c:url value="/admin/studentsList" />" class="btn btn-primary btn-lg" role="button">Назад</a>
        <a class="btn btn-lg btn-danger" href="<c:url value="/logout" />" role="button">Выйти</a></p>

    </div>
</div>

</body>
</html>
