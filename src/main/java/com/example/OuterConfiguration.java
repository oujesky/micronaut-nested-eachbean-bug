package com.example;

import io.micronaut.context.annotation.EachProperty;
import io.micronaut.context.annotation.Parameter;

import java.util.List;

@EachProperty("config.outers")
public record OuterConfiguration(
    @Parameter String name,
    List<InnerConfiguration> inners
) {

    @EachProperty("inners")
    public record InnerConfiguration(
        @Parameter String name,
        boolean value
    ) {
    }


}
