package com.ji.spring5.test.boot.autoConfig.one;

import com.ji.spring5.test.boot.autoConfig.four.WebConfig1;
import com.ji.spring5.test.boot.autoConfig.four.WebConfig2;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{WebConfig1.class.getName(), WebConfig2.class.getName()};
    }
}
