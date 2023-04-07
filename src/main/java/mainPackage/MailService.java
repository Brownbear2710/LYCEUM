package mainPackage;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailService {
    public static boolean sendMail(String receiver, String text, String subject)
    {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("lyceumservice@gmail.com", "bigkdgbamfkzrpgl");
            }
        });
        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("lyceumservice@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
            System.out.println("mail sent successfully");
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Mail not sent");
            return false;
        }
    }

    static boolean InternetAccess() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("lyceumservice@gmail.com", "bigkdgbamfkzrpgl");
            }
        });
//        session.setDebug(true);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("lyceumservice@gmail.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress("lyceumservice@gmail.com"));
            message.setSubject("Checking Internet Connection");
            message.setText("Ok");
            Transport.send(message);
            System.out.println("mail sent successfully");
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("No Internet");
            return false;
        }
    }
}
