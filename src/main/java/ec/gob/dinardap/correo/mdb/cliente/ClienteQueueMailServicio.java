/*
 * ClienteQueueMailServicio.java
 * 
 */
package ec.gob.dinardap.correo.mdb.cliente;

import javax.ejb.Local;

import ec.gob.dinardap.correo.util.MailMessage;



/**
 * Interfaz que define los metodos para encolar un mensaje que utiliza la cola
 * de mail.
 * 
 * @author Daniel Cardenas
 * @version $1.0$
 */
@Local
public interface ClienteQueueMailServicio {

	/**
	 * Pone el mail en la cola.
	 * 
	 * @param mmessage
	 */
	void encolarMail(MailMessage mmessage);
}
