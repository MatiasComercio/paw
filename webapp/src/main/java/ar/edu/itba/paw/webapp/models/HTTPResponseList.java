package ar.edu.itba.paw.webapp.models;


import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class HTTPResponseList {
	private List<HTTPResponseDTO> responses;

	public HTTPResponseList() {

	}

	public HTTPResponseList(final int size) {
		responses = new ArrayList<>(size);
	}


	public void add(final String property, final Response.Status status) {
		final HTTPResponseDTO responseDTO = new HTTPResponseDTO(property, status.getStatusCode());

		responses.add(responseDTO);
	}

	public List<HTTPResponseDTO> getResponses() {
		return responses;
	}

	public void setResponses(List<HTTPResponseDTO> responses) {
		this.responses = responses;
	}
}
