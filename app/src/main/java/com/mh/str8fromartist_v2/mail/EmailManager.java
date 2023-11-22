package com.mh.str8fromartist_v2.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
public class EmailManager {

    public void sendEmail(Email email) {
        final String username = "legolegends.first@gmail.com"; // Your Gmail email address
        final String password = ""; // Your Gmail password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com"); // Gmail SMTP server
        props.put("mail.smtp.port", "587"); // Port for TLS

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("codeblooded80@gmail.com")); // Recipient's email address
            message.setSubject(email.getSubject());
            message.setText(email.getBody());

            Transport.send(message);
            System.out.println("Email sent successfully");
            // Email sent successfully
        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle email sending error
        }
    }

}
