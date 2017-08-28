package com.rick.demo.config;


import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Desc :  注册MyBatis分页插件PageHelper
 * User : RICK
 * Time : 2017/8/28 15:09
  */

@Configuration
public class MyBatisConfiguration {

    @Bean
    public PageHelper pageHelper(){
        System.out.println("MyBatisConfiguration.pageHelper()");
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
