package com.github.rxyor.carp.ddd.start.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
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
            .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo());
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
}
