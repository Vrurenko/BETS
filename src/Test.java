import mail.Mailer;

public class Test {

    public static void main(String[] args) {

        String[] addresses = {"slava.rurenko@gmail.com", "slava_rur@mail.ru"};

        Mailer.informUsers("Пшонка", "Идите нахуй", addresses);

    }
}
