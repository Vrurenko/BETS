import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Test {
    public static void main(String[] args) throws MessagingException {

        Properties mailProperties = new Properties();
        mailProperties.put("mail.smtp.host","smtp.mail.ru");
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.starttls.enable", "true");
        mailProperties.put("mail.port", "465");

        Session mailSession=Session.getInstance(mailProperties,new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return(new PasswordAuthentication("slava_rur","SlavikSingSing94"));
            }
        });
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress("slava_rur@mail.ru"));
        String[] emails={"slava.rurenko@gmail.com"}; //адреса получателей
        InternetAddress dests[]=new InternetAddress[emails.length];
        for(int i=0; i<emails.length; i++){
            dests[i]=new InternetAddress(emails[i].trim().toLowerCase());
        }
        message.setRecipients(Message.RecipientType.TO, dests);
        message.setSubject("тема письма","KOI8-R");
        Multipart mp=new MimeMultipart();
        MimeBodyPart mbp1=new MimeBodyPart();
        mbp1.setText("текст письма","KOI8-R");
        mp.addBodyPart(mbp1);
        message.setContent(mp);
        message.setSentDate(new java.util.Date());
        Transport.send(message);

    }
}
