package com.dreamer.basic;

import com.dreamer.basic.common.util.SpringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@MapperScan({"com.dreamer.basic.**.dao"})
@ComponentScan({"com.dreamer.basic"})
@Import(SpringUtils.class)
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        //    SpringUtil.setApplicationContext(ctx);  这个对应于AnoStatic
//        SpringUtils.setApplicationContext(ctx);
        String[] beanNames =  ctx.getBeanDefinitionNames();
        System.out.println("beanNames个数："+beanNames.length);
        for(String bn:beanNames){
            System.out.println(bn);
        }
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
