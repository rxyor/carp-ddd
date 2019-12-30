package com.github.rxyor.carp.ums.start.config;

import com.github.rxyor.common.support.annotations.CryptoApi;
import com.github.rxyor.common.support.util.CryptoUtil;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import java.lang.annotation.Annotation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *<p>
 *SwaggerConfiguration
 *</p>
 *
 * @author liuyang
 * @date 2018-12-07 Fri 13:27:46
 * @since 1.0.0
 */
@Controller
@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @RequestMapping("/doc/api")
    public String forwardSwaggerUi() {
        return "redirect:/swagger-ui.html";
    }

    @Bean
    public Docket commonDocket() {
        return new Docket(
            DocumentationType.SWAGGER_2)
            .groupName("Api.class Group")
            .select()
            .apis(withClassAnnotation(Api.class, CryptoApi.class))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo());
    }

    @Bean
    public Docket cryptoDocket() {
        //选择swagger版本
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket
            .groupName("CryptoApi.class Group")
            .select()
            .apis(RequestHandlerSelectors.withMethodAnnotation(CryptoApi.class))
            .build();
        docket
            .globalOperationParameters(Lists.newArrayList(cryptoParameter()))
            .apiInfo(apiInfo());
        return docket;
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("carp-ddd",
            "https://blog.csdn.net/liuyanglglg", "rxyor@outlook.com");
        return new ApiInfoBuilder()
            .title("carp ddd api")
            .contact(contact)
            .version("1.0")
            .build();
    }

    private Parameter cryptoParameter() {
        ParameterBuilder builder = new ParameterBuilder();
        return builder
            .name(CryptoUtil.REQUEST_PARAM_CONST_KEY)
            .description("crypto param")
            .parameterType("query")
            .modelRef(new ModelRef("string"))
            .required(false)
            .build();
    }

    private static Predicate<RequestHandler> withClassAnnotation(final Class<? extends Annotation> annotation,
        final Class<? extends Annotation> excludeAnnotation) {
        return new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler input) {
                return declaringClass(input).isAnnotationPresent(annotation)
                    && !input.isAnnotatedWith(excludeAnnotation);
            }
        };
    }

    private static Class<?> declaringClass(RequestHandler input) {
        return input.declaringClass();
    }

}
