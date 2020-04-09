package com.github.rxyor.carp.common.rocketmq.start.config;

import com.github.rxyor.common.support.annotations.CryptoApi;
import com.github.rxyor.common.support.util.CryptoUtil;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import java.lang.annotation.Annotation;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
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
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @RequestMapping("/doc/api")
    public String forwardSwaggerUi() {
        return "redirect:/swagger-ui.html";
    }

    @Bean
    public Docket apiDocket() {
        //选择swagger版本
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
            .groupName("对外接口")
            .select()
            .apis(withClassAnnotation(Api.class, CryptoApi.class))
            .paths(PathSelectors.any())
            .build();
        docket
            .globalOperationParameters(Lists.newArrayList(authenticationParameter()))
            .securitySchemes(Lists.newArrayList(apiKeys()))
            .apiInfo(apiInfo());
        return docket;
    }

    @Bean
    public Docket cryptoApiDocket() {
        //选择swagger版本
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket
            .groupName("对内接口")
            .select()
            .apis(RequestHandlerSelectors.withMethodAnnotation(CryptoApi.class))
            .build();
        docket
            .globalOperationParameters(Lists.newArrayList(cryptoParameter()))
            .apiInfo(apiInfo());
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("carp-rocketmq")
            .version("1.0.0")
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

    private Parameter authenticationParameter() {
        ParameterBuilder builder = new ParameterBuilder();
        return builder
            .name("Authorization")
            .description("Bearer Authentication")
            .parameterType("header")
            .modelRef(new ModelRef("string"))
            .required(false)
            .build();
    }

    private List<ApiKey> apiKeys() {
        ApiKey authorization = new ApiKey("Authorization", "Bearer ", "header");
        return Lists.newArrayList(authorization);
    }


    private static Predicate<RequestHandler> withClassAnnotation(final Class<? extends Annotation> annotation,
        final Class<? extends Annotation> excludeAnnotation) {
        return input -> declaringClass(input).isAnnotationPresent(annotation)
            && !input.isAnnotatedWith(excludeAnnotation);
    }

    private static Class<?> declaringClass(RequestHandler input) {
        return input.declaringClass();
    }

}
