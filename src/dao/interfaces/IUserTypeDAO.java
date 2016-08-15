package dao.interfaces;

public interface IUserTypeDAO {

    String getTypeById(int id);

    int getIdByType(String type);

    String getTypeByLogin(String login);
}
