import dao.AbstractDAOFactory;
import mail.Mailer;

import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {

        ArrayList<String> list = AbstractDAOFactory.getDAOFactory().getUserDAO().getEmailList();

        System.out.println(list);

        Mailer.informUsers("Fucking day", "Test text", list);

    }
}
