package commands;

import dao.AbstractDAOFactory;
import manager.Config;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CommandAddBet implements ICommand {
    private static final Logger logger = Logger.getLogger(CommandAddBet.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = Config.getInstance().getProperty(Config.CLIENT);
        HttpSession session = request.getSession(false);

        if ("client".equals(session.getAttribute("usertype"))) {
            String login = (String) session.getAttribute("user");
            int race = Integer.parseInt(request.getParameter("race"));
            int rider = Integer.parseInt(request.getParameter("rider"));
            double amount = Double.parseDouble(request.getParameter("amount"));

            if (AbstractDAOFactory.getDAOFactory().getRaceDAO().doesExist(race)
                    && !AbstractDAOFactory.getDAOFactory().getRaceDAO().hasWinner(race)
                    && AbstractDAOFactory.getDAOFactory().getUserDAO().getBalanceByLogin(login) >= amount) {
                AbstractDAOFactory.getDAOFactory().getBetDAO().addBet(login, race, rider, amount);
                session.setAttribute("send", "do");
                logger.info("The bet was made by " + login);
            }
            session.setAttribute("bets", AbstractDAOFactory.getDAOFactory().getBetDAO().getBetsByUser(login));
            session.setAttribute("races", AbstractDAOFactory.getDAOFactory().getRaceDAO().getAllRaces());
            session.setAttribute("balance", AbstractDAOFactory.getDAOFactory().getUserDAO().getBalanceByLogin(login));

        } else {
            page = Config.getInstance().getProperty(Config.LOGIN);
        }
        return page;
    }
}
