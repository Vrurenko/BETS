<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ?  language : 'en'}"
       scope="session"/>
<fmt:requestEncoding value="utf-8"/>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="dictionaries.lang"/>
<%@ taglib uri="/WEB-INF/today.tld" prefix="ct" %>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <title>
        <fmt:message key="login.text.title"/>
    </title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
</head>

<body>

<div class="container">
    <nav class="navbar navbar-default navbar-inverse" role="navigation">

        <div class="navbar-header pull-left">
            <a class="navbar-brand" href="/">BETS</a>
        </div>
        <div class="navbar-header navbar-brand col-sm-offset-4">
            <ct:today format="dd MMMM, yyyy" language="${language}"/>
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
    <div class="col-sm-offset-8">
        <form name="toRegistrationForm" method="GET" action="Controller">
            <input type="hidden" name="command" value="toRegistration"/>
            <input class="btn btn-link" type="submit" value=<fmt:message key="login.link.registration"/>>
        </form>
    </div>
    <br>
    <div class="col-md-offset-3 col-md-6">
        <form name="loginForm" method="POST" action="Controller" class="form-horizontal" role="form">
            <input type="hidden" name="command" value="login"/>

            <div class="form-group">
                <label for="login" class="col-sm-4 control-label"><fmt:message key="login.label.username"/></label>
                <div class="col-sm-8">
                    <input type="text" name="login" required id="login" class="form-control">
                </div>
            </div>

            <div class="form-group">
                <label for="password" class="col-sm-4 control-label"><fmt:message key="login.label.password"/></label>
                <div class="col-sm-8">
                    <input type="password" name="password" value="" required id="password" class="form-control">
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-10">
                    <input class="btn btn-primary" type="submit" value=<fmt:message key="login.button.submit"/>>
                </div>
            </div>

        </form>
    </div>
</div>

<script src="https://code.jquery.com/jquery-2.2.4.min.js"
        integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
