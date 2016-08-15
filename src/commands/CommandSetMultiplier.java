package commands;

import dao.AbstractDAOFactory;
import manager.Config;
import org.apache.log4j.Logger;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CommandSetMultiplier implements ICommand {
    private static final Logger logger = Logger.getLogger(CommandSetMultiplier.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = Config.getInstance().getProperty(Config.BOOKMAKER);
        HttpSession session = request.getSession(false);

        if ("bookmaker".equals(session.getAttribute("usertype"))) {
            try {
                String id = request.getParameter("id");
                String multiplier = request.getParameter("multiplier");
                String user = (String) request.getSession(false).getAttribute("user");

                if (Integer.parseInt(id) > 0
                        && Double.parseDouble(multiplier) > 0
                        && user.equals(AbstractDAOFactory.getDAOFactory().getRaceDAO().getRaceBookmaker(Integer.parseInt(id)))) {
                    AbstractDAOFactory.getDAOFactory().getRaceDAO().setMultiplier(Integer.parseInt(id), Double.parseDouble(multiplier));
                    session.setAttribute("send", "do");
                    logger.info("Multiplier was successfully set to " + multiplier + " by " + user);
                }
                session.setAttribute("races", AbstractDAOFactory.getDAOFactory().getRaceDAO().getAllRaces());

            } catch (NumberFormatException e) {
                logger.warn("NumberFormatException in CommandSetMultiplier: " + e);
            }
        }

        return page;
    }
}
