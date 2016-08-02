package dao.entity;

import beans.Bet;
import dao.AbstractDAOFactory;
import dao.ConnectionPool;
import dao.interfaces.IBetDAO;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BetDAO implements IBetDAO {
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final Logger logger = Logger.getLogger(BetDAO.class);


    @Override
    public ArrayList<Bet> getBetsByUser(String user) {
        ArrayList<Bet> list = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM bet WHERE client = ?");
            preparedStatement.setInt(1, AbstractDAOFactory.getDAOFactory().getUserDAO().getIdByLogin(user));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Bet bet = new Bet();
                bet.setId(resultSet.getInt("id"));
                bet.setClient(user);
                bet.setRace(resultSet.getInt("race"));
                bet.setRider(resultSet.getInt("rider"));
                bet.setAmount(resultSet.getDouble("amount"));
                bet.setResult(resultSet.getBoolean("result"));
                if (resultSet.wasNull()){
                    bet.setResult(null);
                }
                list.add(bet);
            }
        } catch (SQLException e){
            logger.error("SQLException in getBetsByUser() : " + e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return list;
    }

    @Override
    public boolean addBet(String client, int race, int rider, double amount) {
        Boolean result = false;
            try{
                connection = connectionPool.getConnection();
                preparedStatement = connection.prepareStatement(
                        "INSERT INTO bet (client, race, rider, amount) VALUES(?,?,?,?);");
                preparedStatement.setInt(1,AbstractDAOFactory.getDAOFactory().getUserDAO().getIdByLogin(client));
                preparedStatement.setInt(2,race);
                preparedStatement.setInt(3,rider);
                preparedStatement.setDouble(4,amount);
                preparedStatement.execute();
                result = true;
            } catch (SQLException e){
                logger.error("SQLException in addBet() : " + e);
            }

        return result;
    }

    @Override
    public boolean setBetResultByID(int id, boolean result){
        boolean answer = false;
        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE bet SET result = ? WHERE id = ?");
            preparedStatement.setBoolean(1,result);
            preparedStatement.setInt(2,id);
            if (preparedStatement.executeUpdate()>0){
                answer = true;
            }
        } catch (SQLException e){
            logger.error("SQLException in setBetResultByID() : " + e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return  answer;
    }
}
