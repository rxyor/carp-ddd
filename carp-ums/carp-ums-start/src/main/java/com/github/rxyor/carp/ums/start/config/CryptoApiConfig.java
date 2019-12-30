package com.github.rxyor.carp.ums.start.config;

import com.github.rxyor.common.support.aspect.crypto.CryptoApiAspect;
import com.github.rxyor.common.support.aspect.crypto.CryptoApiResolver;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/12/2 周一 21:23:00
 * @since 1.0.0
 */
@Configuration
public class CryptoApiConfig {

    @Bean
    public CryptoApiAspect cryptoApiAspect(CryptoApiResolver cryptoApiResolver){
        return new CryptoApiAspect(cryptoApiResolver);
    }

    @Bean
    public CryptoApiResolver cryptoApiResolver(HttpServletRequest httpServletRequest){
        CryptoApiResolver cryptoApiResolver = new CryptoApiResolver(httpServletRequest);
        return cryptoApiResolver;
    }

}
