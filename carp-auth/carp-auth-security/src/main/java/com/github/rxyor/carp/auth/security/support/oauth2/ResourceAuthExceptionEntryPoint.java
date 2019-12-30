package com.github.rxyor.carp.auth.security.support.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.rxyor.common.core.model.R;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/3/31 Sun 14:21:00
 * @since 1.0.0
 */
@SuppressWarnings("all")
@AllArgsConstructor
public class ResourceAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        R<String> result = new R<>();
        result.setCode(HttpStatus.FORBIDDEN.value());
        if (authException != null) {
            result.setMsg("无权访问");
        }
        response.setStatus(HttpStatus.FORBIDDEN.value());
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
    }
}
