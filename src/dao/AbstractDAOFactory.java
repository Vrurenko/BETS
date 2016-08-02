package dao;

import dao.interfaces.*;

public abstract class AbstractDAOFactory {

    public abstract IUserDAO getUserDAO();
    public abstract IBetDAO getBetDAO();
    public abstract IRaceDAO getRaceDAO();
    public abstract IUserTypeDAO getUserTypeDAO();

    public static AbstractDAOFactory getDAOFactory(){
        return new MySQLDAOFactory();
    }
}
