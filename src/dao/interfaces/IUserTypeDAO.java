package dao.interfaces;

import java.sql.SQLException;

public interface IUserTypeDAO {

    String getTypeById(int id) throws SQLException;
    int getIdByType(String type)throws SQLException;
    String getTypeByLogin(String login) throws SQLException;
}
