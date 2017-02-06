package ar.edu.itba.paw.webapp.models;


public class AvailableFinalInscriptionDTO {

	private FinalInscriptionIndexDTO inscription;
	private Boolean available;

  public AvailableFinalInscriptionDTO() {
  }

	public AvailableFinalInscriptionDTO(FinalInscriptionIndexDTO inscription, Boolean available) {
		this.inscription = inscription;
		this.available = available;
  }

	public FinalInscriptionIndexDTO getInscription() {
		return inscription;
	}

	public void setInscription(FinalInscriptionIndexDTO inscription) {
		this.inscription = inscription;
	}

  public Boolean getAvailable() {
    return available;
  }

  public void setAvailable(Boolean available) {
    this.available = available;
  }
}
