
package ec.gob.dinardap.correo.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

import ec.gob.dinardap.correo.exception.MailException;
import ec.gob.dinardap.correo.servicio.MailServicio;
import ec.gob.dinardap.correo.util.MailMessage;

/**
 * @author Daniel Cardenas
 * @version $Revision: 1.2 $
 */

@MessageDriven(activationConfig = {
@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/MaildinardapQueue"), 
@ActivationConfigProperty(propertyName = "transactionTimeout", propertyValue="60") })

public class MailMDB implements MessageListener{
 
	@EJB
	private MailServicio mailService;

	/**
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	
	public void onMessage(Message message) {
		System.out.println("Se procesa el elemento encolado...");
		if (message instanceof ObjectMessage) {
			try {
				MailMessage mm = (MailMessage) (((ObjectMessage) message).getObject());

				mailService.sendMail(mm);
			} catch (JMSException e) {
				e.printStackTrace();
			} catch (AuthenticationFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	

}