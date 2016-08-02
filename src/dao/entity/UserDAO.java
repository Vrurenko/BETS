package dao.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

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


    @Override
    public boolean checkUserByLoginAndPassword(String login, String password) throws SQLException {
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

    @Override
    public boolean isBookmaker(String login) throws SQLException {
        return AbstractDAOFactory.getDAOFactory().getUserDAO().checkUserByLogin(login)
                && AbstractDAOFactory.getDAOFactory().getUserTypeDAO().getTypeByLogin(login)
                .equals(AbstractDAOFactory.getDAOFactory().getUserTypeDAO().getTypeById(3));
    }

    @Override
    public boolean isAdmin(String login) throws SQLException {
        return AbstractDAOFactory.getDAOFactory().getUserDAO().checkUserByLogin(login)
                && AbstractDAOFactory.getDAOFactory().getUserTypeDAO().getTypeByLogin(login)
                .equals(AbstractDAOFactory.getDAOFactory().getUserTypeDAO().getTypeById(2));
    }
}
