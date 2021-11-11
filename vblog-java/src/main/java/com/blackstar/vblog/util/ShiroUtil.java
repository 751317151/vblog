package com.blackstar.vblog.util;

import com.blackstar.vblog.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

/**
 * @author huah
 * @since 2021年11月08日
 */
public class ShiroUtil {

  public static AccountProfile getProfile() {
    return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
  }

}
