import dao.AbstractDAOFactory;

public class Test {

    public static void main(String[] args) {

        System.out.println(AbstractDAOFactory.getDAOFactory().getUserTypeDAO().getTypeByLogin("Viacheslav"));

    }
}
