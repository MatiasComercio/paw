package ar.edu.itba.paw.webapp.models;

import javax.validation.constraints.NotNull;

public class InscriptionSwitchDTO {
  //TODO ask if it should receive a string and parse it so that other thing different from true is not considered false, except for false
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
