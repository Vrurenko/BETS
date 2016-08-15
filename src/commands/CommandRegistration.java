package commands;

import dao.AbstractDAOFactory;
import mail.Validator;
import manager.Config;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class CommandRegistration implements ICommand {
    private static final Logger logger = Logger.getLogger(CommandRegistration.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = null;
        HttpSession session = request.getSession(false);

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String usertype = request.getParameter("usertype");
        String email = null;
        if (new Validator().validate(request.getParameter("email"))){
            email = request.getParameter("email");
        }

        if (AbstractDAOFactory.getDAOFactory().getUserDAO().addUser(login, password, usertype, email)) {
            logger.info("User " + login + " was successfully added");

            session.setAttribute("user", login);
            session.setAttribute("usertype", usertype);
            session.setAttribute("send", "do");

            if (usertype.equals(AbstractDAOFactory.getDAOFactory().getUserTypeDAO().getTypeById(1))) {
                session.setAttribute("bets", AbstractDAOFactory.getDAOFactory().getBetDAO().getBetsByUser(login));
                session.setAttribute("balance", AbstractDAOFactory.getDAOFactory().getUserDAO().getBalanceByLogin(login));
                session.setAttribute("races", AbstractDAOFactory.getDAOFactory().getRaceDAO().getAllRaces());
                page = Config.getInstance().getProperty(Config.CLIENT);
            }
            if (usertype.equals(AbstractDAOFactory.getDAOFactory().getUserTypeDAO().getTypeById(2))) {
                session.setAttribute("races", AbstractDAOFactory.getDAOFactory().getRaceDAO().getAllRaces());
                page = Config.getInstance().getProperty(Config.ADMIN);
            }
            if (usertype.equals(AbstractDAOFactory.getDAOFactory().getUserTypeDAO().getTypeById(3))) {
                session.setAttribute("races", AbstractDAOFactory.getDAOFactory().getRaceDAO().getAllRaces());
                page = Config.getInstance().getProperty(Config.BOOKMAKER);
            }
        } else {
            page = Config.getInstance().getProperty(Config.REGISTRATION);
        }
        return page;
    }
}
