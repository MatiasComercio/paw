package ar.edu.itba.paw.webapp.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CorrelativeDTO {

  @Min(1)
  @NotNull
  private String correlativeId;

  public CorrelativeDTO() {

  }

  public String getCorrelativeId() {
    return correlativeId;
  }

  public void setCorrelativeId(final String correlativeId) {
    this.correlativeId = correlativeId;
  }
}
