<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : sessionScope.language}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="dictionaries.lang"/>

<!DOCTYPE html>

    <html lang="${language}">
        <head>
            <title> <fmt:message key="admin.title"/></title>
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
                        <input type ="submit" value="<fmt:message key="admin.logout"/>" class="btn btn-danger">
                    </form>
                </div>
            </div>
            <br>
            <br>
            <br>
            <div>
                  <div class="col-md-6">
                      <ul class="nav nav-tabs nav-justified">
                          <li class="active"><a href="#menu1"><fmt:message key="admin.announcement.winner"/></a></li>
                          <li><a href="#menu2"><fmt:message key="admin.announcement.race"/></a></li>
                      </ul>
                      <div class="tab-content">
                          <div id="menu1" class="tab-pane fade in active">
                              <br>
                              <form name="setWinnerForm" method="POST" action="Controller" class="form-horizontal" role="form">
                                      <input type="hidden" name="command" value ="setWinner"/>

                                      <div class="form-group">
                                          <label class="control-label col-sm-3" for="race"><fmt:message key="admin.race"/></label>
                                          <div class="col-sm-9">
                                              <input type="number" name="race" id="race" required min="1" autocomplete="off"  class="form-control">
                                          </div>
                                      </div>

                                      <div class="form-group">
                                          <label class="control-label col-sm-3" for="winner"><fmt:message key="admin.winner"/></label>
                                          <div class="col-sm-9">
                                              <input type="text" name="winner" id="winner" required pattern="[0-9]{2}" class="form-control">
                                          </div>
                                      </div>

                                      <div class="form-group">
                                          <div class="col-sm-offset-2 col-sm-10">
                                              <button type="submit" class="btn btn-success pull-right"><fmt:message key="admin.announce"/></button>
                                          </div>
                                      </div>
                                  </form>
                          </div>
                          <div id="menu2" class="tab-pane fade">
                              <br>
                              <form name="addRaceForm" method="POST" action="Controller" class="form-horizontal" role="form">
                                  <input type="hidden" name="command" value ="addRace"/>

                                  <div class="form-group">
                                      <label class="control-label col-sm-3" for="admin"><fmt:message key="admin.admin"/></label>
                                      <div class="col-sm-9">
                                          <input type="text" name="admin" id="admin" required autocomplete="off"  class="form-control">
                                      </div>
                                  </div>

                                  <div class="form-group">
                                      <label class="control-label col-sm-3" for="bookmaker"><fmt:message key="admin.bookmaker"/></label>
                                      <div class="col-sm-9">
                                          <input type="text" name="bookmaker" id="bookmaker" required autocomplete="off"  class="form-control">
                                      </div>
                                  </div>

                                  <div class="form-group">
                                      <div class="col-sm-offset-2 col-sm-10">
                                          <button type="submit" class="btn btn-success pull-right"><fmt:message key="admin.announce"/></button>
                                      </div>
                                  </div>
                              </form>
                          </div>
                      </div>
                  </div>
                  <div class="col-md-6">
                      <div class="panel panel-default">
                          <div class="panel-heading"><h3 class="text-center"><fmt:message key="admin.raceslist"/></h3></div>

                          <table class="table table-hover">
                              <th>ID</th>
                              <th><fmt:message key="admin.table.bookmaker"/></th>
                              <th><fmt:message key="admin.table.admin"/></th>
                              <th><fmt:message key="admin.table.multiplier"/></th>
                              <th><fmt:message key="admin.table.winner"/></th>
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
        <script>
            $(document).ready(function(){
                $(".nav-tabs a").click(function(){
                    $(this).tab('show');
                });
            });
        </script>
        </body>
    </html>
