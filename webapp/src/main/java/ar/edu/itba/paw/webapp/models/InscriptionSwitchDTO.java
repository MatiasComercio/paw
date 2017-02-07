package ar.edu.itba.paw.webapp.models;

import javax.validation.constraints.NotNull;

public class InscriptionSwitchDTO {
  @NotNull
  private boolean enabled;

  public InscriptionSwitchDTO() {
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(final boolean enabled) {
    this.enabled = enabled;
  }
}
