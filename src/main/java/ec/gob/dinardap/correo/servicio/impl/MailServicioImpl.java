
package ec.gob.dinardap.correo.servicio.impl;

import java.io.File;
import java.util.Map;
import javax.mail.BodyPart;
import javax.mail.Part;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.DataHandler;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import ec.gob.dinardap.correo.constante.ParametroEnum;
import ec.gob.dinardap.correo.servicio.MailServicio;
import ec.gob.dinardap.correo.util.MailMessage;
import ec.gob.dinardap.seguridad.servicio.ParametroServicio;



@Stateless(name = "MailServicio")
public class MailServicioImpl implements MailServicio {
	
	@EJB
	ParametroServicio parameterService;

	public void sendMail(MailMessage message)
			throws AuthenticationFailedException, MessagingException {

		// PARAMETROS
		String HOST =
		parameterService.findByPk(ParametroEnum.MAIL_HOST.toString()).getValor();
		String PORT =
		parameterService.findByPk(ParametroEnum.MAIL_PORT.toString()).getValor();
		String PROTOCOL =
		parameterService.findByPk(ParametroEnum.MAIL_PROTOCOL.toString()).getValor();		
		final String USERNAME = message.getUsername();
		final String PASSWORD = message.getPassword();		
		String FROM = message.getFrom();
		Properties p = new Properties();
		p.put("mail.smtp.host", HOST);
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.starttls.enable", "false");
		p.put("mail.smtp.port", PORT);
		p.put("mail.transport.protocol", PROTOCOL);

		Session session = Session.getInstance(p, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});

		Message messa = new MimeMessage(session);
		messa.setFrom(new InternetAddress(message.getFrom()));
		//
		for (String to : message.getTo()) {
			System.out.println("to:" + to);
			messa.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		}
		
		try {
			if (message.getTo() != null && ! message.getTo().isEmpty()) {			   
				messa.setSubject(MimeUtility.encodeText(message.getSubject(), "UTF-8", "Q"));
				Multipart multipartes = new MimeMultipart();
				MimeBodyPart htmlPart = new MimeBodyPart();

				if (message.isTieneAdjunto()) {
					messa = messageConAtachment(message,messa);
				} else {

				htmlPart.setContent(message.getText(), "text/html; charset=utf-8");
				multipartes.addBodyPart(htmlPart);
				messa.setContent(multipartes);
				}
				Transport.send(messa);
			}
		} catch (AuthenticationFailedException e) {
			throw e;
		} catch (MessagingException ex) {
			throw ex;
		} catch (UnsupportedEncodingException ex) {
			Logger.getLogger(MailServicio.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	private Message messageConAtachment(MailMessage message,Message msj) {

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
