package controller;

import commands.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * Class-helper to servlet-class Controller
 */
class ControllerHelper {
    private static ControllerHelper instance = null;
    private HashMap<String, ICommand> commands = new HashMap<>();

    /**
     * Constructor, that puts into map couples key-value, where
     * key - hidden parameter in the request,
     * value - instance of appropriate command
     */
    private ControllerHelper(){
        commands.put("registration", new CommandRegistration());
        commands.put("login", new CommandLogin());
        commands.put("setMultiplier", new CommandSetMultiplier());
        commands.put("setWinner", new CommandSetWinner());
        commands.put("addBet", new CommandAddBet());
        commands.put("logOut", new CommandLogOut());
        commands.put("addRace",new CommandAddRace());
        commands.put("toRegistration", new CommandToRegistration());
    }

    /**
     * Method to recognize the type of command by the hidden parameter in the request.
     * @param request object that contains the request the client has made of the servlet.
     * @return instance of recognized command.
     */
    ICommand getCommand(HttpServletRequest request){
        ICommand command = commands.get(request.getParameter("command"));
        if (command == null){
            command = new CommandMissing();
        }
        return command;
    }

    /**
     * Returns the instance of singleton-class
     * @return the instance of singleton-class if it already created or creates it
     */
    public static ControllerHelper getInstance(){
        if (instance == null){
            instance = new ControllerHelper();
        }
        return instance;
    }
}
