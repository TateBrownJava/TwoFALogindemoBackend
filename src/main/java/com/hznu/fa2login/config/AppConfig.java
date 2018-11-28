package com.hznu.fa2login.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: TateBrown
 * @date: 2018/11/27 19:54
 * @param:
 * @return:
 */
@Configuration
public class AppConfig
{

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){return builder.build();}
}
