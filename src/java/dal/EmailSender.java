/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dal;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author NHAT ANH
 */
public class EmailSender {

    /**
     * @param to
     * @return 
     */
    public boolean sendEmail(String to) {
        // Các thông tin cần thiết để gửi email
        final String username = "thanhthantho14@gmail.com";
        // mât khâu lây tu muc "Mât Khâu Ung dung" trong google mail
        final String password = "tvaxxtxneavaknnz";

        // Địa chỉ email nhận la` "to"

        // Tạo đối tượng Properties và cấu hình các thông tin cần thiết
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        //props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // Tạo đối tượng Session và truyền vào các thông tin cần thiết
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Tạo đối tượng Message và cấu hình các thông tin cần thiết
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Test email from Java");
            message.setText("This is a test email sent from Java.");

            // Gửi email
            Transport.send(message);

            return true;
        } catch (MessagingException e) {
            return false;
        }
    }


    public static void main(String[] args) {
        EmailSender a = new EmailSender();
        System.out.println(a.sendEmail("thanhnx14042000@gmail.com"));
    }
}
