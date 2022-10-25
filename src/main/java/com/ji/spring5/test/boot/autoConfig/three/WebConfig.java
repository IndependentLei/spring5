package com.ji.spring5.test.boot.autoConfig.three;

import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.HandlerMapping;

@Configuration
@Import(MyImportSelector.class)  // 导入自动配置类
public class WebConfig {

}
