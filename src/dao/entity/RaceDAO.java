package dao.entity;

import beans.Race;
import dao.AbstractDAOFactory;
import dao.ConnectionPool;
import dao.interfaces.IRaceDAO;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;

public class RaceDAO implements IRaceDAO {
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final Logger logger = Logger.getLogger(RaceDAO.class);

    /**
     * Returns the list of all races
     *
     * @return List of races
     */
    @Override
    public ArrayList<Race> getAllRaces() {
        ArrayList<Race> list = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM race");
            while (resultSet.next()) {
                Race race = new Race();
                race.setId(resultSet.getInt("id"));
                race.setBookmaker(AbstractDAOFactory.getDAOFactory().getUserDAO().getLoginById(resultSet.getInt("bookmaker")));
                race.setAdmin(AbstractDAOFactory.getDAOFactory().getUserDAO().getLoginById(resultSet.getInt("admin")));
                race.setMultiplier(resultSet.getDouble("multiplier"));
                if (resultSet.wasNull()) {
                    race.setMultiplier(null);
                }
                race.setWinner(resultSet.getInt("winner"));
                if (resultSet.wasNull()) {
                    race.setWinner(null);
                }
                list.add(race);
            }
        } catch (SQLException e) {
            logger.error("SQLException in getAllRaces() : " + e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return list;
    }

    /**
     * Set race multiplier
     *
     * @param id         Integer value of race identifier
     * @param multiplier Double value of multiplier
     * @return True value in case of successfully update, else False
     */
    @Override
    public boolean setMultiplier(int id, double multiplier) {
        boolean result = false;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE race SET multiplier = ? WHERE id = ?");
            preparedStatement.setDouble(1, multiplier);
            preparedStatement.setInt(2, id);
            if (preparedStatement.executeUpdate() > 0) {
                result = true;
            }
        } catch (SQLException e) {
            logger.error("SQLException in setMultiplier() : " + e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return result;
    }

    /**
     * Set the race winner, changes the states of bets for current race,
     * changes the users balances according to the bets results
     *
     * @param id     Integer value of race identifier
     * @param winner Integer value of race winner
     * @return True value in case of successfully update, else False
     */
    @Override
    public boolean setWinner(int id, int winner) {
        boolean result = false;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE race SET winner = ? WHERE id = ? AND winner IS NULL");
            preparedStatement.setInt(1, winner);
            preparedStatement.setInt(2, id);
            if (preparedStatement.executeUpdate() > 0) {
                result = true;
            }
            ///////////////////////////////////////////////////////////////////////////////////
            double multiplier = AbstractDAOFactory.getDAOFactory().getRaceDAO().getMultiplierByRaceID(id);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM bet WHERE result IS NULL");
            while (resultSet.next()) {
                String client = AbstractDAOFactory.getDAOFactory().getUserDAO().getLoginById(resultSet.getInt("client"));
                double amount = resultSet.getDouble("amount");
                int rider = resultSet.getInt("rider");
                int betID = resultSet.getInt("id");

                if (rider == winner) {
                    AbstractDAOFactory.getDAOFactory().getUserDAO().addBalanceByLogin(client, amount * (multiplier - 1));
                    AbstractDAOFactory.getDAOFactory().getBetDAO().setBetResultByID(betID, true);
                } else {
                    AbstractDAOFactory.getDAOFactory().getUserDAO().addBalanceByLogin(client, -amount);
                    AbstractDAOFactory.getDAOFactory().getBetDAO().setBetResultByID(betID, false);
                }
            }
            /////////////////////////////////////////////////////////////////////////////////////////////////////
        } catch (SQLException e) {
            logger.error("SQLException in setWinner() : " + e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return result;
    }

    /**
     * Returns the race multiplier by the race identifier
     *
     * @param id Integer value of race primary key
     * @return Double value of race multiplier
     */
    @Override
    public double getMultiplierByRaceID(int id) {
        double multiplier = 0;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT multiplier FROM race WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                multiplier = resultSet.getDouble("multiplier");
            }
        } catch (SQLException e) {
            logger.error("SQLException in getMultiplierByRaceID() : " + e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return multiplier;
    }

    /**
     * Adds new race with specified parameters
     *
     * @param bookmaker String value of race bookmaker login
     * @param admin     String value of race administrator login
     * @return True value in case of successfully addition, else False
     */
    @Override
    public boolean addRace(String bookmaker, String admin) {
        boolean result = false;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO race (bookmaker, admin) VALUES (?,?)");
            preparedStatement.setInt(1, AbstractDAOFactory.getDAOFactory().getUserDAO().getIdByLogin(bookmaker));
            preparedStatement.setInt(2, AbstractDAOFactory.getDAOFactory().getUserDAO().getIdByLogin(admin));
            preparedStatement.execute();
            result = true;
        } catch (SQLException e) {
            logger.error("SQLException in addRace() : " + e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return result;
    }

    /**
     * Returns the race administrator
     *
     * @param id Integer value of race primary key
     * @return User name of race administrator
     */
    @Override
    public String getRaceAdmin(int id) {
        String admin = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT admin FROM race WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                admin = AbstractDAOFactory.getDAOFactory().getUserDAO().getLoginById(resultSet.getInt("admin"));
            }
        } catch (SQLException e) {
            logger.error("SQLException in getRaceAdmin() : " + e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return admin;
    }

    /**
     * Returns the race bookmaker
     *
     * @param id Integer value of race primary key
     * @return User name of race bookmaker
     */
    @Override
    public String getRaceBookmaker(int id) {
        String bookmaker = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT bookmaker FROM race WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                bookmaker = AbstractDAOFactory.getDAOFactory().getUserDAO().getLoginById(resultSet.getInt("bookmaker"));
            }
        } catch (SQLException e) {
            logger.error("SQLException in getRaceBookmaker() : " + e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return bookmaker;
    }

    /**
     * Checks race for the winner existence
     *
     * @param id Integer value of race identifier
     * @return True value in case of winner existence, else False
     */
    @Override
    public boolean hasWinner(int id) {
        boolean result = true;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT winner FROM race WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            resultSet.getInt("winner");
            if (resultSet.wasNull()) {
                result = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return result;
    }

    /**
     * Checks race for the existence by means of race id
     *
     * @param id Integer value of race identifier
     * @return True value in case of race existence, else False
     */
    @Override
    public boolean doesExist(int id) {
        boolean result = false;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM race WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return result;
    }

    /**
     * Checks race for the multiplier existence
     *
     * @param id Integer value of race identifier
     * @return True value in case of multiplier existence, else False
     */
    @Override
    public boolean hasMultiplier(int id) {
        boolean result = true;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT multiplier FROM race WHERE id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            resultSet.getInt("multiplier");
            if (resultSet.wasNull()) {
                result = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return result;
    }
}
