package com.hznu.fa2login.config;

import com.hznu.fa2login.myBeans.UniversalEnumConverterFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author: TateBrown
 * @date: 2018/11/27 19:58
 * @param:
 * @return:
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addFormatters(FormatterRegistry registrar){
        registrar.addConverterFactory(new UniversalEnumConverterFactory());
    }
}
