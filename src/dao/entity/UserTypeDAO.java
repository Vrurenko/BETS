package dao.entity;

import dao.AbstractDAOFactory;
import dao.ConnectionPool;
import dao.interfaces.IUserTypeDAO;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTypeDAO implements IUserTypeDAO {
    private static Connection connection;
    private static java.sql.PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final Logger logger = Logger.getLogger(UserTypeDAO.class);

    /**
     * Returns user type by identifier
     * @param id Integer value of user type primary key
     * @return String value of user type
     */
    @Override
    public String getTypeById(int id)  {
        String type = null;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT type FROM user_type WHERE id = ?");
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                type = resultSet.getString("type");
            }
        } catch (SQLException e){
            logger.error("SQLException in getTypeById() : " + e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    return type;
    }

    /**
     * Returns unique identifier of user type
     * @param type String value of user type
     * @return Integer value of user type identifier
     * @throws SQLException
     */
    @Override
    public int getIdByType(String type) throws SQLException {
        int id = -1;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT id FROM user_type WHERE type = ?");
            preparedStatement.setString(1,type);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return resultSet.getInt("id");
            }
        } catch (SQLException e){
            logger.error("SQLException in getIdByType() : " + e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return id;
    }

    /**
     * Returns user type according to the user login
     * @param login String value of user login
     * @return String value of user type
     * @throws SQLException
     */
    @Override
    public String getTypeByLogin(String login) throws SQLException {
        String type = null;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT user_type FROM user WHERE username = ?");
            preparedStatement.setString(1,login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                type = AbstractDAOFactory.getDAOFactory().getUserTypeDAO().getTypeById(resultSet.getInt("user_type"));
            }
        } catch (SQLException e){
            logger.error("SQLException in getTypeByLogin() : " + e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return type;
    }
}
