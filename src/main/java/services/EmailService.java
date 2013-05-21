package services;

import model.Email;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {
    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private Properties props;
    private Session session;

    public EmailService() {
        props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        session = Session.getDefaultInstance(props, new EmailAuthenticator());
    }

    public boolean sendEmail(Email email) throws Exception {
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("email@email.com", "Smart fRidge"));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getReceiver(), "User"));
        msg.setSubject(email.getSubject());
        msg.setText(email.getContent());

        Transport.send(msg);

        return true;
    }
}
