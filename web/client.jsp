<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : sessionScope.language}" scope="session" />
<fmt:setLocale value="${language}" /><fmt:setBundle basename="dictionaries.lang"/>

<!DOCTYPE html>
<html lang="${language}">
<head>
    <title>
        <fmt:message key="client.title"/>
    </title>
    <link rel="stylesheet" href="css/bootstrap.min.css"></head>
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
                <div class="col-sm-3">
                    <ul class="nav nav-pills nav-stacked">
                        <li class="active">
                            <a>
                                <span class="badge pull-right">${balance}</span>
                                ${sessionScope.user} (${sessionScope.usertype})
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="col-sm-offset-8 col-sm-1">
                    <form name="logOutForm" method="POST" action="Controller">
                        <input type="hidden" name="command" value ="logOut"/>

                        <input type ="submit" value="<fmt:message key="client.logout"/>" class="btn btn-danger">
                    </form>
                </div>
            </div>
            <br/>
            <br/>
            <br/>

            <div class="col-sm-offset-2">
                <form class="form-inline" role="form" name="addBetForm" method="POST" action="Controller">
                    <input type="hidden" name="command" value ="addBet"/>

                    <div class="form-group">
                        <input type="number" class="form-control"  placeholder="<fmt:message key="client.placeholder.race"/>" name="race" required min="1" autocomplete="off">
                    </div>

                    <div class="form-group">
                        <input type="text" class="form-control"  placeholder="<fmt:message key="client.placeholder.rider"/>" name ="rider" required pattern="[0-9]{2}"  autocomplete="off">
                    </div>

                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="<fmt:message key="client.placeholder.amount"/>"  name ="amount"  required pattern="\d+(\.\d{0-2})?" autocomplete="off">
                    </div>

                    <button type="submit" class="btn btn-success"><fmt:message key="client.button.setbet"/></button>
                </form>
            </div>
            <br>


        <div class="jumbotron container" style="padding-top: 0;">
            <div class="panel panel-default">
                <div class="col-md-6">
                    <div class="panel-heading"><p class="text-center"><fmt:message key="client.table.userbets"/></p></div>
                    <table class="table table-hover">
                        <th>ID</th>
                        <th><fmt:message key="client.table.client"/></th>
                        <th><fmt:message key="client.table.race"/></th>
                        <th><fmt:message key="client.table.rider"/></th>
                        <th><fmt:message key="client.table.amount"/></th>
                        <th><fmt:message key="client.table.result"/></th>
                        <c:forEach items="${sessionScope.bets}" var="item">
                            <tr>
                                <td>${item.id}</td>
                                <td>${item.client}</td>
                                <td>${item.race}</td>
                                <td>${item.rider}</td>
                                <td>${item.amount}</td>
                                <td>${(item.result == null) ? "---" : item.result}</td>
                            </tr>
                        </c:forEach>
                        <%--<%--%>
                        <%--session.removeAttribute("bets");--%>
                        <%--%>--%>
                    </table>
                </div>

                <div class="col-md-6">
                    <div class="panel-heading"><p class="text-center"><fmt:message key="client.table.availableraces"/></p></div>
                    <table class="table table-hover">
                        <th>ID</th>
                        <th><fmt:message key="client.table.bookmaker"/></th>
                        <th><fmt:message key="client.table.admin"/></th>
                        <th><fmt:message key="client.table.multiplier"/></th>
                        <th><fmt:message key="client.table.winner"/></th>
                        <c:forEach items="${sessionScope.races}" var="item">
                            <tr>
                                <td>${item.id}</td>
                                <td>${item.bookmaker}</td>
                                <td>${item.admin}</td>
                                <td>${(item.multiplier == null) ? "---" : item.multiplier}</td>
                                <td>${(item.winner == null) ? "---" : item.winner}</td>
                            </tr>
                        </c:forEach>
                        <%--<%--%>
                            <%--session.removeAttribute("races");--%>
                        <%--%>--%>
                    </table>
                </div>
            </div>

        </div>
        </div>
        </div>

        <script   src="https://code.jquery.com/jquery-2.2.4.min.js"   integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="   crossorigin="anonymous"></script>
        <script src="js/bootstrap.min.js"></script>
        </body>
    </html>
