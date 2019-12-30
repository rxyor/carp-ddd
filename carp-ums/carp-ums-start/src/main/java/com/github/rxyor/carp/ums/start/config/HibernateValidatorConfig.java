package com.github.rxyor.carp.ums.start.config;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/10/11 周五 16:17:00
 * @since 1.0.0
 */
@Configuration
public class HibernateValidatorConfig {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
        postProcessor.setValidator(validator());
        return postProcessor;
    }

    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation
            .byProvider(HibernateValidator.class)
            .configure()
            .failFast(false)
            .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
