

package ec.gob.dinardap.correo.servicio.impl;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import ec.gob.dinardap.correo.constante.ParametroEnum;
import ec.gob.dinardap.correo.servicio.MailServicio;
import ec.gob.dinardap.correo.util.MailMessage;
import ec.gob.dinardap.seguridad.servicio.ParametroServicio;

@Stateless(name = "MailServicio")
public class MailServicioImpl implements MailServicio {
	@EJB
	ParametroServicio parameterService;

	public void sender(MailMessage message) {
		try {

			// PARAMETROS
			String HOST = parameterService.findByPk(ParametroEnum.MAIL_HOST.toString()).getValor();
			String PORT = parameterService.findByPk(ParametroEnum.MAIL_PORT.toString()).getValor();
			String PROTOCOL = parameterService.findByPk(ParametroEnum.MAIL_PROTOCOL.toString()).getValor();
			final String USERNAME = message.getUsername();
			final String PASSWORD = message.getPassword();

			Properties p = new Properties();
			// GMAIL
			if (HOST.equals("smtp.gmail.com")) {
				p.put("mail.smtp.host", HOST);
				p.put("mail.smtp.socketFactory.port", PORT);
				p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				p.put("mail.smtp.auth", true);
				p.put("mail.smtp.from", message.getFrom());
				p.put("mail.smtp.port", PORT);
				p.put("mail.transport.protocol", PROTOCOL);
			} else {
				// NO GMAIL
				p.put("mail.smtp.auth", true);
				p.put("mail.transport.protocol", PROTOCOL);
				p.put("mail.smtp.user", USERNAME);
				p.put("mail.smtp.host", HOST);
				p.put("mail.smtp.from", message.getFrom());
				p.put("mail.smtp.password", PASSWORD);
				p.put("mail.smtp.port", PORT);
				p.put("mail.smtp.starttls.enable", "false");
			}

			// Session mailSession = Session.getDefaultInstance(p);
			Session mailSession = Session.getInstance(p,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(USERNAME, PASSWORD);
						}
					});

			mailSession.setDebug(true);
			Transport transport = mailSession.getTransport();

			MimeMessage mimeMessage = new MimeMessage(mailSession);
			mimeMessage.setSubject(message.getSubject(), "UTF-8");
			
			Multipart multipart = new MimeMultipart();
	        MimeBodyPart htmlPart = new MimeBodyPart();
	        
			

			// tiende adjunto

			if (message.isTieneAdjunto()) {
				mimeMessage = messageConAtachment(message, mimeMessage);
			} else {
				htmlPart.setContent(message.getText(), "text/html;charset=UTF-8");
		        multipart.addBodyPart(htmlPart);
		        mimeMessage.setContent(multipart); 

			}
			for (String to : message.getTo()) {
				System.out.println("to:" + to);
				mimeMessage.addRecipient(RecipientType.TO, new InternetAddress(to));
			}

			InternetAddress from = new InternetAddress(message.getFrom());
			mimeMessage.setFrom(from);

			transport.connect();
			// (SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
			transport.sendMessage(mimeMessage,
					mimeMessage.getRecipients(Message.RecipientType.TO));

			transport.close();

		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private MimeMessage messageConAtachment(MailMessage message, MimeMessage msj) {

		try {

			Multipart multipart = new MimeMultipart();

			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setContent(message.getText(), "text/html;charset=UTF-8");

			multipart.addBodyPart(textPart);

			for(Map.Entry<String, File> archivo : message.getAttachList().entrySet()){
				BodyPart attachmentPart = new MimeBodyPart();
				DataSource source = new FileDataSource(archivo.getValue());

				attachmentPart.setDataHandler(new DataHandler(source));
				//attachmentPart.setDataHandler(new DataHandler(new URL(message.getUrl())));

				attachmentPart.setFileName(archivo.getValue().getName());

				attachmentPart.setDisposition(Part.ATTACHMENT);

				//attachmentPart.setHeader("Content-Type", TipoArchivo.obtenerTipoArchivo(archivo.getKey()));
				//attachmentPart.setHeader("Content-Type", "application/pdf");

				multipart.addBodyPart(attachmentPart);
			}
			

			msj.setContent(multipart);
			return msj;

		} catch (MessagingException e) {
			System.err.println("MENSAJE MAL REPORTE");
			e.printStackTrace();
		}
		return null;
	}
}
