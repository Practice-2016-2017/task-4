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
    <title>Изменить оценку</title>

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
        <h3>Изменение оценки: ${subject.name}, курс ${subject.year.name}</h3>

        <form:form action="${pageContext.request.contextPath}/teacher/${subject.id}/edit-mark/${mark.id}" method="post" >

            <table>
                <tbody>
                <tr>
                    <td><input type="date" name="date" value="${date}" re/></td>
                </tr>
                <tr>
                    <td>
                        <select name="studentId">
                            <option value="${student.id}"selected>${student.name}</option>

                            <c:forEach items="${allYearStudents}" var="st">
                                <c:if test = "${st.id !=student.id}">
                                    <option  value="${st.id}" >${st.name} </option>
                                </c:if>
                            </c:forEach>

                        </select>
                    </td>
                </tr>
                <tr>
                    <td> <select name="mark">

                        <option value="${mark.value}"selected>${mark.value}</option>

                        <c:if test = "${mark.value !=2}">
                            <option  value="2"  >2 </option>
                        </c:if>

                        <c:if test = "${mark.value !=3}">
                            <option  value="3"  >3 </option>
                        </c:if>

                        <c:if test = "${mark.value !=4}">
                            <option  value="4"  >4 </option>
                        </c:if>

                        <c:if test = "${mark.value !=5}">
                            <option  value="5"  >5 </option>
                        </c:if>
                    </select>
                    </td>
                </tr>
                <tr>
                    <td><input type="submit" value="Подтвердить" /></td>
                </tr>
                </tbody>
            </table>
        </form:form>

        <p>Ваш логин: <sec:authentication property="principal.username" /></p>
        <p><a href="<c:url value="/teacher/${subject.id}" />" class="btn btn-primary btn-lg" role="button">Назад</a>
        <a class="btn btn-lg btn-danger" href="<c:url value="/logout" />" role="button">Выйти</a></p>

    </div>
</div>
</body>
</html>
