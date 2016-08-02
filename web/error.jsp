<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : sessionScope.language}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="dictionaries.text"/>
<%@ taglib uri= "/WEB-INF/hello.tld" prefix="own" %>


<!DOCTYPE html>
<html lang="${language}">
<head>
	<title> <fmt:message key="error.text.title"/> </title>
	<link rel="stylesheet" href="css/bootstrap.min.css">
</head>
<body>
<div class="container">
	<nav class="navbar navbar-default navbar-inverse" role="navigation">

		<div class="navbar-header pull-left">
			<a class="navbar-brand" href="/">BETS</a>
		</div>

		<div class="navbar-header pull-right">
			<form method="GET" class="navbar-form navbar-right  ">
				<select id="language" name="language" onchange="submit()" class="form-control">
					<option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
					<option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
					<option value="uk" ${language == 'uk' ? 'selected' : ''}>Українська</option>
				</select>
			</form>
		</div>
	</nav>
	<div class="jumbotron">
		<%
			if (request.getAttribute("error") != null){
		%>
		<h1 class="text-center">
			<own:hello name="user"/>
			<fmt:message key="error.text.error"/>
		</h1>
		<%
		} else {
		%>
		<h1 class="text-center">
			<own:hello name="user"/>
			<fmt:message key="error.text.unknown"/>
		</h1>
		<%
			}
		%>
	</div>
</div>


<script   src="https://code.jquery.com/jquery-2.2.4.min.js"   integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="   crossorigin="anonymous"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>