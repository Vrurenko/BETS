package commands;

import manager.Config;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class CommandLogOut implements ICommand {
    private static final Logger logger = Logger.getLogger(CommandLogOut.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = Config.getInstance().getProperty(Config.LOGIN);
        HttpSession session = request.getSession(false);
        String user = (String) session.getAttribute("user");
        session.removeAttribute("user");
        session.removeAttribute("usertype");
        session.removeAttribute("language");
        session.invalidate();
        logger.info(user + "`s session was interrupted");
        return page;
    }
}
