package dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {
    private static String url;
    private static String user;
    private static String password;
    private static String driver;
    private BlockingQueue<Connection> connectionFreeQueue;
    private static final Logger logger = Logger.getLogger(ConnectionPool.class);


    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("database");
        url = resourceBundle.getString("url");
        user = resourceBundle.getString("user");
        password = resourceBundle.getString("password");
        driver = resourceBundle.getString("driver");
        try {
            Class.forName(driver).newInstance();
        } catch (ClassNotFoundException e) {
            logger.error("Драйвер не загружен");
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static ConnectionPool connectionPool;

    private ConnectionPool() {
        connectionFreeQueue = new LinkedBlockingQueue<>();
    }

    public static ConnectionPool getInstance() {
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        return connectionPool;
    }

    public void releaseConnection(Connection connection) {
        connectionFreeQueue.add(connection);
    }

    public Connection getConnection() throws SQLException {
        if (connectionFreeQueue.isEmpty()) {
            connectionFreeQueue.add(java.sql.DriverManager.getConnection(url, user, password));
        }
        return connectionFreeQueue.poll();
    }


}
