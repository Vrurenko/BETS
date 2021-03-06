package commands;

import manager.Config;
import org.apache.log4j.Logger;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CommandMissing implements ICommand {
    private static final Logger logger = Logger.getLogger(CommandMissing.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("There is unknown command for servlet. Redirected to login page");
        HttpSession session = request.getSession();
        session.setAttribute("send", "do");
        return Config.getInstance().getProperty(Config.LOGIN);
    }
}
