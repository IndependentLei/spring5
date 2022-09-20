package com.ji.spring5.test.beanProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @Description :
 * @ClassName : Bean1
 * @Author : jdl
 * @Create : 2022-09-17 21:31
 */
public class Bean1 {
    private Bean2 bean2;
    private Bean3 bean3;
    private String home;

    @Autowired
    public void setBean2(Bean2 bean2){
        System.out.println("@Autowired 生效："+bean2);
        this.bean2 = bean2;
    }

    @Resource
    public void setBean3(Bean3 bean3){
        System.out.println("@Resource 生效："+bean3);
        this.bean3 = bean3;
    }

    @Autowired
    public void setHome(@Value("${JAVA_HOME}") String home){
        System.out.println("@Value 生效："+home);
        this.home = home;
    }

    @PostConstruct
    public void init(){
        System.out.println("@PostConstruct 生效");
    }

    @PreDestroy void destroy(){
        System.out.println("@PreDestroy 生效");
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Bean1{");
        sb.append("bean2=").append(bean2);
        sb.append(", bean3=").append(bean3);
        sb.append(", home='").append(home).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
