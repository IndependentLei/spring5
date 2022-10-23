package com.ji.spring5.test.boot.autoConfig.one;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

public class MyImportSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        List<String> autoConfigurationNameList = SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class, ClassLoader.getSystemClassLoader());
        for (String autoConfigClassName : autoConfigurationNameList) {
            System.out.println(autoConfigClassName);
        }
        List<String> classNameList = SpringFactoriesLoader.loadFactoryNames(MyImportSelector.class, ClassLoader.getSystemClassLoader());
        return classNameList.toArray(new String[0]);
    }
}
