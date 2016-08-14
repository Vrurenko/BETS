package dao.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import beans.User;
import dao.AbstractDAOFactory;
import dao.ConnectionPool;
import dao.interfaces.IUserDAO;
import org.apache.log4j.Logger;

public class UserDAO implements IUserDAO {
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final Logger logger = Logger.getLogger(UserDAO.class);

    /**
     * Checks user in the database according to his login and password
     * @param login user login
     * @param password user password
     * @return boolean value true or false
     * @throws SQLException
     */
    @Override
    public boolean checkUserByLoginAndPassword(String login, String password){
        Boolean result = false;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,password);
            resultSet = preparedStatement.executeQuery();
            result = resultSet.next();
        } catch (SQLException e) {
            logger.error("SQLException in checkUserByLoginAndPassword() : " + e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return result;
    }

    /**
     * Checks user in the database with the specified login
     * @param login user login
     * @return boolean value true or false
     */
    @Override
    public boolean checkUserByLogin(String login) {
        Boolean result = false;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
            preparedStatement.setString(1,login);
            resultSet = preparedStatement.executeQuery();
            result = resultSet.next();
        } catch (SQLException e) {
            logger.error("SQLException in checkUserByLogin() : " + e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return result;
    }

    /**
     * Adds new user to the database
     * @param userName String value of user nickname
     * @param password String value of user password
     * @param userType String value of user type
     * @return True value in case of successfully addition, else False
     * @throws SQLException
     */
    @Override
    public boolean addUser(String userName, String password, String userType) throws SQLException{
        boolean result = AbstractDAOFactory.getDAOFactory().getUserDAO().checkUserByLogin(userName);
        if (!result){
            try{
                double balance = 0;
                if (userType.equals("client")){
                    balance = 1000;
                }
                connection = connectionPool.getConnection();
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO user (user_type, username, password, balance) VALUES(?,?,?,?);");
                preparedStatement.setInt(1,AbstractDAOFactory.getDAOFactory().getUserTypeDAO().getIdByType(userType));
                preparedStatement.setString(2,userName);
                preparedStatement.setString(3,password);
                preparedStatement.setDouble(4,balance);
                preparedStatement.execute();
            } catch (SQLException e){
                logger.error("SQLException in addUser() : " + e);
            }
        }
        return !result;
    }


    /**
     * Returns primary key of DB record? that contains the specified user login
     * @param login String value of user login
     * @return integer value of primary key
     */
    @Override
    public int getIdByLogin(String login) {
        int id = -1;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username = ?");
            preparedStatement.setString(1,login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            logger.error("SQLException in getIdByLogin() : " + e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return id;
    }

    /**
     * Returns the user login according to the specified primary key
     * @param id Integer value of primary key
     * @return String value of user login
     */
    @Override
    public String getLoginById(int id) {
        String login = null;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT username FROM user WHERE id = ?");
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                login = resultSet.getString("username");
            }
        } catch (SQLException e) {
            logger.error("SQLException in getLoginById() : " + e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return login;
    }

    /**
     * Returns the user balance
     * @param login String value of user login
     * @return Double value of user balance
     */
    @Override
    public double getBalanceByLogin(String login) {
        double balance = -1;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT balance FROM user WHERE username = ?");
            preparedStatement.setString(1,login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                balance = resultSet.getInt("balance");
            }
        } catch (SQLException e) {
            logger.error("SQLException in getBalanceByLogin() : " + e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return balance;
    }

    /**
     * Increases the user's balance for the specified amount
     * @param login String value of user login
     * @param amount Double value of amount to increase
     * @return True value in case of successfully increase, else False
     */
    @Override
    public boolean addBalanceByLogin(String login, double amount) {
        boolean result = false;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE user SET balance = balance + ? WHERE username = ? ");
            preparedStatement.setDouble(1,amount);
            preparedStatement.setString(2,login);
            if (preparedStatement.executeUpdate()>0){
                result = true;
            }
        } catch (SQLException e){
            logger.error("SQLException in addBalanceByLogin() : " + e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return  result;
    }

    /**
     * Checks user for bookmaker role
     * @param login String value of user login
     * @return boolean value true or false
     * @throws SQLException
     */
    @Override
    public boolean isBookmaker(String login) throws SQLException {
        return AbstractDAOFactory.getDAOFactory().getUserDAO().checkUserByLogin(login)
                && AbstractDAOFactory.getDAOFactory().getUserTypeDAO().getTypeByLogin(login)
                .equals(AbstractDAOFactory.getDAOFactory().getUserTypeDAO().getTypeById(3));
    }

    /**
     * Checks user for administrator role
     * @param login String value of user login
     * @return boolean value true or false
     * @throws SQLException
     */
    @Override
    public boolean isAdmin(String login) throws SQLException {
        return AbstractDAOFactory.getDAOFactory().getUserDAO().checkUserByLogin(login)
                && AbstractDAOFactory.getDAOFactory().getUserTypeDAO().getTypeByLogin(login)
                .equals(AbstractDAOFactory.getDAOFactory().getUserTypeDAO().getTypeById(2));
    }

    @Override
    public ArrayList<String> getEmailList(){
        ArrayList<String> list = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM user");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String email = resultSet.getString("email");
                if (email != null){
                    list.add(resultSet.getString("email"));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return list;
    }
}
