package com.blackstar.vblog.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author huah
 * @since 2021年11月09日
 */
@Data
public class LoginDto implements Serializable {

  @NotBlank(message = "昵称不能为空")
  private String username;

  @NotBlank(message = "密码不能为空")
  private String password;
}
