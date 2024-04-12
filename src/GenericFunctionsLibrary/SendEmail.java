package GenericFunctionsLibrary;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;  
  
public class SendEmail {  
	
	

	/***********************************************************************************************
	 * Function Description :Sends email from your gmail account
	 * @author rajat.jain, date: 26-May-2016
	 * @throws UnsupportedEncodingException 
	 
	 * *********************************************************************************************/

	public void sendEmail(String To ,String From,String yourPassword,String subject,String msg,String mailerTitle) throws UnsupportedEncodingException
	{
		// String to="rajat.jain@naukri.com";//change accordingly  
		  final String from=From;
		  final String password=yourPassword;
		  //Get the session object  
		  Properties props = new Properties();  
		  props.put("mail.smtp.host", "smtp.gmail.com");  
		  props.put("mail.smtp.socketFactory.port", "465");  
		  props.put("mail.smtp.socketFactory.class",  
		            "javax.net.ssl.SSLSocketFactory");  
		  props.put("mail.smtp.auth", "true");  
		  props.put("mail.smtp.port", "465");  
		   
		  Session session = Session.getDefaultInstance(props,  
		   new javax.mail.Authenticator() {  
		   protected PasswordAuthentication getPasswordAuthentication() {  
		   return new PasswordAuthentication(from,password);//change accordingly  
		   }  
		  });  
		   
		  //compose message  
		  try {  
		   MimeMessage message = new MimeMessage(session);  
		   message.setFrom(new InternetAddress(From, mailerTitle));//change accordingly  
		 //  message.addRecipient(Message.RecipientType.TO,new InternetAddress(To));  
		   message.addRecipients(Message.RecipientType.TO, To);
		   message.setSubject(subject);  
		   message.setContent(msg, "text/html"); 
		     
		   //send message  
		   Transport.send(message);  
		   System.out.println("Mail Sent");
		   
		  } catch (MessagingException e) {throw new RuntimeException(e);}  
		   
		 }  
	
	 public static void main(String[] args) throws UnsupportedEncodingException {
		 
		 String message = "<i>Testing Alias Mailing For GCM</i><br>";
	        message += "<b>Wish you a nice day!</b><br>";
	        message += "<font color=red>Rajat</font>";
		  // method to send a mail in Java.
		  SendEmail sendEmailToGroup = new SendEmail();
		  sendEmailToGroup.sendEmail("mobiletesting@naukri.com", "rashitesting@gmail.com", "naukri2test", "Testing Alias mailing and mail formatting", message,"");
	
		 
		 }
		 
		
		}	
