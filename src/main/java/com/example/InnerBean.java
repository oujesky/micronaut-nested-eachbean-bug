package com.example;

import io.micronaut.context.annotation.EachBean;
import io.micronaut.context.annotation.Parameter;

@EachBean(OuterConfiguration.InnerConfiguration.class)
public class InnerBean {

    private final OuterConfiguration.InnerConfiguration innerConfiguration;

    public InnerBean(@Parameter OuterConfiguration.InnerConfiguration innerConfiguration) {
        this.innerConfiguration = innerConfiguration;
    }

    public String getConfigName() {
        return innerConfiguration.name();
    }
}
