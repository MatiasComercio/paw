package ar.edu.itba.paw.webapp.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class JWTTokenHandler implements TokenHandler {
  private static final String KEY = "B/1+wzD9jv8UwBZke9EZHfAclHDYre9msPR54UfhXmhx8OBQXrQb0URl9mBwGPBK\n" +
          "ZExc4gxayeTxEPT3DIwMCZfCdMBVh4yjhzJ46AzummsrpyHeo2TgRtWRW+KCTtwo\n" +
          "19kyrxbMpc8dEY9YO2ghNT0ZOcwtvEu13kzngevUsPV9pWsKdRXGgt5HV3rpuvJ7\n" +
          "M0rL3BeV4L2HxkGcs6lfFgsHPxeQvtXLInwdTlrba0TjhN7AdC9vu3000CPmhRGL\n" +
          "4opte33ukexjtkzRXoMdYsDv+pbbufi+vlcl439dwMl9toEhFpKt74ZmPfd989m2\n" +
          "cvSzTN3iOdjfYlf2jMecDV/s05Cs1k4WgCuXYeBY7KVm0hDH3qSzUwPy2I8lH+Pv\n" +
          "ECeypIH0fXNiJurARZ+TndcHicM0ENyEdtM8z8x77oneYioidGC6InotO3FiPiiP\n" +
          "g5gkCMmZKgKdwrHqyT7+/TEUd6dggmHyUgLcSdIb6kznwof/vnbGaoIpyGJqGBc/\n" +
          "rOk4WY61BOQAwnqxg/81vXTv+xFRSjhHWEoSNToshpZwXKq1OvM4s6xIENBXdz6p\n" +
          "V3t4aUYxW7+wBxKxOkl4lq/2MFcTG0szMyiq4E9qrz4kSL6lKEzM/9arXX6+2uOy\n" +
          "ccSr02tkCPLSl05R+/unRZJbOTAgFrvLQvl0dFawj7HQaS0sPf53NenXzU9gmVgo\n" +
          "ZlINm2XKpmf3xOTCS81TS7ReKj4NMGAw0y0WhXasn18ziTEEK9im99SECsZC/hUU\n" +
          "JXBgahepbHqkv8HzJkhV0ExhVn88OOv+Ma2X95ZARmkpbPVBhERRfWVSns12NQzX\n" +
          "FAmS+LCbTTaE2xsz0PDSGZwKIFa5lh2QxKaNlSaaYLbOwVtYxf0CdNQHpvBYJSjj\n" +
          "v5BNMyleYKCZAuLTqTRDuEPn08sPpii2bOafiKPWJ/dgqRjB2sJKpEj0E9IcO5Mk\n" +
          "/SbjyQB7KD0ZwbCQyRC7ffaxjiHkOJV8GwC1x5wB0H5uSfPRIvOmm9FDiZeexbUc\n" +
          "EiOYfXiqetKJe9dTcLKFKRYZDHIj3fZThasbA7nC2qthNHy/0T/UFksmMO+VnOUe\n" +
          "Nm8Zq0DFZqQx6Q/FMUFWZbZvJDT8YtBATX6XkxHGpkWapnkidowDdsWeWT44HCfk\n" +
          "uxCXTkhNRmoQAgOr5Eev2Zda7+ydFK6jxweC5hvR+diobD6ItHllAdBaoOj9hVVs\n" +
          "tJ53S9JXi/az7+UlzxUtPgk4kO1jrulQ5XiN2tn6R2pv1ncnIHdqhaQHjSBkUZik\n" +
          "vUf8FRscFkvo2EbtKPPLQiY6A1yuvrpobCgWj6TOKeujALF6jzdhepsk7sL2Qg1g\n" +
          "6Nah/Tu9I0VOloJlciQenA==\n";

  private UserDetailsService userDetailsServices = new UserDetailsServiceImpl();

  @Override
  public String createToken(final UserDetails user) {
    final String token = Jwts.builder()
            .setSubject(user.getUsername())
            .signWith(SignatureAlgorithm.HS512, KEY)
            .compact();

    return token;
  }

  public String getUsername(final String token) {
    String username = null;

    try {
      username = Jwts.parser()
              .setSigningKey(KEY)
              .parseClaimsJws(token)
              .getBody()
              .getSubject();
    } catch (SignatureException e) {
      return null;
    }
    return username;

  }
}
