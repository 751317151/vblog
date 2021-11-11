package com.blackstar.vblog.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author huah
 * @since 2021年11月08日
 */
public class JwtToken implements AuthenticationToken {

  private String token;

  public JwtToken(String jwt) {
    this.token = jwt;
  }

  @Override
  public Object getPrincipal() {
    return token;
  }

  @Override
  public Object getCredentials() {
    return token;
  }
}
