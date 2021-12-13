package com.zhang.eduservice.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@MapperScan("com.zhang.eduservice.mapper")
public class EduConfig {

    /**
     * 逻辑删除组件
     * @return
     */
    @Bean
    public ISqlInjector sqlInjector()
    {
        return new LogicSqlInjector();
    }

    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


    @Bean
    //@LoadBalanced
    public RestTemplate getRestTemplate()
    {
        return new RestTemplate();
    }

}
