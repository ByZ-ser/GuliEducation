package com.zhang.msmservice;

import com.zhang.msmservice.utils.EmailUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.zhang")
public class MsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(MsmApplication.class);
    }
    @Bean
    public EmailUtil getEmaliUtil(){
        return new EmailUtil();
    }
}
