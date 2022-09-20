package com.ji.spring5.test.beanProcessor;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description :
 * @ClassName : Bean4
 * @Author : jdl
 * @Create : 2022-09-17 21:31
 */
@ConfigurationProperties(prefix = "java")
public class Bean4 {
    private String home;
    private String version;

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
