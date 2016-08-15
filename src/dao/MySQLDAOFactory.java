package dao;

import dao.entity.UserTypeDAO;
import dao.interfaces.IBetDAO;
import dao.interfaces.IRaceDAO;
import dao.interfaces.IUserDAO;
import dao.entity.BetDAO;
import dao.entity.RaceDAO;
import dao.entity.UserDAO;
import dao.interfaces.IUserTypeDAO;


public class MySQLDAOFactory extends AbstractDAOFactory {

    @Override
    public IUserDAO getUserDAO() {
        return new UserDAO();
    }

    @Override
    public IBetDAO getBetDAO() {
        return new BetDAO();
    }

    @Override
    public IRaceDAO getRaceDAO() {
        return new RaceDAO();
    }

    @Override
    public IUserTypeDAO getUserTypeDAO() {
        return new UserTypeDAO();
    }

}
