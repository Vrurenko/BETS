<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : sessionScope.language}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="dictionaries.lang"/>

<!DOCTYPE html>

    <html lang="${language}">
        <head>
            <title> <fmt:message key="bookmaker.title"/></title>
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

            <div>
                <div class="col-md-3">
                    <ul class="nav nav-pills">
                        <li class="active">
                            <a>
                                ${sessionScope.user} (${sessionScope.usertype})
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="col-md-offset-8 col-md-1">
                    <form name="logOutForm" method="POST" action="Controller">
                        <input type="hidden" name="command" value ="logOut"/>
                        <input type ="submit" value="<fmt:message key="bookmaker.logout"/>" class="btn btn-danger">
                    </form>
                </div>
            </div>
            <div class="col-md-12">
                <br>
                <br>
                <div class="col-md-6">
                    <br>
                    <form name="setMultiplierForm" method="POST" action="Controller" class="form-horizontal" role="form">
                        <input type="hidden" name="command" value ="setMultiplier"/>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="id"><fmt:message key="bookmaker.race"/></label>
                            <div class="col-sm-9">
                                <input type="text" name="id" id="id" required pattern="^[0-9]+$" autocomplete="off"  class="form-control">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="control-label col-sm-3" for="multiplier"><fmt:message key="bookmaker.multiplier"/></label>
                            <div class="col-sm-9">
                                <input type="text" name="multiplier" id="multiplier" required pattern="\d+(\.\d{0-2})?" autocomplete="off"  class="form-control">
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" class="btn btn-success pull-right"><fmt:message key="bookmaker.announce"/></button>
                            </div>
                        </div>

                    </form>
                </div>
                <div class="col-md-6">

                    <div class="panel panel-default">
                        <div class="panel-heading"><h4 class="text-center"><fmt:message key="bookmaker.raceslist"/></h4></div>
                        <table class="table table-hover">
                            <th>ID</th>
                            <th><fmt:message key="bookmaker.table.bookmaker"/></th>
                            <th><fmt:message key="bookmaker.table.admin"/></th>
                            <th><fmt:message key="bookmaker.table.multiplier"/></th>
                            <th><fmt:message key="bookmaker.table.winner"/></th>
                            <c:forEach items="${sessionScope.races}" var="item">
                                <tr>
                                    <td>${item.id}</td>
                                    <td>${item.bookmaker}</td>
                                    <td>${item.admin}</td>
                                    <td>${(item.multiplier == null) ? "---" : item.multiplier}</td>
                                    <td>${(item.winner == null) ? "---" : item.winner}</td>
                                </tr>
                            </c:forEach>

                        </table>
                    </div>

                </div>
            </div>

        </div>


        <script   src="https://code.jquery.com/jquery-2.2.4.min.js"   integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="   crossorigin="anonymous"></script>
        <script src="js/bootstrap.min.js"></script>
        </body>
    </html>
