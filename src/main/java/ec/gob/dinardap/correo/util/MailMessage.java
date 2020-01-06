package ec.gob.dinardap.correo.util;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * @author Fernando Tamayo
 * @version $Revision: 1.2 $
 */
public class MailMessage implements Serializable {

	private static final long serialVersionUID = 8298138942139189647L;

	private String text;

	private String from;

	private List<String> to;

	private String subject;

	private HashMap<String, File> attachList;

	/** The template value map. */
	private Map<String, String> templateValueMap;
	
	private boolean tieneAdjunto;
	
	private String url;

	private String nombreReporte; 

	private String templateName;
	
	private String username;
	
	private String password;

	/**
	 * @param text
	 * @param from
	 * @param to
	 * @param subject
	 * @param attachName
	 * @param attachFile
	 */
	public MailMessage(String text, String from, List<String> to,
			String subject, HashMap<String, File> attachFileList) {
		super();
		this.text = text;
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.attachList = attachFileList;
	}

	/**
	 * @param text
	 * @param from
	 * @param to
	 * @param subject
	 * @param attachName
	 * @param attachFile
	 * @param bol
	 */
	public MailMessage(String text, String from, List<String> to,
			String subject, HashMap<String, File> attachFileList, boolean bol) {
		super();
		this.text = text;
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.attachList = attachFileList;
		this.tieneAdjunto = bol;
	}

	/**
	 * @param text
	 * @param from
	 * @param to
	 * @param subject
	 */
	public MailMessage(String text, String from, List<String> to, String subject) {
		super();
		this.text = text;
		this.from = from;
		this.to = to;
		this.subject = subject;
	}

	public MailMessage() {

	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the from
	 */
	public String getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set
	 */
	public void setFrom(String from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public List<String> getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(List<String> to) {
		this.to = to;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * Returns the value of attachList.
	 * 
	 * @return attachList
	 */
	public HashMap<String, File> getAttachList() {
		return attachList;
	}

	/**
	 * Sets the value for attachList.
	 * 
	 * @param attachList
	 */
	public void setAttachList(HashMap<String, File> attachList) {
		this.attachList = attachList;
	}

	public Map<String, String> getTemplateValueMap() {
		return templateValueMap;
	}

	public void setTemplateValueMap(Map<String, String> templateValueMap) {
		this.templateValueMap = templateValueMap;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public InternetAddress getFromAddres() throws AddressException {
		return new InternetAddress(this.from);
	}

	public boolean isTieneAdjunto() {
		return tieneAdjunto;
	}

	public void setTieneAdjunto(boolean tieneAdjunto) {
		this.tieneAdjunto = tieneAdjunto;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public InternetAddress[] getToAddresses() throws AddressException {
		List<InternetAddress> resultado = new ArrayList<InternetAddress>();

		for (String t : this.to) {
			resultado.add(new InternetAddress(t));
		}

		return resultado.toArray(new InternetAddress[this.to.size()]);
	}

	public String getNombreReporte() {
		return nombreReporte;
	}

	public void setNombreReporte(String nombreReporte) {
		this.nombreReporte = nombreReporte;
	}
}
