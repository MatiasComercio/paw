package ar.edu.itba.paw.webapp.models;

import java.util.List;

public class AdminsList {
  private List<AdminDTO> admins;

  public AdminsList(List<AdminDTO> admins) {
    this.admins = admins;
  }

  public AdminsList() {
  }

  public List<AdminDTO> getAdmins() {
    return admins;
  }

  public void setAdmins(List<AdminDTO> admins) {
    this.admins = admins;
  }
}
