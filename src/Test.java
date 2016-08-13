import mail.Mailer;

import javax.mail.internet.*;
import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {

        ArrayList<String> list = new ArrayList<>();
        list.add("slava.rurenko@gmail.com");
        list.add("slava_rur@mail.ru");

        Mailer.informUsers("Fucking day", "Test text", list);

    }
}
