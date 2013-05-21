package services;

import javax.mail.PasswordAuthentication;

/**
 * Created with IntelliJ IDEA.
 * User: Pawel
 * Date: 28.04.13
 * Time: 15:42
 * To change this template use File | Settings | File Templates.
 */
public class EmailAuthenticator extends javax.mail.Authenticator {
    private static final String SMTP_AUTH_USER = "pwrticketmanagment@gmail.com";
    private static final String SMTP_AUTH_PWD = "ltxfmvlbsgswpckr";

    public PasswordAuthentication getPasswordAuthentication() {
        String username = SMTP_AUTH_USER;
        String password = SMTP_AUTH_PWD;
        return new PasswordAuthentication(username, password);
    }
}
