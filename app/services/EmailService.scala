package services

import javax.mail._
import javax.mail.internet._
import java.util.Properties._

case class Email(from: String, to: String, subject: String, content: String);

// requires Java Mail API (mail.jar), which must be in classpath

class EmailService {
	
	
	
	def sendEmail(email: Email) = {
		// Set up the mail object
		val properties = System.getProperties
		properties.put("mail.smtp.host", "localhost")
		val session = Session.getDefaultInstance(properties)
		val message = new MimeMessage(session)
		
		// Set the from, to, subject, body text
		message.setFrom(new InternetAddress(email.from))
		message.setRecipients(Message.RecipientType.TO, email.to)
		message.setSubject(email.subject)
		message.setText(email.content)

		// And send it
		Transport.send(message)
	}
}
