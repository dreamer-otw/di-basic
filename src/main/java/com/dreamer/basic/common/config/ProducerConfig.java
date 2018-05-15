package com.dreamer.basic.common.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * > producer 配置
 * author : dreamer
 * email : dreamers_otw@163.com
 * date : 2018/5/15 11:12
 */
@Configuration
public class ProducerConfig {
    @Bean("producer")
    public DefaultKaptcha defaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "no");
        properties.setProperty("kaptcha.textproducer.font.color", "black");
        properties.setProperty("kaptcha.textproducer.char.space", "5");
        Config config = new Config(properties);

        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
