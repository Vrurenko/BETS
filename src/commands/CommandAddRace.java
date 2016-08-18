package commands;

import dao.AbstractDAOFactory;
import mail.Mailer;
import manager.Config;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandAddRace implements ICommand {
    private static final Logger logger = Logger.getLogger(CommandAddRace.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = Config.getInstance().getProperty(Config.ADMIN);
        HttpSession session = request.getSession(false);

        if ("administrator".equals(session.getAttribute("usertype"))) {
            String bookmaker = request.getParameter("bookmaker");
            String admin = request.getParameter("admin");

            if (AbstractDAOFactory.getDAOFactory().getUserDAO().isBookmaker(bookmaker)
                    && AbstractDAOFactory.getDAOFactory().getUserDAO().isAdmin(admin)) {
                AbstractDAOFactory.getDAOFactory().getRaceDAO().addRace(bookmaker, admin);
                session.setAttribute("send", "do");
                Mailer.informUsers("New race has been added",
                        "Dear User, inform you about the new race adding.",
                        AbstractDAOFactory.getDAOFactory().getUserDAO().getEmailList());
                logger.info("The race has been added successfully");
            }

            session.setAttribute("races", AbstractDAOFactory.getDAOFactory().getRaceDAO().getAllRaces());
        } else {
            page = Config.getInstance().getProperty(Config.LOGIN);
        }
        return page;
    }
}
