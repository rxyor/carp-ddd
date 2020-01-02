package com.github.rxyor.carp.auth.security.support.oauth2.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rxyor.carp.auth.security.exception.UnauthorizedException;
import com.github.rxyor.common.core.model.R;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/3/31 Sun 14:38:00
 * @since 1.0.0
 */
@Slf4j
@AllArgsConstructor
public class CarpAccessDeniedHandler extends OAuth2AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        org.springframework.security.access.AccessDeniedException authException)
        throws IOException, ServletException {
        String errorMsg = "授权失败, 无权访问: " + request.getRequestURI();
        log.info(errorMsg);

        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        R result = R.fail(new UnauthorizedException("授权失败, 没有访问权限"));
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
    }
}
