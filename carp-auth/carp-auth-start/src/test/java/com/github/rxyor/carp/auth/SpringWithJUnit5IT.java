package com.github.rxyor.carp.auth;

import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/10/6 周日 13:44:00
 * @since 1.0.0
 */
@SpringBootTest(classes = {AuthApplication.class})
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class SpringWithJUnit5IT extends JUnit5IT {

    protected static Logger log = LoggerFactory.getLogger(SpringWithJUnit5IT.class);

}
