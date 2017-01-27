package ar.edu.itba.paw.webapp.models;

public class AuthorityDTO {

  private String action;
  private String location;

  public AuthorityDTO() {}

  public AuthorityDTO(String action, String location) {
    this.action = action;
    this.location = location;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

}
