package ar.edu.itba.paw.webapp.models;

import java.util.List;

public class AuthoritiesDTO {

  List<AuthorityDTO> authorities;

  public AuthoritiesDTO() {
  }

  public AuthoritiesDTO(List<AuthorityDTO> authorities) {
    this.authorities = authorities;
  }

  public List<AuthorityDTO> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(List<AuthorityDTO> authorities) {
    this.authorities = authorities;
  }
}
