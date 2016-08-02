package dao.interfaces;

import java.sql.SQLException;

/**
 * Created by Comandante on 05.07.2016.
 */
public interface IUserTypeDAO {

    public String getTypeById(int id) throws SQLException;
    public int getIdByType(String type)throws SQLException;
    public String getTypeByLogin(String login) throws SQLException;
}
