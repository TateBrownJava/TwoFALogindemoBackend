package com.hznu.fa2login.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: TateBrown
 * @date: 2018/11/27 19:56
 * @param:
 * @return:
 */
@Configuration
@MapperScan("com.hznu.fa2login.modules.sys.**.dao")
public class MybatisPlusConfig {
    @Bean
    public PaginationInterceptor paginationInterceptor(){return new PaginationInterceptor();}
}
