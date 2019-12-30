package com.github.rxyor.carp.auth.security.exhandler;


import com.github.rxyor.carp.auth.security.exception.CarpOauth2Exception;
import com.github.rxyor.carp.auth.security.exception.ForbiddenException;
import com.github.rxyor.carp.auth.security.exception.MethodNotAllowedException;
import com.github.rxyor.carp.auth.security.exception.ServerErrorException;
import com.github.rxyor.carp.auth.security.exception.UnauthorizedException;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.web.HttpRequestMethodNotSupportedException;

/**
 *<p>
 * spring secuity不能使用项目中的统一异常处理。因此，
 *复制spring默认实现类，修改异常进而实现自定义序列化
 * {@link DefaultWebResponseExceptionTranslator}
 *</p>
 *
 * @author liuyang
 * @date 2019/4/1 Mon 17:06:00
 * @since 1.0.0
 */
@SuppressWarnings("all")
@Slf4j
public class CarpWebResponseExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {

        // Try to extract a SpringSecurityException from the stacktrace
        Throwable[] causeChain = throwableAnalyzer.determineCauseChain(e);
        Exception ase = (OAuth2Exception) throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);

        if (ase != null) {
            return handleOAuth2Exception((OAuth2Exception) ase);
        }

        ase = (AuthenticationException) throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class,
            causeChain);
        if (ase != null) {
            return handleOAuth2Exception(new UnauthorizedException(e.getMessage(), e));
        }

        ase = (AccessDeniedException) throwableAnalyzer
            .getFirstThrowableOfType(AccessDeniedException.class, causeChain);
        if (ase instanceof AccessDeniedException) {
            return handleOAuth2Exception(new ForbiddenException(ase.getMessage(), ase));
        }

        ase = (HttpRequestMethodNotSupportedException) throwableAnalyzer.getFirstThrowableOfType(
            HttpRequestMethodNotSupportedException.class, causeChain);
        if (ase instanceof HttpRequestMethodNotSupportedException) {
            return handleOAuth2Exception(new MethodNotAllowedException(ase.getMessage(), ase));
        }

        return handleOAuth2Exception(new ServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));

    }

    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) throws IOException {

        int status = e.getHttpErrorCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        if (status == HttpStatus.UNAUTHORIZED.value() || (e instanceof InsufficientScopeException)) {
            headers.set("WWW-Authenticate", String.format("%s %s", OAuth2AccessToken.BEARER_TYPE, e.getSummary()));
        }
        String errMsg = e.getMessage();
        Integer errorCode = e.getHttpErrorCode();
        if (e instanceof InvalidGrantException) {
            errMsg = "用户名或密码错误";
        }

        return new ResponseEntity<OAuth2Exception>(new CarpOauth2Exception(errorCode, errMsg), headers,
            HttpStatus.valueOf(status));
    }
}
