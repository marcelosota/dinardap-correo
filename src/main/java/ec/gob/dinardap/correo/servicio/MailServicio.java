package ec.gob.dinardap.correo.servicio;

import javax.ejb.Local;

import ec.gob.dinardap.correo.exception.MailException;
import ec.gob.dinardap.correo.util.MailMessage;

/**
 * @author Juan Ochoa
 * @version $Revision: 1.1 $
 */
@Local
public interface MailServicio {
	////revisar

	/**
	 * Metodo que coloca el mail al enviador.
	 * 
	 * @param message
	 */ 
	public void sender(MailMessage message) throws MailException;
}
