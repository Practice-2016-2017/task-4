<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>

<html>
<head>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>SRB</title>
</head>
<body>
<header>
    <h1>Title : ${title}</h1>
</header>
<section>
    <h1>Message : ${message}</h1>
</section>
<c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
    <div>Get <a href="admin">admin</a> resource.</div>
</c:if>

<c:if test="${pageContext.request.isUserInRole('ROLE_TEACHER')}">
    <div>Get <a href="teacher">teacher</a> resource.</div>
</c:if>

<c:if test="${pageContext.request.isUserInRole('ROLE_STUDENT')}">
    <div>Get <a href="student">student</a> resource.</div>
</c:if>

<c:if test="${pageContext.request.userPrincipal.name != null}">
<h2>Welcome : ${pageContext.request.userPrincipal.name}
    | <c:url value="login?logout" var="logoutUrl" />
    <a href="${logoutUrl}">Log Out</a>
</h2>
</c:if>

<c:if test="${pageContext.request.userPrincipal.name == null}">
<h2> <c:url value="login" var="loginUrl" />
    <a href="${loginUrl}">Log In</a>
</h2>
</c:if>

</body>
</html>

