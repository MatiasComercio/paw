package ar.edu.itba.paw.webapp.auth;

interface TokenHandler {
  /**
   * Returns a new token used for further authentication and authorization
   * @param username the username that may be used to create the token
   * @return the token
   */
  String createToken(String username);

  /**
   * Returns the username
   * @param token
   * @return the username contained in the token;
   *         null if there was an error when trying to get the username from the token
   */
  String getUsername(String token);
}
