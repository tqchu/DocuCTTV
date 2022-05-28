package com.ctvv.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailUtils {
	public static void send(String toEmail){
		String fromEmail = "quangchu12112002@gmail.com";
		String host = "smtp.gmail.com";

		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.starttls.required", "true");
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication("quangchu12112002@gmail.com", "password");

			}

		});

		session.setDebug(true);

		try {
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(fromEmail));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

			message.setSubject("CTVV xin thong bao ve don hang + order_id");

			message.setContent("<p>Xin chao customer_name,</p>\r\n"
							+ "    <p>CTVV xin loi khach hang vi don hang + order_id + da bi huy va khong the giao den khach hang."
							+ "    <p>Ly do: reasonInput</p>\r\n"
							+ "    <p>De xuat: recommendInput</p>\r\n"
							+ "    <p>Rat mong quy khach thong cam cho su co ngoai y muon nay va se ung ho chung toi vao lan sau.</p>",
					"text/html");

			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
