package ar.edu.itba.paw.webapp.models;

import java.util.List;

public class FinalInscriptionsList {
  private List<FinalInscriptionIndexDTO> finalInscriptions;

  public FinalInscriptionsList(List<FinalInscriptionIndexDTO> finalInscriptions) {
    this.finalInscriptions = finalInscriptions;
  }

  public FinalInscriptionsList() {
  }

  public List<FinalInscriptionIndexDTO> getFinalInscriptions() {
    return finalInscriptions;
  }

  public void setFinalInscriptions(List<FinalInscriptionIndexDTO> finalInscriptions) {
    this.finalInscriptions = finalInscriptions;
  }
}
