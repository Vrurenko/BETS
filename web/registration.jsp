<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="language"
       value="${not empty param.language ? param.language : not empty language ? language : sessionScope.language}"
       scope="session"/>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="dictionaries.lang"/>
<%@ taglib uri="/WEB-INF/today.tld" prefix="ct" %>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <title><fmt:message key="registration.title"/></title>
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
            <form class="navbar-right navbar-form ">
                <select id="language" name="language" onchange="submit()" class="form-control">
                    <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
                    <option value="ru" ${language == 'ru' ? 'selected' : ''}>Русский</option>
                    <option value="uk" ${language == 'uk' ? 'selected' : ''}>Українська</option>
                </select>
            </form>
        </div>
    </nav>


    <form name="addUserForm" method="POST" action="Controller" class="form-horizontal" role="form">
        <input type="hidden" name="command" value="registration"/>

        <div class="form-group">
            <label for="login" class="col-sm-5 control-label"><fmt:message key="registration.username"/></label>
            <div class="col-sm-3">
                <input type="text" name="login" required id="login" class="form-control">
            </div>
        </div>


        <div class="form-group">
            <label for="password" class="col-sm-5 control-label"><fmt:message key="registration.password"/></label>
            <div class="col-sm-3">
                <input type="password" name="password" value="" required id="password" class="form-control">
            </div>
        </div>


        <div class="form-group">
            <label for="email" class="col-sm-5 control-label"><fmt:message key="registration.email"/></label>
            <div class="col-sm-3">
                <input type="email" name="email" value="" id="email" class="form-control">
            </div>
        </div>


        <div class="form-group">
            <label for="usertype" class="col-sm-5 control-label"><fmt:message key="registration.usertype"/></label>
            <div class="col-sm-3">
                <select name="usertype" id="usertype" class="form-control">
                    <option selected value="client"><fmt:message key="registration.client"/></option>
                    <option value="administrator"><fmt:message key="registration.administrator"/></option>
                    <option value="bookmaker"><fmt:message key="registration.bookmaker"/></option>
                </select>
            </div>
        </div>


        <div class="form-group">
            <div class="col-sm-8">
                <input type="submit" value="<fmt:message key="registration.register"/>"
                       class="btn btn-primary pull-right">
            </div>
        </div>
    </form>

</div>

<script src="https://code.jquery.com/jquery-2.2.4.min.js"
        integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44=" crossorigin="anonymous"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
