package com.blackstar.vblog.controller;


import com.blackstar.vblog.common.lang.Result;
import com.blackstar.vblog.entity.MUser;
import com.blackstar.vblog.service.MUserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huah
 * @since 2021-11-08
 */
@RestController
@RequestMapping("/user")
public class MUserController {

  @Autowired
  MUserService userService;

  @RequiresAuthentication
  @GetMapping("/index")
  public Result index() {
    MUser user = userService.getById(1L);
    return Result.succ(user);
  }

  @PostMapping("/save")
  public Result save(@Validated @RequestBody MUser user) {
    return Result.succ(user);
  }

}
