package com.blackstar.vblog.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blackstar.vblog.common.dto.LoginDto;
import com.blackstar.vblog.common.lang.Result;
import com.blackstar.vblog.entity.MUser;
import com.blackstar.vblog.service.MUserService;
import com.blackstar.vblog.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author huah
 * @since 2021年11月09日
 */
@RestController
public class AccountController {

  @Autowired
  MUserService userService;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/login")
  public Result login(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {

    MUser user = userService.getOne(new QueryWrapper<MUser>().eq("username", loginDto.getUsername()));
    Assert.notNull(user, "用户名或密码错误");

    if(!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){
//      Assert.notNull(null, "密码不正确");
      return Result.fail("用户名或密码错误");
    }
    String jwt = jwtUtils.generateToken(user.getId());

    response.setHeader("Authorization", jwt);
    response.setHeader("Access-control-Expose-Headers", "Authorization");

    return Result.succ(MapUtil.builder()
        .put("id", user.getId())
        .put("username", user.getUsername())
        .put("avatar", user.getAvatar())
        .put("email", user.getEmail())
        .map()
    );
  }

  @RequiresAuthentication
  @GetMapping("/logout")
  public Result logout() {
    SecurityUtils.getSubject().logout();
    return Result.succ(null);
  }

}
