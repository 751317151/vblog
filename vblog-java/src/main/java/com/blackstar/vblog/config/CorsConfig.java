package com.blackstar.vblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 解决跨域问题
 * @author huah
 * @since 2021年11月09日
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOriginPatterns("*")
        .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
        .allowCredentials(true)
        .maxAge(3600)
        .allowedHeaders("*");
  }
}
