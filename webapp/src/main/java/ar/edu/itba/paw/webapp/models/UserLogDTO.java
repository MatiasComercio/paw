package ar.edu.itba.paw.webapp.models;

public class UserLogDTO {
  private String dni;

  private String password;

  public UserLogDTO() {

  }

  public UserLogDTO(String dni, String password) {
    this.dni = dni;
    this.password = password;
  }

  public String getDni() {
    return dni;
  }

  public String getPassword() {
    return password;
  }
}
