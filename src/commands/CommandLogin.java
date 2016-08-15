package commands;

import dao.AbstractDAOFactory;
import manager.Config;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CommandLogin implements ICommand {
    private static final Logger logger = Logger.getLogger(CommandLogin.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
        HttpSession session = request.getSession(false);

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String type = AbstractDAOFactory.getDAOFactory().getUserTypeDAO().getTypeByLogin(login);

        if (AbstractDAOFactory.getDAOFactory().getUserDAO().checkUserByLoginAndPassword(login, password)) {
            logger.info("The user " + login + " has been successfully authorized");
            session.setAttribute("user", login);
            session.setAttribute("usertype", type);
            session.setAttribute("send", "do");

            if (type.equals(AbstractDAOFactory.getDAOFactory().getUserTypeDAO().getTypeById(1))) {
                session.setAttribute("bets", AbstractDAOFactory.getDAOFactory().getBetDAO().getBetsByUser(login));
                session.setAttribute("races", AbstractDAOFactory.getDAOFactory().getRaceDAO().getAllRaces());
                session.setAttribute("balance", AbstractDAOFactory.getDAOFactory().getUserDAO().getBalanceByLogin(login));
                page = Config.getInstance().getProperty(Config.CLIENT);
            }
            if (type.equals(AbstractDAOFactory.getDAOFactory().getUserTypeDAO().getTypeById(2))) {
                page = Config.getInstance().getProperty(Config.ADMIN);
                session.setAttribute("races", AbstractDAOFactory.getDAOFactory().getRaceDAO().getAllRaces());
            }
            if (type.equals(AbstractDAOFactory.getDAOFactory().getUserTypeDAO().getTypeById(3))) {
                page = Config.getInstance().getProperty(Config.BOOKMAKER);
                session.setAttribute("races", AbstractDAOFactory.getDAOFactory().getRaceDAO().getAllRaces());
            }
        } else {
            request.setAttribute("error", "error");
            page = Config.getInstance().getProperty(Config.ERROR);
        }
        return page;
    }

}
