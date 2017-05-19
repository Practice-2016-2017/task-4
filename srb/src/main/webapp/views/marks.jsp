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
    <title>Оценки</title>

    <link href="<c:url value="/views/css/bootstrap.css" />" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<c:url value="/views/css/jumbotron-narrow.css" />" rel="stylesheet">

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
        <h2> ${Subject.name}, курс ${Subject.year.name}</h2>
        <table >
            <tr>
                <th>Дата</th>
                <th>Студент</th>
                <th>Оценка</th>

            </tr>

            <c:forEach var="mark" items="${allMarks}">
                <tr>
                    <td><c:out value="${mark.date}" /></td>
                    <td><c:out value="${mark.studentName()}" /></td>
                    <td><c:out value="${mark.value}" /></td>
                </tr>
            </c:forEach>
        </table>

        <form:form action="${pageContext.request.contextPath}/teacher/${pageContext.request.userPrincipal.name}/${Subject.id}/add-mark" method="get" >

            <table>
                <tbody>
                <tr>
                    <td><input type="date" name="date" /></td>
                </tr>
                <tr>
                    <td>
                        <select name="studentName">
                        <c:forEach items="${allYearStudents}" var="student">
                            <option  value="${student.name}" >${student.name} </option>
                        </c:forEach>
                    </select>
                    </td>
                </tr>
                <tr>
                    <td> <select name="mark">
                        <option  value="2" >2 </option>
                        <option  value="3" >3 </option>
                        <option  value="4" >4 </option>
                        <option  value="5" >5 </option>
                    </select>
                    </td>
                </tr>
                <tr>
                    <td><input type="submit" value="Добавить" /></td>
                    <td></td>
                </tr>
                </tbody>
            </table>
        </form:form>

        <p>Ваш логин: <sec:authentication property="principal.username" /></p>
        <p><a class="btn btn-lg btn-danger" href="<c:url value="/logout" />" role="button">Выйти</a></p>

    </div>
</div>
</body>
</html>