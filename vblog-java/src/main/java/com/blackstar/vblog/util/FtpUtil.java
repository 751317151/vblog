package com.blackstar.vblog.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author huah
 * @since 2021年12月20日
 */
@Component
public class FtpUtil {

  //ftp服务器ip地址
  private static final String FTP_ADDRESS = "ip";
  //端口号
  private static final int FTP_PORT = 21;
  //用户名
  private static final String FTP_USERNAME = "user";
  //密码
  private static final String FTP_PASSWORD = "password";
  //路径都是/home/加上用户名
  public final String FTP_BASEPATH = "/home/user/images/";

  //参数传过来了文件和文件的输入流
  public boolean uploadFile(String originFileName, InputStream input,String datePath) {
    boolean success = false;
    FTPClient ftp = new FTPClient();//这是最开始引入的依赖里的方法
    ftp.setControlEncoding("utf-8");
    try {
      int reply;
      ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
      ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
      reply = ftp.getReplyCode();//连接成功会的到一个返回状态码
      System.out.println(reply);//可以输出看一下是否连接成功
      ftp.setFileType(FTPClient.BINARY_FILE_TYPE);//设置文件类型
      boolean flag = ftp.changeWorkingDirectory(FTP_BASEPATH);//修改操作空间

      flag = ftp.makeDirectory(datePath);
      flag = ftp.changeWorkingDirectory(datePath);

      //对了这里说明一下你所操作的文件夹必须要有可读权限，chomd 777 文件夹名//这里我就是用的我的home文件夹
      ftp.storeFile(originFileName, input);//这里开始上传文件
      if (!FTPReply.isPositiveCompletion(reply)) {
        ftp.disconnect();
        System.out.println("连接失败");
        return success;
      }
      System.out.println("连接成功！");

      input.close();
      ftp.logout();
      success = true;
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (ftp.isConnected()) {
        try {
          ftp.disconnect();
        } catch (IOException ioe) {
        }
      }
    }
    return success;
  }
}
