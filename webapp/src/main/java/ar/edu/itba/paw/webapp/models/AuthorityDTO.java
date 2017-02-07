package ar.edu.itba.paw.webapp.models;

import org.springframework.security.core.GrantedAuthority;

public class AuthorityDTO {


  private String authority;

  public AuthorityDTO() {

  }

  public AuthorityDTO(final GrantedAuthority authority) {
    this.authority = authority.getAuthority();
  }

  public String getAuthority() {
    return authority;
  }

  public void setAuthority(String authority) {
    this.authority = authority;
  }
}
