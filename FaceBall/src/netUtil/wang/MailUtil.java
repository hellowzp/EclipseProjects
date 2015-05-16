package netUtil.wang;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
	
	public static void main(String[] args) {
		final String username = "zpkx.wang@gmail.com";
		final String password = "kaixin58bc";
		final String smtphost = "smtp.gmail.com";

//		Properties props =  System.getProperties();
//		props.setProperty("mail.transport.protocol", "smtp"); 				
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.smtp.host", "smtp.gmail.com");
//		props.put("mail.smtp.port", 465);
//		props.put("mail.smtp.localport" , 25);		

		Properties props =  System.getProperties();
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.transport.protocol", "smtps");
		props.put("mail.debug", "true");
		
		props.put("mail.smtp.host", smtphost);
		props.put("mail.smtp.port", 465);
		
		props.put("mail.smtps.socketFactory.port", Integer.toString(465));
		props.put("mail.smtps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtps.socketFactory.fallback", "false");
		
//		props.setProperty("mail.transport.protocol", "smtp"); 			
		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.starttls.enable", "true");	

//		props.setProperty("mail.user", "myuser");
//		props.setProperty("mail.password", "mypwd");
	    System.out.println("1");
		Session session = Session.getInstance(props, null);
//		        new javax.mail.Authenticator() {
//		            protected PasswordAuthentication getPasswordAuthentication() {
//		                return new PasswordAuthentication(username, password);
//		            }
//		        });

		try {
		    System.out.println("2");
		    Message message = new MimeMessage(session);
		    message.setFrom(new InternetAddress("zpkx.wang@gmail.com"));
		    message.setRecipients(Message.RecipientType.TO,
		            InternetAddress.parse("zhipeng.wang@student.kuleuven.be"));
		    message.setSubject("Test Subject");
		    message.setSentDate(new Date());
		    message.setText("Test");

		    System.out.println("3");
		    Transport tr = session.getTransport("smtp");
		    System.out.println("4");

		    tr.connect(smtphost, username, password);
		    System.out.println("1");

		    message.saveChanges();      // don't forget this
		    tr.sendMessage(message, message.getAllRecipients());
		    System.out.println("6");

		    tr.close();
		    //Transport.send(message,username,password);

		    System.out.println("Done");

		} catch (MessagingException e) {
		    throw new RuntimeException(e);
		}
	}

	public static void sendMail(String msg, String mailAddress) {
		final String username = "zpkx.wang@gmail.com";
		final String password = "kaixin58bc";
		final String smtphost = "smtp.gmail.com";

		Properties props =  System.getProperties();
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.transport.protocol", "smtps");
		props.put("mail.debug", "true");
		
		props.put("mail.smtp.host", smtphost);
		props.put("mail.smtp.port", 465);
		
		props.put("mail.smtps.socketFactory.port", Integer.toString(465));
		props.put("mail.smtps.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtps.socketFactory.fallback", "false");		
		props.put("mail.smtp.auth", "true");
		
		Session session = Session.getInstance(props, null);
	    try {
		    Message message = new MimeMessage(session);
		    message.setFrom(new InternetAddress("zpkx.wang@gmail.com"));
		    message.setRecipients(Message.RecipientType.TO,
		            InternetAddress.parse(mailAddress));  //"zhipeng.wang@student.kuleuven.be"
		    message.setSubject("Get back your user name of FaceBall game");
		    message.setSentDate(new Date());
		    message.setText(msg);

		    Transport tr = session.getTransport("smtp");
		    tr.connect(smtphost, username, password);
		    message.saveChanges();      // don't forget this
		    tr.sendMessage(message, message.getAllRecipients());
		    tr.close();
		    
		} catch (MessagingException e) {
		    throw new RuntimeException(e);
		}
	}
}
