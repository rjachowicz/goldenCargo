package com.goldencargo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String SENDER;
    @Value("${email.responder}")
    private String RESPONDER;
    private static final String FILE_TYP = ".pdf";
    private final JavaMailSender mailSender;


    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmailWithAttachment(String to, String subject, String body, byte[] attachment) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);
            helper.setFrom(SENDER);
            helper.addAttachment("report" + FILE_TYP, new ByteArrayDataSource(attachment, "application/pdf"));

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email with attachment", e);
        }
    }

    public void sendSimpleEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);
            helper.setFrom(SENDER);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public void sendContactFormMessage(String subject, String name, String contactEmail, String messageContent) {
        try {
            String body = "Message from: " + name + " (" + contactEmail + "):<br/><br/>" + messageContent;
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(RESPONDER);
            helper.setSubject(subject);
            helper.setFrom(SENDER);
            String htmlContent = buildContactEmailHtml(body);
            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send contact form email", e);
        }
    }

    private String buildContactEmailHtml(String messageBody) {
        return "<html>" +
                "<head>" +
                "  <meta charset='UTF-8'>" +
                "  <style>" +
                "    body { " +
                "      font-family: Arial, sans-serif; " +
                "      background-color: #f4f4f4; " +
                "      margin: 0; " +
                "      padding: 0; " +
                "    }" +
                "    .container { " +
                "      max-width: 600px; " +
                "      margin: 20px auto; " +
                "      background-color: #ffffff; " +
                "      border-radius: 8px; " +
                "      box-shadow: 0 4px 8px rgba(0,0,0,0.1); " +
                "      overflow: hidden; " +
                "    }" +
                "    .header { " +
                "      background-color: #ffcc00; " +
                "      padding: 20px; " +
                "      text-align: center; " +
                "    }" +
                "    .header h1 { " +
                "      margin: 0; " +
                "      color: #333333; " +
                "      font-size: 2rem; " +
                "    }" +
                "    .content { " +
                "      padding: 20px; " +
                "      color: #333333; " +
                "      line-height: 1.6; " +
                "    }" +
                "    .footer { " +
                "      background-color: #333333; " +
                "      color: #ffffff; " +
                "      text-align: center; " +
                "      padding: 10px; " +
                "      font-size: 0.9rem; " +
                "    }" +
                "  </style>" +
                "</head>" +
                "<body>" +
                "  <div class='container'>" +
                "    <div class='header'><h1>Golden Cargo Contact</h1></div>" +
                "    <div class='content'>" + messageBody + "</div>" +
                "    <div class='footer'>© 2025 Golden Cargo. All rights reserved.</div>" +
                "  </div>" +
                "</body>" +
                "</html>";
    }

}
