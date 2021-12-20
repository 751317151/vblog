package com.blackstar.vblog.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author huah
 * @since 2021年12月20日
 */
public interface UploadService {
  Map<String, String> upfile(MultipartFile file, HttpServletRequest request) throws IOException;
}
