package ar.edu.itba.paw.webapp.models;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class HTTPResponseDTO {
	private int status;
	private String property;

	public HTTPResponseDTO() {
	}

	HTTPResponseDTO(String property, int status) {
		setProperty(property);
		setStatus(status);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getProperty() {
		return property;
	}

	private void setProperty(final String property) {
		this.property = property;
	}
}
