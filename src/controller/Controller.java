package controller;

import commands.ICommand;
import manager.Message;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Controller", urlPatterns = {"/Controller"} )
public class Controller extends HttpServlet {
    private static final Logger logger = Logger.getLogger(Controller.class);

    private ControllerHelper controllerHelper = ControllerHelper.getInstance();

    public Controller(){
        super();
    }

    /**
     * Method to handle the POST requests.
     * @param request object that contains the request the client has made of the servlet.
     * @param response object that contains the response the servlet sends to the client.
     * @throws ServletException if the request for the GET could not be handled.
     * @throws IOException if an input or output error is detected when the servlet handles the GET request.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request,response);
    }

    /**
     * Method to handle the GET requests.
     * @param request object that contains the request the client has made of the servlet.
     * @param response object that contains the response the servlet sends to the client.
     * @throws ServletException if the request for the GET could not be handled.
     * @throws IOException if an input or output error is detected when the servlet handles the GET request.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request,response);
    }

    /**
     * Method to handle both types of requests, gets the type of commands from ControllerHelper,
     * returns the page to forward or redirect.
     * @param request object that contains the request the client has made of the servlet.
     * @param response object that contains the response the servlet sends to the client.
     * @throws ServletException if the request for the GET could not be handled.
     * @throws IOException if an input or output error is detected when the servlet handles the GET request.
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");

        String page = null;
        try{
            ICommand command = controllerHelper.getCommand(request);
            logger.info("There was a " + command.getClass() + " instance returned");
            page = command.execute(request,response);
        } catch (ServletException e){
            e.printStackTrace();
            request.setAttribute("messageError", Message.getInstance().getProperty(Message.SERVLET_EXCEPTION));
        } catch (IOException e){
            e.printStackTrace();
            request.setAttribute("messageError",Message.getInstance().getProperty(Message.IO_EXCEPTION));
        }

        if (request.getSession(false) != null
                && "do".equals(request.getSession(false).getAttribute("send"))){
            response.sendRedirect(page);
            logger.info("Redirected to " + page);
        } else {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request,response);
            logger.info("Forwarded to " + page);
        }

    }
}
