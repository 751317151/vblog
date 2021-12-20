package com.blackstar.vblog.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blackstar.vblog.common.lang.Result;
import com.blackstar.vblog.entity.MBlog;
import com.blackstar.vblog.service.MBlogService;
import com.blackstar.vblog.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huah
 * @since 2021-11-08
 */
@RestController
public class MBlogController {

  @Autowired
  MBlogService blogService;

  @GetMapping("/blogs")
  public Result list(@RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue = "5") Integer currentSize) {

    Page page = new Page(currentPage, currentSize);
    IPage pageData = blogService.page(page, new QueryWrapper<MBlog>().orderByDesc("created"));

    return Result.succ(pageData);
  }

  @GetMapping("/blog/{id}")
  public Result detail(@PathVariable(name = "id") Long id) {
    MBlog blog = blogService.getById(id);
    Assert.notNull(blog, "该博客已被删除");

    return Result.succ(blog);
  }

  @RequiresAuthentication
  @PostMapping("/blog/edit")
  public Result edit(@Validated @RequestBody MBlog blog) {

//        Assert.isTrue(false, "公开版不能任意编辑！");
    System.out.println(ShiroUtil.getProfile().getId());
    MBlog temp = null;
    if(blog.getId() != null) {
      temp = blogService.getById(blog.getId());
      // 只能编辑自己的文章
      System.out.println(ShiroUtil.getProfile().getId());
      Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(), "没有权限编辑");

    } else {

      temp = new MBlog();
      temp.setUserId(ShiroUtil.getProfile().getId());
      temp.setCreated(LocalDateTime.now());
      temp.setStatus(0);
    }

    BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
    blogService.saveOrUpdate(temp);

    return Result.succ(null);
  }

}
