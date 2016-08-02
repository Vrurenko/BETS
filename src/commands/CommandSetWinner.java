package commands;

import dao.AbstractDAOFactory;
import manager.Config;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class CommandSetWinner implements ICommand {
    private static final Logger logger = Logger.getLogger(CommandSetWinner.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = Config.getInstance().getProperty(Config.ADMIN);
        String id = request.getParameter("race");
        String winner = request.getParameter("winner");
        String user = (String) request.getSession(false).getAttribute("user");
        HttpSession session = request.getSession(false);

        try {
            if (Integer.parseInt(id)>0
                    && Integer.parseInt(winner)>0
                    && user.equals(AbstractDAOFactory.getDAOFactory().getRaceDAO().getRaceAdmin(Integer.parseInt(id)))) {
                AbstractDAOFactory.getDAOFactory().getRaceDAO().setWinner(Integer.parseInt(id), Integer.parseInt(winner));
                session.setAttribute("send","do");
                logger.info("Winner " + winner + " was successfully announced");
            }
            session.setAttribute("races",AbstractDAOFactory.getDAOFactory().getRaceDAO().getAllRaces());
        } catch (NumberFormatException e) {
            logger.warn("NumberFormatException in CommandSetWinner: " + e);
        }
        return page;
    }
}
