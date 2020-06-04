/*
 * ClienteQueueMailServicioImpl.java.
 */
package ec.gob.dinardap.correo.mdb.cliente.impl;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import ec.gob.dinardap.correo.mdb.cliente.ClienteQueueMailServicio;
import ec.gob.dinardap.correo.util.MailMessage;


/**
 * Clase que implementa los metdos de la interfaz ClienteQueueMailServicio.java
 * 
 * @author Daniel Cardenas
 * @version $1.0$
 */
@Stateless(name = "ClienteQueueMailServicio")
public class ClienteQueueMailServicioImpl implements ClienteQueueMailServicio {
	/*@Resource(mappedName = "java:/ConnectionFactory")
	private ConnectionFactory connectionFactory;

	@Resource(mappedName = "java:/jms/queue/MaildinardapQueue")
	private Queue queue;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pfponline.ejb.service.ClienteQueueMailServiceLocal#encolarMail(com
	 * .pfponline.util.MailMessage)
	 */
	public void encolarMail(MailMessage mmessage) {
		/*Connection connection = null;
		Session session = null;

		try {
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			MessageProducer messageProducer = session.createProducer(queue);
			// objeto a encolar
			ObjectMessage objectMessage = session.createObjectMessage();
			objectMessage.setObject(mmessage);

			messageProducer.send(objectMessage);

			System.out.println("Se encola el elemento");
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				try {
					session.close();
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}*/
	}

}
