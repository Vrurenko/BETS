package commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Comandante on 08.07.2016.
 */
public interface ICommand {

    String execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException;

}
