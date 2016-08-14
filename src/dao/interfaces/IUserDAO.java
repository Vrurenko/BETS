package dao.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

public interface IUserDAO {

    boolean checkUserByLoginAndPassword(String login, String password) throws SQLException;
    boolean checkUserByLogin(String login) throws SQLException;
    boolean addUser(String userName, String password, String userType) throws SQLException;
    int getIdByLogin(String login);
    String getLoginById(int id);
    double getBalanceByLogin(String login);
    boolean addBalanceByLogin(String login, double amount);
    boolean isBookmaker(String login) throws SQLException;
    boolean isAdmin(String login) throws SQLException;
    ArrayList<String> getEmailList();
}
