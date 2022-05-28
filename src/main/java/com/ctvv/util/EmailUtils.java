package com.ctvv.util;

import com.ctvv.model.Order;
import org.bouncycastle.util.encoders.UTF8;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.Properties;

public class EmailUtils {
	public enum EMAIL_TYPE{
		CANCEL,
		SHIPPED
	}
	public static void send(EMAIL_TYPE type, String toEmail, Order order, String reason, String recommend){
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

				return new PasswordAuthentication("noithatctvv@gmail.com", "noithatctvv70959798");

			}

		});

		session.setDebug(true);

		try {
			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress(fromEmail));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

			String subject = null ;
			String content = null;
			switch (type){
				case CANCEL:
					subject = "Đơn hàng " + order.getOrderId() + " đã bị hủy";
					content = getCanceledEmailContent(order, reason, recommend);
					break;
				case SHIPPED:
					subject = "Đơn hàng " + order.getOrderId() + " đã giao thành công";
					content= getShippedOrderEmailContent(order);
					break;
			}
			message.setSubject(subject, "UTF-8");

			message.setContent(content, "text/html;charset=UTF-8");

			Transport.send(message);
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
	private static String getCanceledEmailContent(Order order, String reason, String recommend){
		return "<p>Xin chào "+ order.getCustomerName()+ ",</p>\r\n"
				+ "    <p>CTVV chân thành xin lỗi quý khách hàng vì đơn hàng "+ "<a href='http://localhost:8080" +
				"/noithatctvv/user/purchase/" +order.getOrderId()+"'>" +order.getOrderId() +"</a> đã bị hủy " +
				"vì" +
				" " +
				"<strong><i>" +
				reason+
				 "</i></strong>\r\n. "
				+ "    <p>Quý khách hàng có thể "+ "<i>"+recommend+"</i>."+"</p>\r\n"
				+ "    <p>Rất mong quý khách hàng có thể thông cảm cho chúng tôi về sự cố này và ủng hộ chúng tôi vào" +
				" lần sau.</p> "
				+"<p> Chân thành cảm ơn quý khách! Chúc quý khách một ngày tốt lành! </p>";
	}
	private static String getShippedOrderEmailContent(Order order){
		return "<p>Xin chào "+ order.getCustomerName()+ ",</p>\r\n"
				+ "    <p>Đơn hàng "+ "<a href='http://localhost:8080" +
				"/noithatctvv/user/purchase/" +order.getOrderId()+"'>" +order.getOrderId() +"</a> đã được giao!"
				+"<p> CTVV chân thành cảm ơn quý khách vì đã tin tưởng cửa hàng chúng tôi!</p>" +
				"<p>Mọi vấn đề liên quan đến sản phẩm, xin quý khách hàng vui lòng liên hệ chúng tôi để được giải " +
				"quyết!" +
				"<p>Chúc quý khách một ngày tốt lành! " +
				"</p>";
	}
	public static void main(String[] args) {
	}
}
