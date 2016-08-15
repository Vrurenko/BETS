package dao.interfaces;

import java.util.ArrayList;

public interface IUserDAO {

    boolean checkUserByLoginAndPassword(String login, String password);

    boolean checkUserByLogin(String login);

    boolean addUser(String userName, String password, String userType);

    int getIdByLogin(String login);

    String getLoginById(int id);

    double getBalanceByLogin(String login);

    boolean addBalanceByLogin(String login, double amount);

    boolean isBookmaker(String login);

    boolean isAdmin(String login);

    ArrayList<String> getEmailList();
}
