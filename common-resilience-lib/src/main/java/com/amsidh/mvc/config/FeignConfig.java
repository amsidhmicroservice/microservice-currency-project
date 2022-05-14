package com.amsidh.mvc.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"com.amsidh.mvc.feign"})
public class FeignConfig {
}
