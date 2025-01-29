package com.example;

import io.micronaut.context.ApplicationContext;
import io.micronaut.inject.qualifiers.Qualifiers;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest(startApplication = false)
class MicronautNestedEachbeanBugTest {

    @Inject
    ApplicationContext applicationContext;

    @Test
    void testOuterConfiguration() {
        var outerConfigurations = applicationContext.getBeansOfType(OuterConfiguration.class);
        assertThat(outerConfigurations)
            .singleElement()
            .satisfies(outerConfiguration -> {
                assertThat(outerConfiguration.name()).isEqualTo("outer-a");
                assertThat(outerConfiguration.inners())
                    .hasSize(2)
                    .satisfiesExactlyInAnyOrder(
                        innerConfiguration -> assertThat(innerConfiguration.name()).isEqualTo("inner-a"),
                        innerConfiguration -> assertThat(innerConfiguration.name()).isEqualTo("inner-b")
                    );
            });
    }

    @Test
    void testOuterBeans() {
        var outerBeans = applicationContext.getBeansOfType(OuterBean.class);
        assertThat(outerBeans)
            .singleElement()
            .satisfies(outerBean -> assertThat(outerBean.getConfigName()).isEqualTo("outer-a"));
    }

    @Test
    void testInnerConfiguration() {
        var innerConfigurations = applicationContext.getBeansOfType(OuterConfiguration.InnerConfiguration.class);
        assertThat(innerConfigurations)
            .hasSize(2)
            .satisfiesExactlyInAnyOrder(
                innerConfiguration -> assertThat(innerConfiguration.name()).isEqualTo("inner-a"),
                innerConfiguration -> assertThat(innerConfiguration.name()).isEqualTo("inner-b")
            );
    }

    @Test
    void testInnerBeans() {
        var innerBeans = applicationContext.getBeansOfType(InnerBean.class);
        assertThat(innerBeans)
            .hasSize(2)
            .satisfiesExactlyInAnyOrder(
                innerBean -> assertThat(innerBean.getConfigName()).isEqualTo("inner-a"),
                innerBean -> assertThat(innerBean.getConfigName()).isEqualTo("inner-b")
            );
    }


    @Test
    void testNamedOuterConfiguration() {
        var outerConfiguration = applicationContext.getBean(OuterConfiguration.class, Qualifiers.byName("outer-a"));
        assertThat(outerConfiguration.name())
            .isEqualTo("outer-a");
    }

    @Test
    void testNamedInnerConfiguration() {
        var innerConfiguration = applicationContext.getBean(OuterConfiguration.InnerConfiguration.class, Qualifiers.byName("outer-a-inner-a"));
        assertThat(innerConfiguration.name())
            .isEqualTo("inner-a");
    }

    @Test
    void testNamedOuterBean() {
        var outerBean = applicationContext.getBean(OuterBean.class, Qualifiers.byName("outer-a"));
        assertThat(outerBean.getConfigName())
            .isEqualTo("outer-a");
    }

    @Test
    void testNamedInnerBean() {
        var innerBean = applicationContext.getBean(InnerBean.class, Qualifiers.byName("outer-a-inner-a"));
        assertThat(innerBean.getConfigName())
            .isEqualTo("inner-a");
    }

}
