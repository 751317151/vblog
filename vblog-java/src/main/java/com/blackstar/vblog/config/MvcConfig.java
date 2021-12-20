package com.blackstar.vblog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author huah
 * @since 2021年09月01日
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

  @Value("${file.common.uploadWindow}")
  private String windowsPath;

  @Value("${file.common.uploadLinux}")
  private String linuxPath;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/Images/**")
        .addResourceLocations("file:"+windowsPath);
  }

}
