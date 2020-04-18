package com.yellowmessenger.urlshortner.urlshortner.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Component
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    public void test()
    {
        System.out.println("added");
    }
}
