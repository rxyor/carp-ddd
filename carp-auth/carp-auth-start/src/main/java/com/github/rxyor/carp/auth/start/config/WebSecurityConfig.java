package com.github.rxyor.carp.auth.start.config;

import com.github.rxyor.carp.auth.security.support.oauth2.CarpUserDetailsService;
import com.github.rxyor.carp.auth.security.support.oauth2.NonPasswordEncoder;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/2/13 Wed 18:02:00
 * @since 1.0.0
 */
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name = "carpUserDetailsService")
    private CarpUserDetailsService carpUserDetailsService;


    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic();
        http.authorizeRequests()
            .antMatchers("/oauth/token").permitAll()
            .antMatchers("/oauth2/token/access").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(carpUserDetailsService)
            .passwordEncoder(new NonPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/favor.ico");
    }
}
