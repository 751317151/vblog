package com.blackstar.vblog.service.impl;

import com.blackstar.vblog.service.UploadService;
import com.blackstar.vblog.util.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author huah
 * @since 2021年12月20日
 */
@Service
public class UploadServiceImpl implements UploadService {
  @Autowired
  private FtpUtil ftpUtil;

  @Override
  public  Map<String, String> upfile( MultipartFile file, HttpServletRequest request) throws IOException {
    Map<String, String> map = new HashMap<>();
    map.put("code", "0");
    map.put("msg", "上传文件失败");
    String fileName = file.getOriginalFilename();//获取文件名

    fileName = UUID.randomUUID().toString().replace("_", "") + "_" + file.getOriginalFilename().replaceAll(" ", "");
    //上传的文件名也需要加上后缀，不然虚拟机不知道文件格式
    InputStream inputStream = file.getInputStream();
    String filePath = null;
    //关于ftp处理文件上传下载这里单独写了一个工具类ftpUtil
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    String datepath = sdf.format(new Date());
    Boolean flag = ftpUtil.uploadFile(fileName, inputStream, datepath);//主要就是这里实现了ftp的文件上传
    if (flag == true) {
      //log.info("上传文件成功!");
      filePath = ftpUtil.FTP_BASEPATH  + datepath + "/" + fileName;
      map.put("code", "1");
      map.put("msg", "上传文件成功");
      map.put("imageurl","http://ip:8088/images/"+datepath+"/"+fileName);
    }
    map.put("path", filePath);
    System.out.println(map);
    return map;
  }

}
