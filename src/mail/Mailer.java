package mail;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;

public class Mailer {
    private static Properties mailProperties = new Properties();
    private static String SENDER;
    private static String LOGIN;
    private static String PASSWORD;

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("mail");
        mailProperties.put("mail.smtp.host", resourceBundle.getString("SMTP"));
        mailProperties.put("mail.smtp.auth", resourceBundle.getString("Auth"));
        mailProperties.put("mail.smtp.starttls.enable", resourceBundle.getString("Starttls.enable"));
        mailProperties.put("mail.port", resourceBundle.getString("Port"));
        SENDER = resourceBundle.getString("Sender");
        LOGIN = resourceBundle.getString("Login");
        PASSWORD = resourceBundle.getString("Password");
    }


    public static void informUsers(String subject, String text, ArrayList<String> addressList) {

        Session session = Session.getInstance(mailProperties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(LOGIN, PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER));

            String[] addresses = new String[addressList.size()];
            InternetAddress[] recepients = new InternetAddress[addressList.size()];
            addressList.toArray(addresses);

            for (int i = 0; i < addresses.length; i++) {
                recepients[i] = new InternetAddress(addresses[i]);
            }

            message.setRecipients(Message.RecipientType.TO, recepients);
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setText(text);
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
