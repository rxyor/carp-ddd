package com.github.rxyor.carp.auth.security.config;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *<p>
 *swagger api配置
 *</p>
 *
 * @author liuyang
 * @date 2018-12-07 Fri 13:27:46
 * @since 1.0.0
 */
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig {

    private List<String> oauth2UrlList = Lists.newArrayList(
        "/oauth/token",
        "/oauth/confirm_access",
        "/oauth/check_token",
        "/oauth/authorize",
        "/actuator"
    );


    @Bean
    public Docket commonDocket() {
        //选择swagger版本
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket
            .groupName("Api.class Group")
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
            .paths(PathSelectors.any())
            .build();
        docket
            .globalOperationParameters(Lists.newArrayList(authenticationParameter()))
            .securitySchemes(Lists.newArrayList(apiKeys()))
            .apiInfo(apiInfo());
        return docket;
    }

    @Bean
    public Docket oauth2Docket() {
        //选择swagger版本
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket
            .groupName("Oauth2 Group")
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(this::isOauth2Path)
            .build();
        List<Parameter> parameterList = Lists.newArrayListWithCapacity(4);
        parameterList.add(authenticationParameter());
        parameterList.addAll(httpBasicParameters());
        docket
            .globalOperationParameters(parameterList)
            .securitySchemes(Lists.newArrayList(apiKeys()))
            .apiInfo(apiInfo());
        return docket;
    }

    @SuppressWarnings("all")
    private boolean isOauth2Path(String url) {
        if (StringUtils.isEmpty(url)) {
            return false;
        }
        for (String e : oauth2UrlList) {
            if (url.startsWith(e)) {
                return true;
            }
        }
        return false;
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

    private List<Parameter> httpBasicParameters() {
        ParameterBuilder builder = new ParameterBuilder();
        Parameter clientId = builder
            .name("client_id")
            .description("client_id")
            .parameterType("query")
            .modelRef(new ModelRef("string"))
            .required(false)
            .build();
        Parameter clientSecret = builder
            .name("client_secret")
            .description("client_secret")
            .parameterType("query")
            .modelRef(new ModelRef("string"))
            .required(false)
            .build();
        return Lists.newArrayList(clientId, clientSecret);
    }


    private List<ApiKey> apiKeys() {
        ApiKey authorization = new ApiKey("Authorization", "Bearer ", "header");
        return Lists.newArrayList(authorization);
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("巷子外的人",
            "https://blog.csdn.net/liuyanglglg", "rxyor@outlook.com");
        return new ApiInfoBuilder()
            .title("carp cloud api")
            .contact(contact)
            .version("1.0")
            .build();
    }


}
