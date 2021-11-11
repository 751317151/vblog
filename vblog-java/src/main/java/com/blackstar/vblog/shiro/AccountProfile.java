package com.blackstar.vblog.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * @author huah
 * @since 2021年11月08日
 */
@Data
public class AccountProfile implements Serializable {

  private Long id;

  private String username;

  private String avatar;

  private String email;

}
