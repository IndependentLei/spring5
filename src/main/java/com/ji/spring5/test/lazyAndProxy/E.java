package com.ji.spring5.test.lazyAndProxy;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class E {
    @Autowired
    @Lazy
    private F f;

    @Autowired
    private G g;

    @Autowired
    private ObjectFactory<H> h; // 使用工厂

    @Autowired
    ApplicationContext applicationContext;

    public I getI(){
        return applicationContext.getBean(I.class);
    }

    public H getH(){
        return h.getObject();
    }

    public G getG(){
        return g;
    }
    public F getF() {
        return f;
    }
}
