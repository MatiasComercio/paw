package ar.edu.itba.paw.webapp.models;


import java.util.List;

public class AvailableFinalInscriptionsList {
  private List<AvailableFinalInscriptionDTO> finalInscriptions;

  public AvailableFinalInscriptionsList() {
  }

  public AvailableFinalInscriptionsList(List<AvailableFinalInscriptionDTO> finalInscriptions) {
    this.finalInscriptions = finalInscriptions;
  }

  public List<AvailableFinalInscriptionDTO> getFinalInscriptions() {
    return finalInscriptions;
  }

  public void setFinalInscriptions(List<AvailableFinalInscriptionDTO> finalInscriptions) {
    this.finalInscriptions = finalInscriptions;
  }
}
