package com.qiang.springvue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@SpringBootApplication
@Configuration
@ComponentScan(value = "com.zach.services")
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class SpringVueApplication {

    @GetMapping("/demo")
    public String index(){
        return "hello!!!";
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringVueApplication.class, args);
    }

}
