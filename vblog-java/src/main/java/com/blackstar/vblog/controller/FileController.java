package com.blackstar.vblog.controller;

import com.alibaba.fastjson.JSONObject;
import com.blackstar.vblog.common.lang.Result;
import com.blackstar.vblog.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author huah
 * @since 2021年12月20日
 */
@RestController
public class FileController {

  @Autowired
  private UploadService uploadService;

  @PostMapping("/upload")
  @ResponseBody
  public Result upload(HttpServletRequest request, @RequestParam("file")MultipartFile image) {
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

  @PostMapping("/upload2")
  @ResponseBody
  public Result uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
    Map<String,String> map = uploadService.upfile(file, request);//这里调用service的upfile方法，传入两个参数。

    JSONObject object = new JSONObject();
    object.put("url", map.get("imageurl"));

    return Result.succ(object);
  }
}
