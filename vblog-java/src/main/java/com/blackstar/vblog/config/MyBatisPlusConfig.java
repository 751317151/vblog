package com.blackstar.vblog.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author huah
 * @since 2021年11月08日
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.blackstar.vblog.mapper")
public class MyBatisPlusConfig {

  @Bean
  public PaginationInterceptor paginationInterceptor(){
    PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    return paginationInterceptor;
  }
}