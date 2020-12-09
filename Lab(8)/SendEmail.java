package ua.edu.chmnu.ki.networks.mail.Lab08_MailSend;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Мэил кому отправлять
        String to = "receiver1@gmail.com";

        // Мэил отправителя
        String from = "sender1@gmail.com";

        // отправка по локальному хосту
        String host = "localhost";

        // получение свойств
        Properties properties = System.getProperties();

        // настройка почтового сервера
        properties.setProperty("mail.smtp.host", host);

        //сессия
        Session session = Session.getDefaultInstance(properties);

        // тип ответа
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // создание обьекта MimeMessage
            MimeMessage message = new MimeMessage(session);


            message.setFrom(new InternetAddress(from));


            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Тема
            message.setSubject("123 Testing mail");

            // сообщение
            message.setText("Hello nerds, how are you doing ? =)");

            // Отправка
            Transport.send(message);
            String title = "Send Email";
            String res = "Sent message successfully....";
            String docType =
                    "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

            out.println(docType +
                    "<html>\n" +
                    "<head><title>" + title + "</title></head>\n" +
                    "<body bgcolor = \"#f0f0f0\">\n" +
                    "<h1 align = \"center\">" + title + "</h1>\n" +
                    "<p align = \"center\">" + res + "</p>\n" +
                    "</body> </html>"
         );
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}