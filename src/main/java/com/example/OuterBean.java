package com.example;

import io.micronaut.context.annotation.EachBean;
import io.micronaut.context.annotation.Parameter;

@EachBean(OuterConfiguration.class)
public class OuterBean {

    private final OuterConfiguration outerConfiguration;

    public OuterBean(@Parameter OuterConfiguration outerConfiguration) {
        this.outerConfiguration = outerConfiguration;
    }

    public String getConfigName() {
        return outerConfiguration.name();
    }
}
