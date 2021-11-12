package com.blackstar.vblog.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blackstar.vblog.common.lang.Result;
import com.blackstar.vblog.entity.MBlog;
import com.blackstar.vblog.service.MBlogService;
import com.blackstar.vblog.util.ShiroUtil;
import lombok.Value;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

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

  @PostMapping("/upload")
  public Result upload(HttpServletRequest request, MultipartFile image) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String filePath = sdf.format(new Date());

    File baseFolder = new File("/Images/" + filePath);
    if (!baseFolder.exists()) {
      baseFolder.mkdirs();
    }

    StringBuffer url = new StringBuffer();
    url.append(request.getScheme())
        .append("://")
        .append(request.getServerName())
        .append(":")
        .append(request.getServerPort())
        .append(request.getContextPath())
        .append("/Images/")
        .append(filePath);

    String imgName = UUID.randomUUID().toString().replace("_", "") + "_" + image.getOriginalFilename().replaceAll(" ", "");
    try {
      File dest = new File(baseFolder, imgName);
      FileCopyUtils.copy(image.getBytes(), dest);
      url.append("/").append(imgName);

      JSONObject object = new JSONObject();
      object.put("url", url);


      return Result.succ(object);
    } catch (IOException e) {
      return Result.fail("文件上传错误");
    }
  }

}
